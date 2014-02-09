package uk.co.o2.facewall.controllers;

import uk.co.o2.facewall.facade.PersonDetailsFacade;
import uk.co.o2.facewall.model.PersonDetailsModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static uk.co.o2.facewall.application.Facewall.facewall;
import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static javax.ws.rs.core.Response.ok;
import static uk.co.o2.facewall.views.TemplateRenderer.renderTemplate;

@Path("/person")
public class PersonController {

    private final PersonDetailsFacade personDetailsFacade = facewall().personDetailsFacade;

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") String id) {
        final PersonDetailsModel person = personDetailsFacade.createPersonDetailsModel(newPersonId(id));

        Map model = new HashMap<String, PersonDetailsModel>() {{
            put("person", person);
        }};
        return ok(renderTemplate("singleperson.ftl", model)).build();
    }
}
