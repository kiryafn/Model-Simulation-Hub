package domain;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/alieksieiev/IdeaProjects/UTP/3rd_PRO/EconomySimulator/src/main/resources/";
        Controller c1 = new Controller("EconomyModel")
                .readDataFrom(path + "economy_data.txt")
                .runModel()
                .runScript("ZIND = new double[LL]; for (i = 0; i < LL; i++) { ZIND[i] = EXP[i] / PRC[i]; }");
        String output = c1.getResultsAsTsv();
        System.out.println(output);

    }
}
