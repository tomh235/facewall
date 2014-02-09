package uk.co.o2.facewall.model;

public class UserModelBuilder {

    private String name;
    private String imgURL;
    private String role;
    private String email;
    private String team;
    private String location;
    private String scrum;

    public static UserModelBuilder aUserModel() {
        return new UserModelBuilder();
    }

    private UserModelBuilder(){}

    public UserModel build() {
        UserModel userModel = new UserModel();
        userModel.name = name;
        userModel.imgURL = imgURL;
        userModel.role = role;
        userModel.email = email;
        userModel.team = team;
        userModel.location = location;
        userModel.scrum = scrum;
        return userModel;
    }

    public UserModelBuilder named(String name) {
        this.name = name;
        return this;
    }

    public UserModelBuilder withImageURL(String imgURL) {
        this.imgURL = imgURL;
        return this;
    }

    public UserModelBuilder LocatedIn(String location) {
        this.location = location;
        return this;
    }

    public UserModelBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public UserModelBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserModelBuilder inTeam(String team) {
        this.team= team;
        return this;
    }

    public UserModelBuilder inScrum(String scrum) {
        this.scrum = scrum;
        return this;
    }
}
