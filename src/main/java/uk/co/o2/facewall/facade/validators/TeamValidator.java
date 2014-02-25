package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.facade.validators.annotations.ValidTeam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static uk.co.o2.facewall.domain.NoTeam.noTeam;

public class TeamValidator implements ConstraintValidator<ValidTeam, Team> {
    @Override
    public void initialize(ValidTeam constraintAnnotation) {
    }

    @Override
    public boolean isValid(Team team, ConstraintValidatorContext context) {
        return !team.equals(noTeam());
    }
}
