package uk.co.o2.facewall.requestmapper;

import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.UserModel;

public class PersonMapper {
    public Person map(final UserModel userModel, final Team team) {
        return new Person() {
            @Override
            public String name() {
                return userModel.name;
            }

            @Override
            public String picture() {
                return userModel.imgURL;
            }

            @Override
            public String email() {
                return userModel.email;
            }

            @Override
            public String role() {
                return userModel.role;
            }

            @Override
            public Team team() {
                return team;
            }

            @Override
            public PersonId getId() {
                return null;
            }

        };
    }
}
