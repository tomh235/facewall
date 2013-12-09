package requestmapper;

import domain.Person;
import domain.Team;
import model.UserModel;

public class PersonMapper {

    public Person map(final UserModel userModel, final Team team) {
        return new Person() {
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
                return team;
            }
        };
    }
}
