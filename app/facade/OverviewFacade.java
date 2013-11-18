package facade;

import domain.Person;
import facade.modelmapper.OverviewModelMapper;
import model.OverviewEntryModel;
import data.ScalaRepository;

import java.util.*;

public class OverviewFacade {

    private final ScalaRepository repository;

    public OverviewFacade(ScalaRepository repository) {
        this.repository = repository;
    }

    private class TeamNameThenNameComparator implements Comparator<Person> {
        @Override
        public int compare(Person person1, Person person2) {
            String person1TeamName = person1.team().name();
            String person2TeamName = person2.team().name();

            if (person1TeamName.equals("")) {
                return -1;
            }
            int namesCompared = person1TeamName.compareTo(person2TeamName);
            if (namesCompared == 0) {
                return namesCompared;
            }
            else {
                String person1Name = person1.name();
                String person2Name = person2.name();
                return person1Name.compareTo(person2Name);
            }
        }
    }

    private List<Person> sortAllPersons() {
        List<Person> personList = repository.listPersons();
        List<Person> sortableList = new ArrayList<>(personList);
        Collections.sort(sortableList, new TeamNameThenNameComparator());
        return sortableList;
    }

    public List<OverviewEntryModel> createOverviewModel() {
        List<Person> personList = sortAllPersons();
        OverviewModelMapper overviewModelMapper = new OverviewModelMapper();
        List<OverviewEntryModel> overviewEntryModelList = new ArrayList <> ();

        for(Person person : personList) {
           overviewEntryModelList.add(overviewModelMapper.map(person));
        }
        return overviewEntryModelList;
    }
}
