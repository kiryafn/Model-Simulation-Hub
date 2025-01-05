package domain;

import data.Model;
import data.annotations.Bind;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code Controller} class is responsible for managing and executing various modeling and scripting operations
 * on a given {@link Model}. It facilitates loading data, executing Groovy scripts, running the model logic, and
 * exporting results in a tabular format.
 *
 * <p>This class supports features like:
 * <ul>
 *   <li>Loading data from files into the model</li>
 *   <li>Running custom Groovy scripts</li>
 *   <li>Executing the predefined logic of the model</li>
 *   <li>Exporting the results as tab-separated values (TSV)</li>
 * </ul>
 *
 * <p>It leverages reflection to bind data to fields annotated with {@link Bind}, enabling dynamic interaction
 * with the model and custom scripts.
 */
public class Controller {
    /**
     * The {@link Model} instance managed by this controller.
     */
    private final Model model;

    /**
     * A map storing variables created in Groovy scripts.
     * The key represents the variable name, and the value is an array of double values associated with the variable.
     */
    private Map<String, double[]> scriptVariables = new LinkedHashMap<>();

    /**
     * Constructs a {@code Controller} with the specified model name.
     *
     * @param modelName the name of the {@link Model} class to instantiate.
     * @throws RuntimeException if the {@link Model} cannot be created or initialized.
     */
    public Controller(String modelName) {
        try {
            this.model = (Model) Class.forName("data.models." + modelName).newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Reads data from a file and populates the fields in the associated {@link Model}.
     *
     * <p>The input file should have data in a specific format, where each line represents a variable name followed by
     * its values. For example:
     * <pre>
     * YEARS  2020 2021 2022
     * VAR1   1.0  2.0  3.0
     * VAR2   4.0  5.0  6.0
     * </pre>
     *
     * @param fname the name of the input file from which data is read.
     * @return the current {@code Controller} instance for method chaining.
     * @throws RuntimeException if any error occurs while reading the file or binding data to the {@link Model}.
     */
    public Controller readDataFrom(String fname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            Map<String, double[]> data = new HashMap<>();
            String line;
            int LL = 0;
            int[] years = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("YEARS")) {
                    String[] parts = line.split("\\s+");
                    LL = parts.length - 1;
                    years = new int[LL];
                    for (int i = 1; i < parts.length; i++) {
                        years[i - 1] = Integer.parseInt(parts[i]);
                    }
                    data.put("LL", new double[]{LL});
                } else {
                    String[] parts = line.split("\\s+");
                    String varName = parts[0];
                    double[] values = new double[parts.length - 1];
                    for (int i = 1; i < parts.length; i++) {
                        values[i - 1] = Double.parseDouble(parts[i]);
                    }
                    data.put(varName, values);
                }
            }

            for (Field field : model.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Bind.class)) {
                    field.setAccessible(true);
                    if (field.getName().equals("LL")) {
                        field.set(model, (int) data.get("LL")[0]);
                    } else if (field.getName().equals("YEARS")) {
                        field.set(model, years);
                    } else if (field.getType().isArray() && field.getType().getComponentType() == double.class) {
                        double[] values = data.get(field.getName());
                        if (values == null) {
                            //throw new RuntimeException("Данные для поля '" + field.getName() + "' отсутствуют в входных данных.");
                            values = new double[LL];
                        } else if (values.length < LL) {
                            double[] extended = new double[LL];
                            System.arraycopy(values, 0, extended, 0, values.length);
                            for (int i = values.length; i < LL; i++) {
                                extended[i] = values[values.length - 1];
                            }
                            values = extended;
                        }
                        field.set(model, values);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return this;
    }

    /**
     * Runs the logic encapsulated within the current {@link Model}.
     *
     * <p>This method invokes the {@link Model#run()} method, which processes the loaded data based on the model's
     * predefined computation logic.
     *
     * @return the current {@code Controller} instance for method chaining.
     */
    public Controller runModel() {
        model.run();
        return this;
    }

    /*public Controller runScript(String script) {
        try {
            // Create Groovy engine
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("groovy");

            // Передаём данные модели в движок
            for (Field field : model.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Bind.class)) {
                    field.setAccessible(true);

                    // Добавляем массивы и переменные модели в скриптовый движок
                    if (field.getType().isArray() && field.getType().getComponentType() == double.class) {
                        engine.put(field.getName(), field.get(model));
                    } else if (field.getType() == int.class) {
                        engine.put(field.getName(), field.get(model));
                    }
                }
            }

            // Также передаем переменные из scriptVariables в движок
            for (Map.Entry<String, double[]> entry : scriptVariables.entrySet()) {
                engine.put(entry.getKey(), entry.getValue());
            }

            // Выполняем скрипт
            engine.eval(script);

            // Извлекаем данные обратно из движка в модель
            for (Field field : model.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Bind.class)) {
                    field.setAccessible(true);

                    // Если поле - массив double[], обновляем его из движка
                    if (field.getType().isArray() && field.getType().getComponentType() == double.class) {
                        Object updatedValue = engine.get(field.getName());
                        if (updatedValue != null) {
                            field.set(model, updatedValue);
                        }
                    }
                }
            }

            // Извлекаем обновленные (или добавленные) данные из движка и сохраняем их в scriptVariables
            for (String varName : engine.getBindings(javax.script.ScriptContext.ENGINE_SCOPE).keySet()) {
                Object value = engine.get(varName);
                if (value instanceof double[]) {
                    scriptVariables.put(varName, (double[]) value);
                }
            }
        } catch (ScriptException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }

        return this;
    }*/

    /**
     * Executes a Groovy script provided as a string.
     *
     * <p>The script has access to all fields annotated with {@link Bind} within the {@link Model}.
     * Additionally, variables created or updated during script execution are stored in {@code scriptVariables}.
     *
     * @param script the Groovy script to execute.
     * @return the current {@code Controller} instance for method chaining.
     * @throws RuntimeException if there is any error while executing the script.
     */
    public Controller runScript(String script) {
        // Create a Groovy Binding

        // The `Binding` object is used in the GroovyShell class
        // to pass variables and their values into the Groovy script context.
        // It is a container of named variables that are accessible
        // within the script.
        Binding binding = new Binding();

        // Bind all fields annotated with @Bind to the Groovy script
        Field[] fields = model.getClass().getDeclaredFields();
        ArrayList<String> bindedFieldNames = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Bind.class)) {
                field.setAccessible(true);
                try {
                    binding.setVariable(field.getName(), field.get(model));
                    bindedFieldNames.add(field.getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Bind fields created in scripts
        for (Map.Entry<String, double[]> entry : scriptVariables.entrySet()) {
            binding.setVariable(entry.getKey(), entry.getValue());
        }

        // Create and run the Groovy script
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(script);

        //Variables created or modified in the script are retrieved from `binding`.
        for (var obj : binding.getVariables().entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) obj;

            if (entry.getKey().length() < 2 && entry.getKey().matches("[a-z]"))
                continue;

            if (bindedFieldNames.contains(entry.getKey()))
                continue;

            scriptVariables.put(entry.getKey(), (double[]) entry.getValue());
        }
        //executes the script code specified as a string
        return this;
    }

    /**
     * Reads a Groovy script from a file and executes it.
     *
     * @param fname the name of the file containing the Groovy script.
     * @return the current {@code Controller} instance for method chaining.
     * @throws RuntimeException if any error occurs while reading the file or executing the script.
     */
    public Controller runScriptFromFile(String fname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            StringBuilder scriptBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                scriptBuilder.append(line).append(System.lineSeparator());
            }
            // Выполняем скрипт, переданный в виде строки, с помощью метода runScript
            return this.runScript(scriptBuilder.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error reading script file: " + fname + ". " + e.getMessage());
        }
    }

    /**
     * Retrieves the results of the {@link Model} and variables created during script execution in TSV format.
     *
     * <p>The output is a string where each row represents a variable, with the variable name followed by a tab-separated
     * list of values. For example:
     * <pre>
     * YEARS   2020  2021  2022
     * VAR1    1.0   2.0   3.0
     * VAR2    4.0   5.0   6.0
     * </pre>
     *
     * @return the results in tab-separated values (TSV) format.
     * @throws RuntimeException if there is any error while formatting the results.
     */
    public String getResultsAsTsv() {
        StringBuilder sb = new StringBuilder();

        try {
            for (Field field : model.getClass().getDeclaredFields()) {
                if (field.getType().isArray()) {
                    field.setAccessible(true);

                    String fieldName = field.getName();

                    if (field.getType().getComponentType() == double.class) {
                        double[] values = (double[]) field.get(model);
                        sb.append(fieldName).append("\t");
                        for (double value : values) {
                            //FORMATING VALUES

                            //String formatted = String.format("%.2f", value);
                            //formatted = formatted.replace(",", ".");

                            DecimalFormat formatter = new DecimalFormat("#,###.##");
                            String formatted =  formatter.format(value);
                            sb.append(formatted).append("\t");
                        }
                    } else if (field.getType().getComponentType() == int.class) {
                        int[] intValues = (int[]) field.get(model);
                        sb.append(fieldName).append("\t");
                        for (int value : intValues) {
                            sb.append(value).append("\t");
                        }
                    }

                    sb.append("\n");
                }
            }

            // Формируем TSV из переменных scriptVariables
            for (Map.Entry<String, double[]> entry : scriptVariables.entrySet()) {
                sb.append(entry.getKey()).append("\t");
                for (double value : entry.getValue()) {

                    DecimalFormat formatter = new DecimalFormat("#,###.##");
                    String formatted =  formatter.format(value).replace(",", ".");

                    sb.append(formatted).append("\t");
                }
                sb.append("\n");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error formatting TSV: " + e.getMessage());
        }

        return sb.toString();
    }

    public void resetScriptVariables() {
        scriptVariables = new HashMap<>();
    }
}
