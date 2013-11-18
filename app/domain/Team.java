package domain;

import java.util.List;

public interface Team {
    //The id shouldn't be exposed, it's purely a database concern
    String id();

    String name();
    String colour();
    List<Person> members();
}
