package uk.co.o2.facewall.data.dao.database.query;

import uk.co.o2.facewall.data.dao.database.QueryResultRow;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.TeamInformation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FacewallQueryResults implements Iterable<QueryResultRow> {

    private List<QueryResultRow> resultRows = new ArrayList<>();

    public void add(PersonInformation person, TeamInformation team) {
        resultRows.add(new DefaultQueryResultRow(person, team));
    }

    @Override public Iterator<QueryResultRow> iterator() {
        return resultRows.iterator();
    }

    private static class DefaultQueryResultRow implements QueryResultRow {

        private final PersonInformation person;
        private final TeamInformation team;

        private DefaultQueryResultRow(PersonInformation person, TeamInformation team) {
            this.person = person;
            this.team = team;
        }

        @Override public PersonInformation getPerson() {
            return person;
        }

        @Override public TeamInformation getTeam() {
            return team;
        }
    }
}
