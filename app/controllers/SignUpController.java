package controllers;

import data.TeamRepository;
import domain.Team;
import facade.SignUpFacade;
import facade.validators.UserModelValidator;
import facade.validators.ValidatedUserModel;
import model.UserModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static application.Facewall.facewall;
import static util.freemarker.TemplateHelper.view;
import static util.freemarker.TemplateHelper.withArgs;

public class SignUpController extends Controller {
    private static final Form<UserModel> signUpForm = Form.form(UserModel.class);
    private static final SignUpFacade signUpFacade = facewall().signUpFacade;
    private static final UserModelValidator userModelValidator = facewall().userModelValidator;

    // TODO - Charlie : Implement a list of teams in the database on the signUpForm
        public static Result blankSignUpForm() {
            List<Team> teamList= signUpFacade.getAvailableTeams();
            return ok(view("signupform.ftl",
                    withArgs("teamList", teamList),
                    withArgs("userForm", signUpForm)
                    ));
        }

        //How about using decorator pattern to chain together Validator<Team> and Validator<Person>, and then
        //if (Validator<Team>.isValid && Validator<Person>.isValid) then do
        //facade.addPersonToTeam(Validator<Team>.validatedTeam, Validator<Person>.validatedPerson)
        //otherwise signupform.render(Validator<Team>, Validator<Person>)

        public static Result submitSignUpForm() {
            Form<UserModel> filledSignUpForm = signUpForm.bindFromRequest();
            UserModel newUserModel = filledSignUpForm.get();

            ValidatedUserModel validatedUserModel = userModelValidator.validate(newUserModel);
            signUpFacade.registerPerson(validatedUserModel.getPersonInformation(), validatedUserModel.getTeam());

            return ok(view("signupsummary.ftl", withArgs("userModel", newUserModel)));
        }
}