package facewall.database.fixture;

import java.util.ArrayList;
import java.util.List;

import static facewall.database.fixture.PersonDataFactory.defaultPersons;
import static facewall.database.fixture.TeamData.newTeamData;
import static java.lang.Integer.toHexString;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static java.util.concurrent.ThreadLocalRandom.current;

public class TeamDataFactory {

    public static List<TeamData.Builder> defaultTeams(int number) {
        List<TeamData.Builder> result = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            result.add(defaultTeamWithDefaultMembers());
        }
        return result;
    }

    public static TeamData.Builder defaultTeamWithDefaultMembers() {
        return defaultTeam()
            .withMembers(defaultPersons(1 + randomInt(14)));
    }

    public static TeamData.Builder defaultTeam() {
        return newTeamData()
            .withProperty("id", randomUUID().toString())
            .withProperty("name", randomName())
            .withProperty("colour", randomColour());
    }

    private static String randomName() {
        return teamNames.get(randomInt(teamNames.size()));
    }

    private static String randomColour() {
        int randomRange = 0xffffff - 0x999999;
        int randomInt = randomInt(randomRange);

        return toHexString(randomInt + 0x999999);
    }

    private static int randomInt(int bound) {
        return current().nextInt(bound);
    }

    private static final List<String> teamNames = asList(
        "The Elfin Chickens",
        "The Momentous Ants",
        "The Jagged Dugongs",
        "The Changeable Ferrets",
        "The Sordid Horses",
        "The Stupid Baboons",
        "The Wet Hyenas",
        "The Nonstop Gooses",
        "The Brawny Meerkats",
        "The Wiggly Pigs",
        "The Substantial Crocodiles",
        "The Tiresome Tigers",
        "The Daily Cheetahs",
        "The Boundless Cockroaches",
        "The Tasteless Ponies",
        "The Entertaining Deers",
        "The Understood Minks",
        "The Safe Chickens",
        "The Sleepy Ants",
        "The Uneven Dugongs",
        "The Elfin Ferrets",
        "The Momentous Horses",
        "The Jagged Baboons",
        "The Changeable Hyenas",
        "The Sordid Gooses",
        "The Stupid Meerkats",
        "The Wet Pigs",
        "The Nonstop Crocodiles",
        "The Brawny Tigers",
        "The Wiggly Cheetahs",
        "The Substantial Cockroaches",
        "The Tiresome Ponies",
        "The Daily Partridges",
        "The Boundless Ducks",
        "The Tasteless Alligators",
        "The Six Skunks",
        "The Stiff Rabbits",
        "The Clumsy Cougars",
        "The Unaccountable Ostriches",
        "The Lewd Snails",
        "The Gamy Foxes",
        "The Ultra Leopards",
        "The Obedient Deers",
        "The Unequal Pigeons",
        "The Domineering Dragonflies",
        "The Mere Finches",
        "The Hurt Boars",
        "The Dusty Anteaters",
        "The Spurious Whales",
        "The Terrible Penguins",
        "The Windy Magpies",
        "The White Walruses",
        "The Homeless Jellyfishes",
        "The Soggy Mules",
        "The Obsolete Lobsters",
        "The Complete Flies",
        "The Efficient Monkeys",
        "The Numerous Louses",
        "The Outrageous Stinkbugs",
        "The Outstanding Turkeys",
        "The Auspicious Hamsters",
        "The Dark Dogfishes",
        "The Breakable Eagles",
        "The Combative Sheep"
    );
}
