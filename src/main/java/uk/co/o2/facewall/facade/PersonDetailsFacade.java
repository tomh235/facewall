package uk.co.o2.facewall.facade;

import uk.co.o2.facewall.data.PersonRepository;
import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.facade.modelmapper.PersonDetailsModelMapper;
import uk.co.o2.facewall.model.PersonDetailsModel;

public class PersonDetailsFacade {

    private final PersonRepository repository;
    private final PersonDetailsModelMapper personDetailsModelMapper;

    public PersonDetailsFacade(PersonRepository repository, PersonDetailsModelMapper personDetailsModelMapper) {
        this.repository = repository;
        this.personDetailsModelMapper = personDetailsModelMapper;
    }

    public PersonDetailsModel createPersonDetailsModel(PersonId id) {
       Person person = repository.findPersonById(id);
       return personDetailsModelMapper.map(person);
    }
}
