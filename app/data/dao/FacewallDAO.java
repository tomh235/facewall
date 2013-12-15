package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.QueryResultRow;
import data.dao.database.query.DatabaseQueryBuilder;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import domain.Query;

import java.util.ArrayList;
import java.util.List;

import static data.dao.database.query.PersonDatabaseQueryBuilder.forPersons;
import static data.dao.database.query.TeamDatabaseQueryBuilder.forTeams;

public class FacewallDAO {

    private final FacewallDB db;

    public FacewallDAO(FacewallDB facewallDB) {
        this.db = facewallDB;
    }

    public Iterable<PersonDTO> fetchPersons() {
        return queryPersons(forPersons());
    }

    public Iterable<TeamDTO> fetchTeams() {
        return queryTeams(forTeams());
    }

    //refactor so that this is somewhat safer
    public PersonDTO fetchPerson(PersonId personId) {
        return queryPersons(
            forPersons()
                .withId(personId)
        ).get(0);
    }

    public TeamDTO fetchTeam(TeamId teamId) {
        return queryTeams(forTeams()
            .withId(teamId)
        ).getSingle();
    }

    public Iterable<PersonDTO> queryPersons(Query query) {
        return queryPersons(
            forPersons().named(query.queryString())
        );
    }

    public Iterable<TeamDTO> queryTeams(Query query) {
        return queryTeams(
            forTeams().named(query.queryString())
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
