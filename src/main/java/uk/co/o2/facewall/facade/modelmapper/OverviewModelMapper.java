package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.model.OverviewEntryModel;

public class OverviewModelMapper {
    public OverviewEntryModel map(Person person) {
        String link = person.getId().value;
        return new OverviewEntryModel(person.team().name(), person.name(), person.picture(), person.team().colour(), link);
    }
}
