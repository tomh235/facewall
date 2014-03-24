package uk.co.o2.facewall.domain;

import uk.co.o2.facewall.data.datatype.PersonId;

public class PersonStub implements Person {
    public final PersonId id;
    public final String name;
    public final String picture;
    public final String role;
    public Team team;

    public PersonStub(PersonId id, String name, String picture, String role, Team team) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.role = role;
        this.team = team;
    }

    @Override
    public PersonId getId() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String role() {
        return role;
    }

    @Override
    public String picture() {
        return picture;
    }

    @Override
    public Team team() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
