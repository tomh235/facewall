package facade;

import data.Repository;
import data.datatype.PersonId;
import domain.Person;
import facade.modelmapper.PersonDetailsModelMapper;
import model.PersonDetailsModel;

public class PersonDetailsFacade {

    private final Repository repository;
    private final PersonDetailsModelMapper personDetailsModelMapper;

    public PersonDetailsFacade(Repository repository, PersonDetailsModelMapper personDetailsModelMapper) {
        this.repository = repository;
        this.personDetailsModelMapper = personDetailsModelMapper;
    }

    public PersonDetailsModel createPersonDetailsModel(PersonId id) {
       Person person = repository.findPersonById(id);
       PersonDetailsModel resultsModel = personDetailsModelMapper.map(person);
       return resultsModel;
    }
}
