package uk.co.o2.facewall.data.dao;

import uk.co.o2.facewall.data.dao.database.query.DatabaseQueryFactory;
import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.data.datatype.TeamId;
import uk.co.o2.facewall.data.dto.PersonDTO;
import uk.co.o2.facewall.data.dto.TeamDTO;
import uk.co.o2.facewall.domain.Query;

public class FacewallDAO {

    private final QueryingDAO dao;
    private final DatabaseQueryFactory databaseQueryFactory;

    public FacewallDAO(QueryingDAO dao, DatabaseQueryFactory databaseQueryFactory) {
        this.dao = dao;
        this.databaseQueryFactory = databaseQueryFactory;
    }

    public Iterable<PersonDTO> fetchPersons() {
        return dao.queryPersons(databaseQueryFactory.forPersons());
    }

    public Iterable<TeamDTO> fetchTeams() {
        return dao.queryTeams(databaseQueryFactory.forTeams());
    }

    //refactor so that this is somewhat safer
    public PersonDTO fetchPerson(PersonId personId) {
        return dao.queryPersons(
                databaseQueryFactory.forPersons()
                        .withId(personId)
        ).iterator().next();
    }

    //refactor so that this is somewhat safer
    public TeamDTO fetchTeam(TeamId teamId) {
        return dao.queryTeams(
                databaseQueryFactory.forTeams()
                        .withId(teamId)
        ).iterator().next();
    }

    public TeamDTO fetchTeam(Query name) {
        return dao.queryTeams(
                databaseQueryFactory.forTeams()
                        .named(name.queryString())
        ).iterator().next();
    }

    public Iterable<PersonDTO> queryPersons(Query query) {
        return dao.queryPersons(
                databaseQueryFactory.forPersons()
                        .named(query.queryString())
        );
    }

    public Iterable<TeamDTO> queryTeams(Query query) {
        return dao.queryTeams(
                databaseQueryFactory.forTeams()
                        .named(query.queryString())
        );
    }
}
