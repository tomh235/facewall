package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.mvc.Viewable;
import uk.co.o2.facewall.facade.OverviewFacade;
import uk.co.o2.facewall.model.OverviewModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static uk.co.o2.facewall.application.Facewall.facewall;

@Path("/splash")
public class SplashPageController {

    @GET
    public Viewable splashPage() {

        return new Viewable("/splashpage.ftl");
    }

}