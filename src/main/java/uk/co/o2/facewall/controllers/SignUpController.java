package uk.co.o2.facewall.controllers;

import uk.co.o2.facewall.facade.SignUpFacade;
import uk.co.o2.facewall.facade.validators.UserModelValidator;
import uk.co.o2.facewall.facade.validators.ValidatedUserModel;
import uk.co.o2.facewall.model.UserModel;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static uk.co.o2.facewall.application.Facewall.facewall;
import static javax.ws.rs.core.Response.ok;
import static uk.co.o2.facewall.views.TemplateRenderer.renderTemplate;

@Path("/signup")
public class SignUpController {

    private final SignUpFacade signUpFacade = facewall().signUpFacade;
    private final UserModelValidator userModelValidator = facewall().userModelValidator;

        // TODO - Charlie : Implement a list of teams in the database on the signUpForm
        @GET
        public static Response blankSignUpForm() {
            Map model = new HashMap<String, Object>() {{
                put("userForm", new UserModel());
                put("teamExists", true);
            }};

            return ok(renderTemplate("signupform.ftl", model)).build();
        }

        //How about using decorator pattern to chain together Validator<Team> and Validator<Person>, and then
        //if (Validator<Team>.isValid && Validator<Person>.isValid) then do
        //facade.addPersonToTeam(Validator<Team>.validatedTeam, Validator<Person>.validatedPerson)
        //otherwise signupform.render(Validator<Team>, Validator<Person>)
        @Path("/summary")
        @POST
        public Response submitSignUpForm() {
            final UserModel newUserModel = new UserModel();

            ValidatedUserModel validatedUserModel = userModelValidator.validate(newUserModel);
            signUpFacade.registerPerson(validatedUserModel.getPersonInformation(), validatedUserModel.getTeam());

            Map model = new HashMap<String, Object>() {{
                put("userModel", newUserModel);
            }};
            return ok(renderTemplate("signupsummary.ftl", model)).build();
        }
}