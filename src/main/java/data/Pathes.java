package data;

public enum Pathes {
    ToModels("./src/main/java/data/models"),
    ToData("./src/main/resources/data");

    String path;

    Pathes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
