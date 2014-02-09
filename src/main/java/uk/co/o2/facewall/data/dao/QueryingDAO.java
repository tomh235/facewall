package uk.co.o2.facewall.data.dao;

import uk.co.o2.facewall.data.dao.database.FacewallDB;
import uk.co.o2.facewall.data.dao.database.QueryResultRow;
import uk.co.o2.facewall.data.dao.database.query.DatabaseQueryBuilder;
import uk.co.o2.facewall.data.dto.PersonDTO;
import uk.co.o2.facewall.data.dto.TeamDTO;

import java.util.ArrayList;
import java.util.List;

public class QueryingDAO {

    private final FacewallDB db;

    public QueryingDAO(FacewallDB facewallDB) {
        this.db = facewallDB;
    }

    public Iterable<PersonDTO> queryPersons(DatabaseQueryBuilder query) {
        List<PersonDTO> dtos = new ArrayList<>();

        for (QueryResultRow row : db.query(query)) {
            dtos.add(new PersonDTO(row.getPerson(), row.getTeam()));
        }
        return dtos;
    }

    public Iterable<TeamDTO> queryTeams(DatabaseQueryBuilder query) {
        TeamDTOs dtos = new TeamDTOs();

        for (QueryResultRow row : db.query(query)) {
            dtos.addMemberToTeam(row.getTeam(), row.getPerson());
        }
        return dtos;
    }
}
