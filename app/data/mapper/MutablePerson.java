package data.mapper;

import data.datatype.PersonId;
import domain.Person;
import domain.Team;

import static data.datatype.PersonId.noPersonId;

abstract public class MutablePerson implements Person {

    protected PersonId id = noPersonId();
    protected String name = "";
    protected String picture = "";

    protected MutablePerson() {}

    final public void setId(PersonId personId) {
        this.id = personId;
    }

    @Override final public String name() {
        return name;
    }

    final public void setName(String name) {
        this.name = name;
    }

    @Override final public String picture() {
        return picture;
    }

    final public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override abstract public Team team();
}
