package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.QueryResultRow;
import data.dao.database.RelationshipTypes;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.mapper.PersonNodeMapper;
import domain.Person;
import domain.Query;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static data.dao.database.query.PersonDatabaseQueryBuilder.forPersons;
import static data.dao.database.query.TeamDatabaseQueryBuilder.forTeams;

public class FacewallDAO {

    private final FacewallDB db;

    public FacewallDAO(FacewallDB facewallDB) {
        this.db = facewallDB;
    }

    public Iterable<PersonDTO> fetchPersons() {
        List<PersonDTO> dtos = new ArrayList<>();

        for (QueryResultRow row : db.query(forPersons())) {
            dtos.add(new PersonDTO(row.getPerson(), row.getTeam()));
        }
        return dtos;
    }

    public Iterable<TeamDTO> fetchTeams() {
        TeamDTOs dtos = new TeamDTOs();

        for (QueryResultRow row : db.query(forTeams())) {
            dtos.addMemberToTeam(row.getTeam(), row.getPerson());
        }
        return dtos;
    }

    public TeamDTO fetchTeam(TeamId teamId) {
        TeamDTOs dtos = new TeamDTOs();

        for (QueryResultRow row : db.query(forTeams().withId(teamId))) {
            dtos.addMemberToTeam(row.getTeam(), row.getPerson());
        }
        return dtos.getSingle();
    }

    //refactor for safety
    public PersonDTO fetchPerson(PersonId personId) {
        List<PersonDTO> dtos = new ArrayList<>();

        for (QueryResultRow row : db.query(forPersons().withId(personId))) {
            dtos.add(new PersonDTO(row.getPerson(), row.getTeam()));
        }
        return dtos.get(0);
    }
}
