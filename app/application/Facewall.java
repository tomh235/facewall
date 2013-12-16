package application;

import data.Repository;
import facade.OverviewFacade;
import facade.PersonDetailsFacade;
import facade.SearchFacade;
import facade.modelmapper.OverviewModelMapper;
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
    public final PersonDetailsFacade personDetailsFacade;

    private Facewall(
            OverviewFacade overviewFacade,
            SearchFacade searchFacade,
            PersonDetailsFacade personDetailsFacade
        )
    {
        this.overviewFacade = overviewFacade;
        this.searchFacade = searchFacade;
        this.personDetailsFacade = personDetailsFacade;
    }

    public static Facewall facewall() {
        return facewall;
    }

    private static Facewall createFacewall() {

        RestAPIFacade restAPIFacade = new RestAPIFacade("http://localhost:7474/db/data/");
        Repository repository = createRepository(new RestCypherQueryEngine(restAPIFacade));

        OverviewFacade overviewFacade = new OverviewFacade(repository, new OverviewModelMapper());

        SearchResultsModelMapper searchResultsModelMapper = new SearchResultsModelMapper();
        PersonDetailsModelMapper personDetailsModelMapper = new PersonDetailsModelMapper();
        TeamDetailsModelMapper teamDetailsModelMapper = new TeamDetailsModelMapper();

        PersonDetailsFacade personDetailsFacade = new PersonDetailsFacade(repository,personDetailsModelMapper);

        SearchFacade searchFacade = new SearchFacade(
            repository,
            searchResultsModelMapper,
            personDetailsModelMapper,
            teamDetailsModelMapper
        );
        return new Facewall(overviewFacade, searchFacade, personDetailsFacade);
    }
}
