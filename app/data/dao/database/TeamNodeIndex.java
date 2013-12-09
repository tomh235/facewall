package data.dao.database;

public enum TeamNodeIndex implements NodeIndex {

    Teams_Id("Teams_Id", "id"),
    Teams_Name("Teams_Name", "name");

    private final String name;
    private final String key;

    private TeamNodeIndex(String name, String key) {
        this.name = name;
        this.key = key;
    }

    @Override public String getName() {
        return name;
    }

    @Override public String getKey() {
        return key;
    }
}
