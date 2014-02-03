package facade;

import data.PersonRepository;
import domain.Person;
import domain.Persons;
import facade.modelmapper.OverviewModelMapper;
import model.OverviewEntryModel;

import java.util.ArrayList;
import java.util.List;

import static domain.Persons.newPersons;

public class OverviewFacade {

    private final PersonRepository repository;
    private final OverviewModelMapper overviewModelMapper;

    public OverviewFacade(PersonRepository repository, OverviewModelMapper overviewModelMapper) {
        this.repository = repository;
        this.overviewModelMapper = overviewModelMapper;
    }

    public List<OverviewEntryModel> createOverviewModel() {
        List<OverviewEntryModel> overviewEntryModels = new ArrayList<>();

        Persons persons = newPersons(repository.listPersons());
        persons.sortByTeamNameThenName();

        for(Person person : persons) {
            overviewEntryModels.add(overviewModelMapper.map(person));
        }
        return overviewEntryModels;
    }
}
