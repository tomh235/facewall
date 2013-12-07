package domain;

import java.util.Iterator;
import java.util.List;

public class Persons implements Iterable<Person> {

    private final List<Person> persons;

    private Persons(List<Person> persons) {
        this.persons = persons;
    }

    public static Persons newPersons(List<Person> personList) {
        return new Persons(personList);
    }

    @Override public Iterator<Person> iterator() {
        return persons.iterator();
    }
}
