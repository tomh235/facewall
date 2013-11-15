package facade.modelmapper;

import domain.Person;

public class OverviewModelMapper {
    public OverviewEntryModel map(Person person){
        return new OverviewEntryModel(person.team().name(), person.name(), person.picture(), person.team().colour());
    }
}
