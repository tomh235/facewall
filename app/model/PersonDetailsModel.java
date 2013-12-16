package model;

public class PersonDetailsModel implements SearchResultsModel {
    public final String name;
    public final String teamName;
    public final String picture;

    public PersonDetailsModel(String name, String teamName, String picture) {
        this.name = name;
        this.teamName = teamName;
        this.picture = picture;
    }
}
