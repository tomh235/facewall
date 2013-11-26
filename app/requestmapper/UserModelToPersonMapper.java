package requestmapper;

import domain.Person;
import domain.Team;
import model.UserModel;

public class UserModelToPersonMapper {
    public Person map(final UserModel userModel) {
        Person newPerson = new Person() {
            @Override
            public String id() {
                return null;
            }

            @Override
            public String name() {
                return userModel.name;
            }

            @Override
            public String picture() {
                return userModel.imgURL;
            }

            @Override
            public Team team() {
                return null;
            }
        };
        return newPerson;
    }

}
