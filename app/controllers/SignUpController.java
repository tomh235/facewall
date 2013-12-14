package controllers;

import data.DataModule;
import data.Repository;
import facade.FacadeCreator;
import facade.SignUpFacade;
import model.UserModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class SignUpController extends Controller {
    private static final Form<UserModel> signUpForm = Form.form(UserModel.class);
    private static final Repository repository = DataModule.createRepository(null);
    private static final SignUpFacade signUpFacade = FacadeCreator.createSignUpFacade(repository);
    private static UserModel newUserModel;

       // TODO - Charlie : Implement a list of teams in the database on the signUpForm
        public static Result blankSignUpForm() {
            return ok(views.html.signupform.render(signUpForm, true));
        }

        public static Result submitSignUpForm() {
            Result result;
            Form<UserModel> filledSignUpForm = signUpForm.bindFromRequest();
            newUserModel = filledSignUpForm.get();
            boolean teamExists = signUpFacade.validateModelsTeamExists(newUserModel);

            if (filledSignUpForm.hasErrors() || !teamExists ) {
                result = badRequest(views.html.signupform.render(filledSignUpForm, teamExists));
            } else {
                signUpFacade.delegateNewUserToRepository(newUserModel);
                result = redirect(routes.SignUpController.summaryPage());
            }
            return result;
        }

        public static Result summaryPage() {
            return ok(views.html.signupsummary.render(newUserModel));
        }
}