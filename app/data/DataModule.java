package data;

import data.dao.FacewallDAO;
import data.dao.TraversingDAO;
import data.dao.database.FacewallDB;
import data.factory.*;
import data.mapper.PersonMapper;
import data.mapper.TeamMapper;
import org.neo4j.graphdb.GraphDatabaseService;

public class DataModule {

    public static Repository createRepository(GraphDatabaseService db) {
        FacewallDB facewallDB = new FacewallDB(db);

        FacewallDAO facewallDAO = new FacewallDAO(facewallDB);
        TraversingDAO traversingDAO = new TraversingDAO(facewallDB);

        PersonMapper personMapper = new PersonMapper();
        TeamMapper teamMapper = new TeamMapper();

        LazyMutableTeamFactory lazyMutableTeamFactory = new LazyMutableTeamFactory(traversingDAO, personMapper);
        LazyMutablePersonFactory lazyMutablePersonFactory = new LazyMutablePersonFactory(
            traversingDAO, lazyMutableTeamFactory, teamMapper
        );

        MembersFactory membersFactory = new MembersFactory(personMapper, lazyMutablePersonFactory);

        PersonFactory personFactory = new PersonFactory(personMapper, teamMapper, lazyMutableTeamFactory);
        TeamFactory teamFactory = new TeamFactory(teamMapper, membersFactory);

        return new FacewallRepository(personFactory, teamFactory, facewallDAO);
    }
}
