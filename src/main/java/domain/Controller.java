package domain;

import data.Model;
import data.annotations.Bind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private Model model;

    public Controller(String modelName) {
        try {
            this.model = (Model) Class.forName("domain.models." + modelName).newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

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
                            throw new RuntimeException("Данные для поля '" + field.getName() + "' отсутствуют в входных данных.");
                        }
                        if (values.length < LL) {
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
            throw new RuntimeException("Ошибка чтения данных: " + e.getMessage());
        }
        return this;
    }

    public Controller runModel() {
        model.run();
        return this;
    }

    public Controller runScript(String script) {
        // Логика выполнения скрипта
        return this;
    }

    public String getResultsAsTsv() {
        StringBuilder sb = new StringBuilder();

        try {
            // Обходим все поля модели
            for (Field field : model.getClass().getDeclaredFields()) {
                if (field.getType().isArray()) {
                    field.setAccessible(true);

                    // Получаем имя переменной и её значения
                    String fieldName = field.getName();

                    if (field.getType().getComponentType() == double.class) {
                        double[] values = (double[]) field.get(model);
                        sb.append(fieldName).append("\t");
                        for (double value : values) {
                            sb.append(value).append("\t");
                        }
                    } else if (field.getType().getComponentType() == int.class) {
                        int[] intValues = (int[]) field.get(model);
                        sb.append(fieldName).append("\t");
                        for (int value : intValues) {
                            sb.append(value).append("\t"); // Преобразование int -> double
                        }
                    }

                    sb.append("\n");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при формировании TSV: " + e.getMessage());
        }

        return sb.toString();
    }
}
