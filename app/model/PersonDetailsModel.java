package model;

public class PersonDetailsModel implements SearchResultsModel {
    public String name;
    public String teamName;
    public String picture;

    public PersonDetailsModel(String name, String teamName, String picture) {
        this.name = name;
        this.teamName = teamName;
        this.picture = picture;
    }
}
