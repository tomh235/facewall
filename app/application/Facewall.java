package application;

import data.DataModule;
import data.PersonRepository;
import data.TeamRepository;
import data.dao.AdminDAO;
import facade.OverviewFacade;
import facade.PersonDetailsFacade;
import facade.SearchFacade;
import facade.SignUpFacade;
import facade.modelmapper.OverviewModelMapper;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import facade.modelmapper.TeamDetailsModelMapper;
import facade.validators.UserModelValidator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;

import static data.DataModule.createDataModule;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

final public class Facewall {

    private static Facewall facewall = createFacewall();

    public final OverviewFacade overviewFacade;
    public final SearchFacade searchFacade;
    public final PersonDetailsFacade personDetailsFacade;
    public final SignUpFacade signUpFacade;
    public final UserModelValidator userModelValidator;

    private Facewall(
            OverviewFacade overviewFacade,
            SearchFacade searchFacade,
            PersonDetailsFacade personDetailsFacade,
            SignUpFacade signUpFacade, UserModelValidator userModelValidator)
    {
        this.overviewFacade = overviewFacade;
        this.searchFacade = searchFacade;
        this.personDetailsFacade = personDetailsFacade;
        this.signUpFacade = signUpFacade;
        this.userModelValidator = userModelValidator;
    }

    public static Facewall facewall() {
        return facewall;
    }

    private static Facewall createFacewall() {

        RestCypherQueryEngine queryEngine = new RestCypherQueryEngine(new RestAPIFacade("http://localhost:7474/db/data/"));
        GraphDatabaseService graphDatabaseService = databaseFor("http://localhost:7474/db/data/");

        DataModule dataModule = createDataModule(queryEngine, graphDatabaseService);
        PersonRepository personRepository = dataModule.personRepository;
        TeamRepository teamRepository = dataModule.teamRepository;
        AdminDAO adminDAO = dataModule.adminDAO;

        OverviewFacade overviewFacade = new OverviewFacade(personRepository, new OverviewModelMapper());

        SearchResultsModelMapper searchResultsModelMapper = new SearchResultsModelMapper();
        PersonDetailsModelMapper personDetailsModelMapper = new PersonDetailsModelMapper();
        TeamDetailsModelMapper teamDetailsModelMapper = new TeamDetailsModelMapper();

        PersonDetailsFacade personDetailsFacade = new PersonDetailsFacade(personRepository, personDetailsModelMapper);

        SearchFacade searchFacade = new SearchFacade(
            personRepository,
            teamRepository,
            searchResultsModelMapper,
            personDetailsModelMapper,
            teamDetailsModelMapper
        );

        SignUpFacade signUpFacade = new SignUpFacade(adminDAO, personRepository);
        UserModelValidator userModelValidator = new UserModelValidator(teamRepository);

        return new Facewall(overviewFacade, searchFacade, personDetailsFacade, signUpFacade, userModelValidator);
    }
}
