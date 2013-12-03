package requestmapper;

import data.ScalaRepository;
import domain.Person;
import domain.Query;
import domain.Team;
import model.UserModel;

public class PersonMapper {
    ScalaRepository repository;

    public PersonMapper(ScalaRepository repository) {
        this.repository = repository;
    }

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
