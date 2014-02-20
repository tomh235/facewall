package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.model.UserModel;

public class UserModelMapper {
    public UserModel map(String name, String imgUrl, String email, String team, String scrum, String role, String location) {
        return new UserModel(email, imgUrl, location, name, role, scrum, team);
    }
}
