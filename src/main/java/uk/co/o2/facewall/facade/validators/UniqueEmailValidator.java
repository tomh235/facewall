package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.data.PersonRepository;
import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.facade.validators.annotations.UniqueEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private PersonRepository personRepository;

    public UniqueEmailValidator(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return personRepository.findPersonById(PersonId.newPersonId(value))!=null;
    }
}
