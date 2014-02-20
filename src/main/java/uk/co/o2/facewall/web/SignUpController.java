package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.mvc.Viewable;
import uk.co.o2.facewall.facade.SignUpFacade;
import uk.co.o2.facewall.facade.validators.UserModelValidator;
import uk.co.o2.facewall.facade.validators.ValidatedUserModel;
import uk.co.o2.facewall.model.UserModel;

import javax.ws.rs.*;
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
            model.put("userForm", new UserModel());
            model.put("teamNamesList", teamNamesList);

            return new Viewable("/signupform.ftl", model);
        }

        //How about using decorator pattern to chain together Validator<Team> and Validator<Person>, and then
        //if (Validator<Team>.isValid && Validator<Person>.isValid) then do
        //facade.addPersonToTeam(Validator<Team>.validatedTeam, Validator<Person>.validatedPerson)
        //otherwise signupform.render(Validator<Team>, Validator<Person>)

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

            UserModel userModel = signUpFacade.mapUserModel(name, imgUrl, email, team, scrum, role, location);
            ValidatedUserModel validatedUserModel = userModelValidator.validate(userModel);
            signUpFacade.registerPerson(validatedUserModel.getPersonInformation(), validatedUserModel.getTeam());

            Map<String, Object> model = new HashMap<>();
            model.put("userModel", validatedUserModel.getPersonInformation());
            model.put("userModelsTeam", validatedUserModel.getTeam());

            return new Viewable("/signupsummary.ftl", validatedUserModel);
        }
}
