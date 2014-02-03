package domain;

import java.util.List;

public interface Team {
    String name();
    String colour();
    List<Person> members();

    void addMember(Person member);
}
