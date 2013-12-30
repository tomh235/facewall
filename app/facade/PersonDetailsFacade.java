package facade;

import data.PersonRepository;
import data.datatype.PersonId;
import domain.Person;
import facade.modelmapper.PersonDetailsModelMapper;
import model.PersonDetailsModel;

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
