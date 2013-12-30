package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.QueryResultRow;
import data.dao.database.query.DatabaseQueryBuilder;
import data.dao.database.query.DatabaseQueryFactory;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import domain.Query;

import java.util.ArrayList;
import java.util.List;

public class FacewallDAO {

    private final FacewallDB db;
    private final DatabaseQueryFactory databaseQueryFactory;

    public FacewallDAO(FacewallDB facewallDB, DatabaseQueryFactory databaseQueryFactory) {
        this.db = facewallDB;
        this.databaseQueryFactory = databaseQueryFactory;
    }

    public Iterable<PersonDTO> fetchPersons() {
        return queryPersons(databaseQueryFactory.forPersons());
    }

    public Iterable<TeamDTO> fetchTeams() {
        return queryTeams(databaseQueryFactory.forTeams());
    }

    //refactor so that this is somewhat safer
    public PersonDTO fetchPerson(PersonId personId) {
        return queryPersons(
                databaseQueryFactory.forPersons()
                        .withId(personId)
        ).get(0);
    }

    public TeamDTO fetchTeam(TeamId teamId) {
        return queryTeams(
            databaseQueryFactory.forTeams()
                .withId(teamId)
        ).getSingle();
    }

    public Iterable<PersonDTO> queryPersons(Query query) {
        return queryPersons(
                databaseQueryFactory.forPersons()
                        .named(query.queryString())
        );
    }

    public Iterable<TeamDTO> queryTeams(Query query) {
        return queryTeams(
                databaseQueryFactory.forTeams()
                        .named(query.queryString())
        );
    }

    private List<PersonDTO> queryPersons(DatabaseQueryBuilder query) {
        List<PersonDTO> dtos = new ArrayList<>();

        for (QueryResultRow row : db.query(query)) {
            dtos.add(new PersonDTO(row.getPerson(), row.getTeam()));
        }
        return dtos;
    }

    private TeamDTOs queryTeams(DatabaseQueryBuilder query) {
        TeamDTOs dtos = new TeamDTOs();

        for (QueryResultRow row : db.query(query)) {
            dtos.addMemberToTeam(row.getTeam(), row.getPerson());
        }
        return dtos;
    }
}
