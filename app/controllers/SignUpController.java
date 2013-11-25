package controllers;

import model.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class SignUpController extends Controller {
    private static final Form<User> signUpForm = Form.form(User.class);

        public static Result blankSignUpForm() {
            return ok(views.html.signupform.render());
        }

        public static Result signUpFormSubmit() {
            Form<User> filledSignUpForm = signUpForm.bindFromRequest();

            if(filledSignUpForm.hasErrors()) {
                return badRequest(views.html.signupform.render());
            }
            else {
                User newUser = filledSignUpForm.get();
                return ok(views.html.signupsummary.render(newUser));
            }
        }
}