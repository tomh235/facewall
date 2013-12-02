package controllers;

import model.UserModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class SignUpController extends Controller {
    private static final Form<UserModel> signUpForm = Form.form(UserModel.class);
    private static Form<UserModel> filledSignUpForm;

        public static Result blankSignUpForm() {
            return ok(views.html.signupform.render(signUpForm));
        }

        public static Result signUpFormSubmit() {
            filledSignUpForm = signUpForm.bindFromRequest();

            if(filledSignUpForm.hasErrors()) {
                return badRequest(views.html.signupform.render(filledSignUpForm));
            }
            else {
                return redirect(routes.SignUpController.summaryPage());
            }
        }

        public static Result summaryPage() {
            UserModel newUserModel = filledSignUpForm.get();
            return ok(views.html.signupsummary.render(newUserModel));
        }
}