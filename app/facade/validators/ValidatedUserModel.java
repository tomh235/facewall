package facade.validators;

import data.dto.PersonInformation;
import domain.Team;

public class ValidatedUserModel {
    private final PersonInformation personInformation;
    private final Team team;

    public ValidatedUserModel(PersonInformation personInformation, Team team) {
        this.personInformation = personInformation;
        this.team = team;
    }

    public PersonInformation getPersonInformation() {
        return personInformation;
    }

    public Team getTeam() {
        return team;
    }
}
