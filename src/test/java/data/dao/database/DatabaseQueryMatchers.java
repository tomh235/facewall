package data.dao.database;

import data.dao.database.query.DatabaseQuery;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.ArgumentCaptor;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.util.QueryResultBuilder;

import java.util.Map;

import static data.dao.database.NodeIndex.Persons;
import static data.dao.database.NodeIndex.Teams;
import static data.dao.database.RelationshipTypes.TEAMMEMBER_OF;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class DatabaseQueryMatchers {

    private DatabaseQueryMatchers() {}

    public static Matcher<DatabaseQuery> aQueryForAllTeams() {
        return new TypeSafeMatcher<DatabaseQuery>() {
            @Override public boolean matchesSafely(DatabaseQuery databaseQuery) {
                String cypherStatement = captureCypherStatement(databaseQuery);

                return cypherStatement.contains(
                    "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":*'), " +
                        "team = node:" + Teams.getName() + "('" + Teams.getKey() + ":*') " +
                        "WHERE person-[:" + TEAMMEMBER_OF.name() + "]->team " +
                        "RETURN person, team"
                );
            }

            @Override public void describeTo(Description description) {
                description.appendText("a query for all teams.");
            }
        };
    }

    public static Matcher<DatabaseQuery> aQueryForTeamWithId(final String teamId) {
        return new TypeSafeMatcher<DatabaseQuery>() {
            @Override public boolean matchesSafely(DatabaseQuery databaseQuery) {
                String cypherStatement = captureCypherStatement(databaseQuery);

                return cypherStatement.contains(
                    "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":*'), " +
                        "team = node:" + Teams.getName() + "('" + Teams.getKey() + ":" + teamId + "') " +
                        "WHERE person-[:" + TEAMMEMBER_OF.name() + "]->team " +
                        "RETURN person, team"
                );
            }

            @Override public void describeTo(Description description) {
                description.appendText("a query for the team with the id ").appendValue(teamId);
            }
        };
    }

    public static Matcher<DatabaseQuery> aQueryForTeamsWithName(final String name) {
        return new TypeSafeMatcher<DatabaseQuery>() {
            @Override public boolean matchesSafely(DatabaseQuery databaseQuery) {
                String cypherStatement = captureCypherStatement(databaseQuery);

                return cypherStatement.contains(
                    "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":*'), " +
                        "team = node:" + Teams.getName() + "('" + Teams.getKey() + ":*') " +
                        "WHERE person-[:" + TEAMMEMBER_OF.name() + "]->team " +
                        "AND team.name =~ '" + name + "' " +
                        "RETURN person, team"
                );
            }

            @Override public void describeTo(Description description) {
                description.appendText("a query teams with a name matching ").appendValue(name);
            }
        };
    }

    public static Matcher<DatabaseQuery> aQueryForAllPersons() {
        return new TypeSafeMatcher<DatabaseQuery>() {
            @Override public boolean matchesSafely(DatabaseQuery databaseQuery) {
                String cypherStatement = captureCypherStatement(databaseQuery);

                return cypherStatement.contains(
                    "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":*') " +
                    "MATCH (person)-[r?]->(team) " +
                    "WHERE 1=1 " +
                    "RETURN person, team"
                );
            }

            @Override public void describeTo(Description description) {
                description.appendText("a query for all persons.");
            }
        };
    }

    public static Matcher<DatabaseQuery> aQueryForPersonsNamed(final String name) {
        return new TypeSafeMatcher<DatabaseQuery>() {
            @Override public boolean matchesSafely(DatabaseQuery databaseQuery) {
                String cypherStatement = captureCypherStatement(databaseQuery);

                return cypherStatement.contains(
                    "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":*') " +
                    "MATCH (person)-[r?]->(team) " +
                    "WHERE 1=1 " +
                    "AND person.name =~ '" + name + "' " +
                    "RETURN person, team"
                );
            }

            @Override public void describeTo(Description description) {
                description.appendText("a query persons with a name matching ").appendValue(name);
            }
        };
    }

    public static Matcher<DatabaseQuery> aQueryForPersonWithId(final String personId) {
        return new TypeSafeMatcher<DatabaseQuery>() {
            @Override public boolean matchesSafely(DatabaseQuery databaseQuery) {
                String cypherStatement = captureCypherStatement(databaseQuery);

                return cypherStatement.contains(
                    "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":" + personId + "') " +
                    "MATCH (person)-[r?]->(team) " +
                    "WHERE 1=1 " +
                    "RETURN person, team"
                );
            }

            @Override public void describeTo(Description description) {
                description.appendText("a query for a person with the id ").appendValue(personId);
            }
        };
    }

    private static String captureCypherStatement(DatabaseQuery databaseQuery) {
        QueryEngine mockEngine = mock(QueryEngine.class);
        when(mockEngine.query(anyString(), anyMap())).thenReturn(
            new QueryResultBuilder(emptyList())
        );

        databaseQuery.execute(mockEngine);

        ArgumentCaptor<String> cypherStatement = forClass(String.class);
        verify(mockEngine).query(cypherStatement.capture(), any(Map.class));
        return cypherStatement.getValue();
    }

}
