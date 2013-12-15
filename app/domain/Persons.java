package domain;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static domain.NoTeam.noTeam;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Collections.sort;

public class Persons implements Iterable<Person> {

    private static final Comparator<Person> teamNameThenName = new TeamNameThenNamePersonComparator();

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

    public void sortByTeamNameThenName() {
        sort(persons, teamNameThenName);
    }

    private static class TeamNameThenNamePersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person person1, Person person2) {

            int result = compareLackOfTeams(person1, person2);
            if (result == 0) {

                result = CASE_INSENSITIVE_ORDER.compare(person1.team().name(), person2.team().name());
                if (result == 0) {

                    result = CASE_INSENSITIVE_ORDER.compare(person1.name(), person2.name());
                }
            }
            return result;
        }

        private static int compareLackOfTeams(Person person1, Person person2) {
            int result = 0;

            if (person1.team() == noTeam()) {
                result++;
            }

            if (person2.team() == noTeam()) {
                result--;
            }
            return result;
        }
    }
}
