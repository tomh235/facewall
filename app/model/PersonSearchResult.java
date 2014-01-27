package model;

public class PersonSearchResult {
    public String name;
    public String teamName;
    public String picture;
    public String email;

    public PersonSearchResult(String name, String teamName, String picture, String email) {
        this.name = name;
        this.teamName = teamName;
        this.picture = picture;
        this.email = email;
    }
}
