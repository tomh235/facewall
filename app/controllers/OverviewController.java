package controllers;

import facade.OverviewFacade;
import model.OverviewEntryModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static application.Facewall.facewall;
import static javax.ws.rs.core.Response.ok;
import static views.TemplateHelper.renderTemplate;

@Path("/")
public class OverviewController {
    private final OverviewFacade overviewFacade = facewall().overviewFacade;


    @GET
    public Response overview() {
        Map entries = new HashMap<String, List<OverviewEntryModel>>() {{
            put("entries", overviewFacade.createOverviewModel());
        }};
        return ok(renderTemplate("overview.ftl", entries)).build();
    }
}
