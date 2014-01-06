package data.dao.database;

/**
* Created by charlie on 16/12/13.
*/
public enum NodeIndex {

    Persons("Persons", "id"),
    Teams("Teams", "id");

    private final String name;
    private final String key;

    NodeIndex(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public final String getName() {
        return name;
    }

    public final String getKey() {
        return key;
    }
}
