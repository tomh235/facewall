package data;

import data.dao.FacewallDAO;
import data.dao.TraversingDAO;
import data.dao.database.FacewallDB;
import data.factory.*;
import data.mapper.PersonDTOMapper;
import data.mapper.TeamDTOMapper;
import org.neo4j.graphdb.GraphDatabaseService;

public class DataModule {

    public static Repository createRepository(GraphDatabaseService db) {
        FacewallDB facewallDB = new FacewallDB(db);

        FacewallDAO facewallDAO = new FacewallDAO(facewallDB);
        TraversingDAO traversingDAO = new TraversingDAO(facewallDB);

        PersonDTOMapper personDTOMapper = new PersonDTOMapper();
        TeamDTOMapper teamDTOMapper = new TeamDTOMapper();

        LazyMutableTeamFactory lazyMutableTeamFactory = new LazyMutableTeamFactory(traversingDAO, personDTOMapper);
        LazyMutablePersonFactory lazyMutablePersonFactory = new LazyMutablePersonFactory(
            traversingDAO, lazyMutableTeamFactory, teamDTOMapper
        );

        MembersFactory membersFactory = new MembersFactory(personDTOMapper, lazyMutablePersonFactory);

        PersonFactory personFactory = new PersonFactory(personDTOMapper, teamDTOMapper, lazyMutableTeamFactory);
        TeamFactory teamFactory = new TeamFactory(teamDTOMapper, membersFactory);

        return new FacewallRepository(personFactory, teamFactory, facewallDAO);
    }
}
