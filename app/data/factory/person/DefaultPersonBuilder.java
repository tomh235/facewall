package data.factory.person;

import data.builder.PersonBuilder;
import data.datatype.PersonId;
import domain.NoTeam;
import domain.Person;
import domain.Team;

import static data.datatype.PersonId.newPersonId;
import static data.datatype.PersonId.noPersonId;
import static domain.NoTeam.noTeam;

class DefaultPersonBuilder implements PersonBuilder {

    private PersonId personId = noPersonId();
    private String name = "";
    private String picture = "";
    private Team team = noTeam();

    static DefaultPersonBuilder newDefaultPerson() {
        return new DefaultPersonBuilder();
    }

    @Override public Person build() {
        return new Person() {
            final String name = DefaultPersonBuilder.this.name;
            final String picture = DefaultPersonBuilder.this.picture;
            final Team team = DefaultPersonBuilder.this.team;

            @Override public String id() {
                throw new UnsupportedOperationException("id field no longer supported");
            }

            @Override public String name() {
                return this.name;
            }

            @Override public String picture() {
                return this.picture;
            }

            @Override public Team team() {
                return this.team;
            }
        };
    }

    @Override public PersonBuilder withId(String id) {
        personId = newPersonId(id);
        return this;
    }

    @Override public PersonBuilder named(String name) {
        this.name = name;
        return this;
    }

    @Override public PersonBuilder whosePictureIs(String pictureLocation) {
        this.picture = pictureLocation;
        return this;
    }

    public DefaultPersonBuilder inTeam(Team team) {
        this.team = team;
        return this;
    }
}