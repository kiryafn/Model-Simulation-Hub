package data;

public enum Paths {
    ToModels("./src/main/java/data/models"),
    ToData("./src/main/resources/data/"),
    ToScripts("./src/main/resources/scripts");

    String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
