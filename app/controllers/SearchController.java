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
import util.freemarker.TemplateHelper;

import static application.Facewall.facewall;
import static util.freemarker.TemplateHelper.view;
import static util.freemarker.TemplateHelper.withArgs;

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
            result = ok(view("searchresults.ftl", withArgs("results", searchResultsModel)));
        } else if(searchResultsModel instanceof PersonDetailsModel) {
            result = ok(view("persondetails.ftl", withArgs("person", searchResultsModel)));
        } else if(searchResultsModel instanceof TeamDetailsModel) {
            result = ok(view("teamdetails.ftl", withArgs("team", searchResultsModel)));
        } else {
            //This should be a 4xx rather than a 5xx response
            result = ok();
        }
        return result;
    }
}
