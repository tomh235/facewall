package uk.co.o2.facewall.data.dao.database;

import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.TeamInformation;

public interface QueryResultRow {
    PersonInformation getPerson();
    TeamInformation getTeam();
}
