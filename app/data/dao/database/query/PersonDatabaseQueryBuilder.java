package data.dao.database.query;

import data.datatype.PersonId;

public class PersonDatabaseQueryBuilder implements DatabaseQueryBuilder {

    protected String id = "*";

    private PersonDatabaseQueryBuilder() {}

    public static PersonDatabaseQueryBuilder forPersons() {
        return new PersonDatabaseQueryBuilder();
    }

    public PersonDatabaseQueryBuilder withId(PersonId id) {
        this.id = id.value;
        return this;
    }

    public DatabaseQuery build() {
        return new PersonDatabaseQuery(id);
    }
}
