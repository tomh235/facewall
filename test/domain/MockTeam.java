package domain;

import java.util.List;

public class MockTeam implements Team{
    public final String id;
    public final String name;
    public final String colour;
    public final List<Person> members;

    public String id(){
        return id;
    }
    public String name(){
        return name;
    }
    public String colour(){
        return colour;
    }
    public List<Person> members(){
        return members;
    }

    public MockTeam(String id, String name, String colour, List<Person> members) {
        this.id = id;
        this.name = name;
        this.colour = colour;
        this.members = members;
    }
}

