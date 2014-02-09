package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.dao.AdminDAO;
import uk.co.o2.facewall.data.dao.FacewallDAO;
import uk.co.o2.facewall.data.dao.QueryingDAO;
import uk.co.o2.facewall.data.dao.database.FacewallDB;
import uk.co.o2.facewall.data.dao.database.query.DatabaseQueryFactory;
import uk.co.o2.facewall.data.dao.database.query.FacewallQueryResultsMapper;
import uk.co.o2.facewall.data.dto.PersonInformationMapper;
import uk.co.o2.facewall.data.dto.TeamInformationMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public class DataModule {

    public final TeamRepository teamRepository;
    public final PersonRepository personRepository;
    public final AdminDAO adminDAO;

    private DataModule(QueryEngine<Map<String, Object>> queryEngine, GraphDatabaseService graphDatabaseService) {
        FacewallDB facewallDB = new FacewallDB(queryEngine);

        FacewallQueryResultsMapper queryResultsMapper = new FacewallQueryResultsMapper(new PersonInformationMapper(), new TeamInformationMapper());
        FacewallDAO facewallDAO = new FacewallDAO(new QueryingDAO(facewallDB), new DatabaseQueryFactory(queryResultsMapper));

        adminDAO = new AdminDAO(graphDatabaseService);
        teamRepository = new TeamRepository(facewallDAO, new TeamsFactory(adminDAO));
        personRepository = new PersonRepository(teamRepository, facewallDAO);
    }

    public static DataModule createDataModule(QueryEngine<Map<String, Object>> queryEngine, GraphDatabaseService graphDatabaseService) {
        return new DataModule(queryEngine, graphDatabaseService);
    }
}
