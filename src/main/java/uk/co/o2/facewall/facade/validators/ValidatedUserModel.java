package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.facade.validators.annotations.ValidTeam;

import javax.validation.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ValidatedUserModel {

    @Valid
    private final PersonInformation personInformation;
    @ValidTeam
    private final Team team;

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

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

    public Map<String, String> getErrors() {
        Map<String, String> errorMap = new HashMap<>();

        for (ConstraintViolation<ValidatedUserModel> thisError : validator.validate(this)) {
            errorMap.put(thisError.getPropertyPath().toString(), thisError.getMessage());
        }
        return errorMap;
    }

    public boolean hasErrors() {
        return !validator.validate(this).isEmpty();
    }
}