package model;

public class PersonSearchResult {
    public String name;
    public String teamName;
    public String picture;
    public String email;
    public String role;

    public PersonSearchResult(String name, String teamName, String picture, String email, String role) {
        this.name = name;
        this.teamName = teamName;
        this.picture = picture;
        this.email = email;
        this.role = role;
    }
}
