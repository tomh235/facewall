package domain;

import data.datatype.PersonId;

public interface Person {
    String name();
    String picture();
    Team team();
    PersonId getId();
}
