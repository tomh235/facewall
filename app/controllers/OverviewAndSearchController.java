package controllers;

import data.FacewallScalaRepo;
import data.ScalaRepository;
import domain.Query;
import facade.OverviewFacade;
import facade.SearchFacade;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import facade.modelmapper.TeamDetailsModelMapper;
import model.DefaultSearchResultsModel;
import model.PersonDetailsModel;
import model.SearchResultsModel;
import model.TeamDetailsModel;
import play.mvc.Controller;
import play.mvc.Result;
import requestmapper.SearchQueryMapper;

//Split these up into two controllers
public class OverviewAndSearchController extends Controller {
    private static final ScalaRepository repo = new FacewallScalaRepo();
    private static final OverviewFacade overviewFacade = new OverviewFacade(repo);
    private static final SearchFacade searchFacade = new SearchFacade(repo, new SearchResultsModelMapper(), new PersonDetailsModelMapper(), new TeamDetailsModelMapper());
    private static final SearchQueryMapper searchQueryMapper = new SearchQueryMapper();

    public static Result overview() {
        return ok(views.html.overview.render(overviewFacade.createOverviewModel()));
    }

    public static Result search() {
        return ok(views.html.search.render());
    }

    public static Result searchResults() {
        Result result;
        Query query = searchQueryMapper.map(request());
        SearchResultsModel searchResultsModel = searchFacade.createSearchResultsModel(query);

        //This looks like scala code that has been translated into java. That's fine, but this kind of java code
        //should be avoided if possible.
        if(searchResultsModel instanceof DefaultSearchResultsModel) {
            result = ok(views.html.searchresults.render((DefaultSearchResultsModel) searchResultsModel));
        } else if(searchResultsModel instanceof PersonDetailsModel) {
            result = ok(views.html.persondetails.render((PersonDetailsModel) searchResultsModel));
        } else if(searchResultsModel instanceof TeamDetailsModel) {
            result = ok(views.html.teamdetails.render((TeamDetailsModel) searchResultsModel));
        } else {
            result = internalServerError("Query not recognized");
        }
        return result;
    }
}
