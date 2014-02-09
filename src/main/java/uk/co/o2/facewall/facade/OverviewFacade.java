package uk.co.o2.facewall.facade;

import uk.co.o2.facewall.data.PersonRepository;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Persons;
import uk.co.o2.facewall.facade.modelmapper.OverviewModelMapper;
import uk.co.o2.facewall.model.OverviewEntryModel;

import java.util.ArrayList;
import java.util.List;

import static uk.co.o2.facewall.domain.Persons.newPersons;

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
