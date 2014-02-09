package uk.co.o2.facewall.model;

public class PersonDetailsModel implements SearchResultsModel {
    public String name;
    public String teamName;
    public String picture;
    public String email;
    public String role;

    public PersonDetailsModel(String name, String teamName, String picture, String email, String role) {
        this.name = name;
        this.teamName = teamName;
        this.picture = picture;
        this.email = email;
        this.role = role;
    }
}
