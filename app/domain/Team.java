package domain;

import java.util.List;

public interface Team {
    String id();
    String name();
    String colour();
    List<Person> members();
}
