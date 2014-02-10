package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.mvc.Viewable;
import uk.co.o2.facewall.facade.SearchFacade;
import uk.co.o2.facewall.model.DefaultSearchResultsModel;
import uk.co.o2.facewall.model.PersonDetailsModel;
import uk.co.o2.facewall.model.SearchResultsModel;
import uk.co.o2.facewall.model.TeamDetailsModel;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.serverError;
import static uk.co.o2.facewall.application.Facewall.facewall;
import static uk.co.o2.facewall.domain.Query.newCaseInsensitiveQuery;

@Path("/search")
public class SearchController {

    private final SearchFacade searchFacade = facewall().searchFacade;

    @GET
    public Viewable search() {
        return new Viewable("/search.ftl");
    }

    @GET
    @Path("/results")
    public Response searchResults(@DefaultValue("") @QueryParam("keywords") String keywords) {
        final SearchResultsModel searchResults = searchFacade.createSearchResultsModel(newCaseInsensitiveQuery(keywords));

        //This looks like scala code that has been translated into java. That's fine, but this kind of java code
        //should be avoided if possible. Hopefully we can design it away.
        Response.ResponseBuilder response = serverError();

        if(searchResults instanceof DefaultSearchResultsModel) {
            response = ok(new Viewable("/searchresults.ftl", searchResults));
        } else if(searchResults instanceof PersonDetailsModel) {
            response = ok(new Viewable("/persondetails.ftl", searchResults));
        } else if(searchResults instanceof TeamDetailsModel) {
            response = ok(new Viewable("/teamdetails.ftl", searchResults));
        }
        return response.build();
    }
}
