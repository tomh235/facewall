package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.mvc.Viewable;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.facade.TeamDetailsFacade;
import uk.co.o2.facewall.facade.TeamFacade;
import uk.co.o2.facewall.model.TeamDetailsWithPersonsModel;
import uk.co.o2.facewall.model.TeamListModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import static uk.co.o2.facewall.application.Facewall.facewall;

@Path("/team")
public class TeamController {

    private final TeamDetailsFacade teamDetailsFacade = facewall().teamDetailsFacade;
    private final TeamFacade teamFacade = facewall().teamFacade;

    @GET
    public Viewable teamList() {
        final TeamListModel teamListModel = teamFacade.createTeamListModel();
        return new Viewable("/team.ftl", teamListModel);
    }

    @GET
    @Path("/{teamName}")
    public Viewable getTeam(@PathParam("teamName") String teamName) {
        final TeamDetailsWithPersonsModel team = teamDetailsFacade.createTeamDetailsModel(Query.newExactQuery(teamName));
        if(!team.equals(null)){
            return new Viewable("/teamdetails.ftl", team);
        }
        else {
            return new Viewable("/noteam.ftl");
        }
    }
}
