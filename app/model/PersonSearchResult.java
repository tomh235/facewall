package model;

public class PersonSearchResult {
    public final String name;
    public final String teamName;
    public final String picture;

    public PersonSearchResult(String name, String teamName, String picture) {
        this.name = name;
        this.teamName = teamName;
        this.picture = picture;
    }
}
