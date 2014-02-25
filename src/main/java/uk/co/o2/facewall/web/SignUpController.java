package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.mvc.Viewable;
import uk.co.o2.facewall.facade.SignUpFacade;
import uk.co.o2.facewall.facade.validators.UserModelValidator;
import uk.co.o2.facewall.facade.validators.ValidatedUserModel;
import uk.co.o2.facewall.model.UserModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.co.o2.facewall.application.Facewall.facewall;

@Path("/signup")
public class SignUpController {

    private static final SignUpFacade signUpFacade = facewall().signUpFacade;
    private final UserModelValidator userModelValidator = facewall().userModelValidator;

    @GET
    public static Viewable blankSignUpForm() {
        final List<String> teamNamesList = signUpFacade.getSortedAvailableTeamNames();
        Map<String, Object> model = new HashMap<>();
        model.put("teamNamesList", teamNamesList);

        return new Viewable("/signupform.ftl", model);
    }


    // FIXME - Charlie: When the page returns with errors it still navigates to /signup/summary rather than staying on /signup
    @Path("/summary")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Viewable submitSignUpForm(@FormParam("name") String name,
                                     @FormParam("imgUrl") String imgUrl,
                                     @FormParam("email") String email,
                                     @FormParam("team") String team,
                                     @FormParam("scrum") String scrum,
                                     @FormParam("role") String role,
                                     @FormParam("location") String location) {

        UserModel userModel = new UserModel(email, imgUrl, name, role, location, team, scrum);
        ValidatedUserModel validatedUserModel = userModelValidator.validate(userModel);

        Map<String, Object> model = new HashMap<>();
        model.put("personInformation", validatedUserModel.getPersonInformation());
        model.put("team", validatedUserModel.getTeam());

        if (validatedUserModel.hasErrors()) {
            final List<String> teamNamesList = signUpFacade.getSortedAvailableTeamNames();
            model.put("teamNamesList", teamNamesList);
            model.put("errors", validatedUserModel.getErrors());
            return new Viewable("/signupform.ftl", model);
        } else {
            signUpFacade.registerPerson(validatedUserModel.getPersonInformation(), validatedUserModel.getTeam());
            return new Viewable("/signupsummary.ftl", model);
        }
    }
}
