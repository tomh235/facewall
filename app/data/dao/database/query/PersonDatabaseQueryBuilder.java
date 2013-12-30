package data.dao.database.query;

import data.datatype.PersonId;
import domain.datatype.QueryString;

import java.util.HashMap;
import java.util.Map;

public class PersonDatabaseQueryBuilder implements DatabaseQueryBuilder {

    private final FacewallQueryResultsMapper queryResultsMapper;

    private String id = "*";
    private Map<String, String> propertyCriteria = new HashMap<>();

    PersonDatabaseQueryBuilder(FacewallQueryResultsMapper queryResultsMapper) {
        this.queryResultsMapper = queryResultsMapper;
    }

    public PersonDatabaseQueryBuilder withId(PersonId id) {
        this.id = id.value;
        return this;
    }

    public PersonDatabaseQueryBuilder named(QueryString queryString) {
        propertyCriteria.put("name", queryString.value);
        return this;
    }

    public DatabaseQuery build() {
        return new PersonDatabaseQuery(queryResultsMapper, id, propertyCriteria);
    }
}
