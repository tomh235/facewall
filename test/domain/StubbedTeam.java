package domain;

import java.util.List;

public class StubbedTeam implements Team {
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

    @Override
    public void addMember(Person member) {

    }

    public StubbedTeam(String name, String colour, List<Person> members) {
        this.name = name;
        this.colour = colour;
        this.members = members;
    }
}

