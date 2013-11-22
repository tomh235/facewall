package data.factory;

import data.datatype.PersonId;
import domain.Person;
import domain.Team;

abstract class AbstractPerson implements Person {
    final PersonId id;
    final String name;
    final String picture;

    protected AbstractPerson(PersonId id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Deprecated
    @Override public String id() {
        throw new UnsupportedOperationException("This field is deprecated, and no longer supported");
    }

    @Override public String name() {
        return name;
    }

    @Override public String picture() {
        return picture;
    }

    @Override abstract public Team team();
}
