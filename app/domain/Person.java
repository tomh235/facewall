package domain;

import data.datatype.PersonId;

public interface Person {
    String name();
    String picture();
    String email();
    Team team();
    PersonId getId();
}
