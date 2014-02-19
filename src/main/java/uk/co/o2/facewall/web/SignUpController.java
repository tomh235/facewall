package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.mvc.Viewable;
import uk.co.o2.facewall.facade.SignUpFacade;
import uk.co.o2.facewall.facade.validators.UserModelValidator;
import uk.co.o2.facewall.facade.validators.ValidatedUserModel;
import uk.co.o2.facewall.model.UserModel;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
            Map model = new HashMap<String, Object>() {{
                put("userForm", new UserModel());
                put("teamNamesList", teamNamesList);
            }};

            return new Viewable("/signupform.ftl", model);
        }

        //How about using decorator pattern to chain together Validator<Team> and Validator<Person>, and then
        //if (Validator<Team>.isValid && Validator<Person>.isValid) then do
        //facade.addPersonToTeam(Validator<Team>.validatedTeam, Validator<Person>.validatedPerson)
        //otherwise signupform.render(Validator<Team>, Validator<Person>)
        @Path("/summary")
        @POST
        public Viewable submitSignUpForm() {
            final UserModel newUserModel = new UserModel();

            ValidatedUserModel validatedUserModel = userModelValidator.validate(newUserModel);
            signUpFacade.registerPerson(validatedUserModel.getPersonInformation(), validatedUserModel.getTeam());

            return new Viewable("/signupsummary.ftl", newUserModel);
        }
}