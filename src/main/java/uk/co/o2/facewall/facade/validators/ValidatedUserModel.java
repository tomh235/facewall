package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.Team;

import java.util.Map;

public class ValidatedUserModel {

    private final PersonInformation personInformation;
    private final Team team;
    private Map<String, String> errorMap;

    public ValidatedUserModel(PersonInformation personInformation, Team team, Map<String, String> errorMap) {
        this.personInformation = personInformation;
        this.team = team;
        this.errorMap = errorMap;
    }

    public PersonInformation getPersonInformation() {
        return personInformation;
    }

    public Team getTeam() {
        return team;
    }

    public Map<String, String> getErrors() {
        return errorMap;
    }
}