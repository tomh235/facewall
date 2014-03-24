package uk.co.o2.facewall.databaseutils.fixture;

import java.util.ArrayList;
import java.util.List;

import static uk.co.o2.facewall.databaseutils.fixture.PersonData.newPersonData;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static java.util.concurrent.ThreadLocalRandom.current;

abstract public class PersonDataFactory {

    private static final List<String> firstNames = asList(
        "Bertrand",
        "Friedrich",
        "Jonathan",
        "Rene",
        "David",
        "Ludwig",
        "Donald",
        "Hilary",
        "Immanuel",
        "George",
        "John",
        "Gottfried",
        "George Edward",
        "Gottlob",
        "Willard",
        "Rudolf"
    );

    private static final List<String> lastNames = asList(
        "Russell",
        "Nietzshe",
        "Searle",
        "Descartes",
        "Hume",
        "Wittgenstein",
        "Davidson",
        "Putnam",
        "Kant",
        "Berkeley",
        "Locke",
        "Leibniz",
        "Moore",
        "Frege",
        "Quine",
        "Carnap"
    );

    private static final List<String> roles = asList(
        "Happy",
        "Space",
        "Black",
        "Negative",
        "Positive",
        "Cynical",
        "Daring",
        "Useless",
        "Crazy",
        "Outcast"
    );


    private PersonDataFactory() {
    }

    public static List<PersonData.Builder> defaultPersons(int number) {
        List<PersonData.Builder> result = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            result.add(defaultPerson());
        }
        return result;
    }

    public static PersonData.Builder defaultPerson() {
        final String name = randomName();
        final String role = randomRole();


        return newPersonData()
            .withProperty("id", uniqueEmail())
            .withProperty("name", name)
            .withProperty("picture", "http://dummyimage.com/200x200/000/fff.png&text=" + name)
            .withProperty("role", role);
    }

    private static String randomRole() {
        return roles.get(randomInt(roles.size())) + " Philosopher";

    }

    private static String randomName() {
        String firstName = firstNames.get(randomInt(firstNames.size()));
        String lastName = lastNames.get(randomInt(lastNames.size()));
        return firstName + " " + lastName;
    }

    private static int randomInt(int bound) {
        return current().nextInt(bound);
    }

    //TODO: implement name based unique email
    private static String uniqueEmail() {
        return randomUUID() + "@email.com";
    }
}
