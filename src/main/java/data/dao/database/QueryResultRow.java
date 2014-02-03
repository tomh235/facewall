package data.dao.database;

import data.dto.PersonInformation;
import data.dto.TeamInformation;

public interface QueryResultRow {
    PersonInformation getPerson();
    TeamInformation getTeam();
}
