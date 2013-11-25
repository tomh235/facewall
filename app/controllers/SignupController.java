package controllers;

import model.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


import static play.data.Form.form;

public class SignupController extends Controller {

    // initializes blank signup form
    private static final Form<User> signUpForm = form(User.class);

        // renders blank signup form
        public static Result blankSignUpForm() {
            return ok(views.html.signupform.render());
        }

        // submits sign up form, taking into account validation defined in User model
        public static Result signUpFormSubmit() {

            Form<User> filledSignUpForm = signUpForm.bindFromRequest();

            // returns badRequest 400 response if form has submission errors
            if(filledSignUpForm.hasErrors()) {
                return badRequest(views.html.signupform.render());
            }
            // navigates to summary page if form submitted successfully
            else {
                User newUser = filledSignUpForm.get();
                return ok(views.html.signupsummary.render(newUser));
            }
        }
}