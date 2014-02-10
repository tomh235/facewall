package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.mvc.Viewable;
import uk.co.o2.facewall.facade.PersonDetailsFacade;
import uk.co.o2.facewall.model.PersonDetailsModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.HashMap;
import java.util.Map;

import static uk.co.o2.facewall.application.Facewall.facewall;
import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;

@Path("/person")
public class PersonController {

    private final PersonDetailsFacade personDetailsFacade = facewall().personDetailsFacade;

    @GET
    @Path("/{id}")
    public Viewable getPerson(@PathParam("id") String id) {
        final PersonDetailsModel person = personDetailsFacade.createPersonDetailsModel(newPersonId(id));

        return new Viewable("/singleperson.ftl", person);
    }
}
