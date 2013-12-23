package data;

import data.datatype.PersonId;
import data.dto.PersonInformation;
import domain.Person;
import domain.Team;

import static data.datatype.PersonId.noPersonId;

class MutablePerson implements Person {

    private final PersonInformation personInformation;
    private Team team;

    public MutablePerson(PersonInformation personInformation) {
        this.personInformation = personInformation;
    }

    @Override final public PersonId getId() {
        return personInformation.getId();
    }

    @Override final public String name() {
        return personInformation.getName();
    }

    @Override final public String picture() {
        return personInformation.getPicture();
    }

    @Override public Team team() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
