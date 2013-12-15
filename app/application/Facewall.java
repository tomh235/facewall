package application;

import data.Repository;
import facade.OverviewFacade;
import facade.SearchFacade;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import facade.modelmapper.TeamDetailsModelMapper;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;

import static data.DataModule.createRepository;

final public class Facewall {

    private static Facewall facewall = createFacewall();

    public final OverviewFacade overviewFacade;
    public final SearchFacade searchFacade;

    private Facewall(
        OverviewFacade overviewFacade,
        SearchFacade searchFacade) {
        this.overviewFacade = overviewFacade;
        this.searchFacade = searchFacade;
    }

    public static Facewall facewall() {
        return facewall;
    }

    private static Facewall createFacewall() {

        System.setProperty("org.neo4j.rest.logging_filter", "true");

        RestAPIFacade restAPIFacade = new RestAPIFacade("http://localhost:7474/db/data/");
        Repository repository = createRepository(new RestCypherQueryEngine(restAPIFacade));

        OverviewFacade overviewFacade = new OverviewFacade(repository);

        SearchResultsModelMapper searchResultsModelMapper = new SearchResultsModelMapper();
        PersonDetailsModelMapper personDetailsModelMapper = new PersonDetailsModelMapper();
        TeamDetailsModelMapper teamDetailsModelMapper = new TeamDetailsModelMapper();

        SearchFacade searchFacade = new SearchFacade(
            repository,
            searchResultsModelMapper,
            personDetailsModelMapper,
            teamDetailsModelMapper
        );
        return new Facewall(overviewFacade, searchFacade);
    }
}
