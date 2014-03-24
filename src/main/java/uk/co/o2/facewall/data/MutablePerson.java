package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Team;

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

    @Override final public String role() {
        return personInformation.getRole();
    }

    @Override public Team team() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
