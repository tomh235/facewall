package uk.co.o2.facewall.model;

public class UserModel {
    public String email;
    public String imgUrl;
    public String name;
    public String role;
    public String location;
    public String team;
    public String scrum;

    // FIXME - Charlie :  refactor me so I only use one constructor?
    public UserModel() {
    }

    public UserModel(String email, String imgUrl, String name, String role, String location, String team, String scrum) {
        this.email = email;
        this.imgUrl = imgUrl;
        this.name = name;
        this.role = role;
        this.location = location;
        this.team = team;
        this.scrum = scrum;
    }
}
