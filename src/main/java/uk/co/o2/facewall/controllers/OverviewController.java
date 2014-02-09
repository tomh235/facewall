package uk.co.o2.facewall.controllers;

import uk.co.o2.facewall.facade.OverviewFacade;
import uk.co.o2.facewall.model.OverviewEntryModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.co.o2.facewall.application.Facewall.facewall;
import static javax.ws.rs.core.Response.ok;
import static uk.co.o2.facewall.views.TemplateRenderer.renderTemplate;

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
