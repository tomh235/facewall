package data;

import data.dao.FacewallDAO;
import data.dao.database.FacewallDB;
import data.factory.*;
import data.mapper.PersonDTOMapper;
import data.mapper.PersonNodeMapper;
import data.mapper.TeamDTOMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public class DataModule {

    public static Repository createRepository(GraphDatabaseService db, QueryEngine<Map<String, Object>> queryEngine) {
        FacewallDB facewallDB = new FacewallDB(queryEngine);

        PersonDTOMapper personDTOMapper = new PersonDTOMapper();
        TeamDTOMapper teamDTOMapper = new TeamDTOMapper();
        PersonNodeMapper personNodeMapper = new PersonNodeMapper();

        FacewallDAO facewallDAO = new FacewallDAO(facewallDB);

        LazyMutableTeamFactory lazyMutableTeamFactory = new LazyMutableTeamFactory(facewallDAO, personDTOMapper);
        LazyMutablePersonFactory lazyMutablePersonFactory = new LazyMutablePersonFactory(
            facewallDAO, lazyMutableTeamFactory, teamDTOMapper
        );

        MembersFactory membersFactory = new MembersFactory(personDTOMapper, lazyMutablePersonFactory);

        PersonFactory personFactory = new PersonFactory(personDTOMapper, teamDTOMapper, lazyMutableTeamFactory);
        TeamFactory teamFactory = new TeamFactory(teamDTOMapper, membersFactory);

        return new FacewallRepository(personFactory, teamFactory, facewallDAO);
    }
}
