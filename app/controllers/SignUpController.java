package controllers;

import data.dto.PersonInformation;
import facade.SignUpFacade;
import facade.validators.UserModelValidator;
import facade.validators.ValidatedUserModel;
import model.UserModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import static application.Facewall.facewall;

public class SignUpController extends Controller {
    private static final Form<UserModel> signUpForm = Form.form(UserModel.class);
    private static final SignUpFacade signUpFacade = facewall().signUpFacade;
    private static final UserModelValidator userModelValidator = facewall().userModelValidator;

       // TODO - Charlie : Implement a list of teams in the database on the signUpForm
        public static Result blankSignUpForm() {
            return ok(views.html.signupform.render(signUpForm, true));
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

            return ok();
        }
}