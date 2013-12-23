package data;

import data.dao.FacewallDAO;
import data.dao.database.FacewallDB;
import data.dto.PersonInformationMapper;
import data.dto.TeamInformationMapper;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public class DataModule {

    public final TeamRepository teamRepository;
    public final PersonRepository personRepository;

    private DataModule(QueryEngine<Map<String, Object>> queryEngine) {
        FacewallDB facewallDB = new FacewallDB(queryEngine);

        PersonInformationMapper personInformationMapper = new PersonInformationMapper();
        TeamInformationMapper teamInformationMapper = new TeamInformationMapper();
        FacewallDAO facewallDAO = new FacewallDAO(facewallDB, personInformationMapper, teamInformationMapper);

        teamRepository = new TeamRepository(facewallDAO);
        personRepository = new PersonRepository(teamRepository, facewallDAO);
    }

    public static DataModule createDataModule(QueryEngine<Map<String, Object>> queryEngine) {
        return new DataModule(queryEngine);
    }

    public static Repository createRepository(QueryEngine<Map<String, Object>> queryEngine) {
        FacewallDB facewallDB = new FacewallDB(queryEngine);

        PersonInformationMapper personInformationMapper = new PersonInformationMapper();
        TeamInformationMapper teamInformationMapper = new TeamInformationMapper();
        FacewallDAO facewallDAO = new FacewallDAO(facewallDB, personInformationMapper, teamInformationMapper);

        return new FacewallRepository(facewallDAO);
    }
}
