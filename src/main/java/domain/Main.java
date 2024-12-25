package domain;

public class Main {
    public static void main(String[] args) {
        String fname = "/Users/alieksieiev/IdeaProjects/UTP/3rd_PRO/EconomySimulator/src/main/resources/";
        Controller c1 = new Controller("EconomyModel")
                .readDataFrom(fname + "data1.txt")
                .runModel();
        String output = c1.getResultsAsTsv();
        System.out.println(output);

    }
}
