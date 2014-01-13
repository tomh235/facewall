package controllers;

import domain.Query;
import facade.SearchFacade;
import model.DefaultSearchResultsModel;
import model.PersonDetailsModel;
import model.SearchResultsModel;
import model.TeamDetailsModel;
import play.mvc.Controller;
import play.mvc.Result;
import requestmapper.SearchQueryMapper;

import static application.Facewall.facewall;
import static util.freemarker.TemplateHelper.view;

public class SearchController extends Controller {
    private static final SearchQueryMapper searchQueryMapper = new SearchQueryMapper();
    private static final SearchFacade searchFacade = facewall().searchFacade;

    public static Result search() {
        return ok(view("search.ftl"));
    }

    public static Result searchResults() {
        Result result;
        Query query = searchQueryMapper.map(request());
        SearchResultsModel searchResultsModel = searchFacade.createSearchResultsModel(query);

        //This looks like scala code that has been translated into java. That's fine, but this kind of java code
        //should be avoided if possible. Hopefully we can design it away.
        if(searchResultsModel instanceof DefaultSearchResultsModel) {
            result = ok(views.html.searchresults.render((DefaultSearchResultsModel) searchResultsModel));
        } else if(searchResultsModel instanceof PersonDetailsModel) {
            result = ok(views.html.persondetails.render((PersonDetailsModel) searchResultsModel));
        } else if(searchResultsModel instanceof TeamDetailsModel) {
            result = ok(views.html.teamdetails.render((TeamDetailsModel) searchResultsModel));
        } else {
            //This should be a 4xx rather than a 5xx response
            result = internalServerError("Query not recognized");
        }
        return result;
    }
}
