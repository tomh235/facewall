package data.dao.database;

public enum PersonNodeIndex implements NodeIndex {

    Persons_Id("Persons_Id", "id"),
    Persons_Name("Persons_Name", "name");

    private final String name;
    private final String key;

    private PersonNodeIndex(String name, String key) {
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
