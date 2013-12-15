package domain;

import java.util.List;

public class MockTeam implements Team {
    public final String name;
    public final String colour;
    public List<Person> members;

    public String name() {
        return name;
    }

    public String colour() {
        return colour;
    }

    public List<Person> members() {
        return members;
    }

    public MockTeam(String name, String colour, List<Person> members) {
        this.name = name;
        this.colour = colour;
        this.members = members;
    }
}

