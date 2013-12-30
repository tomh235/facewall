package data;

import data.dao.FacewallDAO;
import data.dao.QueryingDAO;
import data.dao.database.FacewallDB;
import data.dao.database.query.DatabaseQueryFactory;
import data.dao.database.query.FacewallQueryResultsMapper;
import data.dto.PersonInformationMapper;
import data.dto.TeamInformationMapper;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public class DataModule {

    public final TeamRepository teamRepository;
    public final PersonRepository personRepository;

    private DataModule(QueryEngine<Map<String, Object>> queryEngine) {
        FacewallDB facewallDB = new FacewallDB(queryEngine);

        FacewallQueryResultsMapper queryResultsMapper = new FacewallQueryResultsMapper(new PersonInformationMapper(), new TeamInformationMapper());
        FacewallDAO facewallDAO = new FacewallDAO(new QueryingDAO(facewallDB), new DatabaseQueryFactory(queryResultsMapper));

        teamRepository = new TeamRepository(facewallDAO);
        personRepository = new PersonRepository(teamRepository, facewallDAO);
    }

    public static DataModule createDataModule(QueryEngine<Map<String, Object>> queryEngine) {
        return new DataModule(queryEngine);
    }
}
