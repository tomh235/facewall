package controllers;

import data.FacewallScalaRepo;
import data.ScalaRepository;
import domain.Query;
import facade.OverviewFacade;
import facade.SearchFacade;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import model.DefaultSearchResultsModel;
import model.PersonDetailsModel;
import model.SearchResultsModel;
import play.mvc.Controller;
import play.mvc.Result;
import requestmapper.QueryMapper;

public class OverviewAndSearchController extends Controller {
    private static final ScalaRepository repo = new FacewallScalaRepo();
    private static final OverviewFacade overviewFacade = new OverviewFacade(repo);
    private static final SearchFacade searchFacade = new SearchFacade(repo, new SearchResultsModelMapper(), new PersonDetailsModelMapper());
    private static final QueryMapper queryMapper = new QueryMapper();

    public static Result overview() {
        return ok(views.html.overview.render(overviewFacade.createOverviewModel()));
    }

    public static Result search() {
        return ok(views.html.search.render());
    }

    public static Result searchResults(String queryparams) {
        Query query = queryMapper.map(request());
        SearchResultsModel searchResultsModel = searchFacade.createSearchResultsModel(query);
        if(searchResultsModel instanceof DefaultSearchResultsModel) {
            return ok(views.html.searchresults.render((DefaultSearchResultsModel) searchResultsModel));
        }
        else if(searchResultsModel instanceof PersonDetailsModel) {
            return ok(views.html.persondetails.render((PersonDetailsModel) searchResultsModel));
        }
        return internalServerError("Query not recognized");
    }
}
