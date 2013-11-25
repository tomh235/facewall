package controllers;


import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.api.mvc.PlainResult;
import play.data.Form;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.data.Form.form;

public class SignupControllerTest {

    PlainResult result;
    Form<User> mockForm= form(User.class);

    @Test
    public void new_user_tab_should_render_new_user_form_page() {
        result = (PlainResult) SignupController.blankSignUpForm().getWrappedResult();
        assert (result.header().status() == 200);
    }

    @Test
    public void new_user_form_submission_should_redirect_to_summary_page() {
        mockForm.fill()
        result = (PlainResult) SignupController.signUpFormSubmit().getWrappedResult();
        assert (result.header().status() == 200);
    }

    @Test
    public void new_user_form_sends_badrequest_for_empty_name() {



        result = (PlainResult) SignupController.signUpFormSubmit();
        assert (result.header().status() == 400);
    }

    @Test
    public void new_user_form_sends_badrequest_for_imglink() {
        result = (PlainResult) SignupController.signUpFormSubmit();
        assert (result.header().status() == 400);
    }

    @Test
    public void new_user_form_sends_badrequest_for_empty_location() {
        result = (PlainResult) SignupController.signUpFormSubmit();
        assert (result.header().status() == 400);

    }

    @Test
    public void new_user_form_sends_badrequest_for_empty_role() {
        result = (PlainResult) SignupController.signUpFormSubmit();
        assert (result.header().status() == 400);

    }

    @Test
    public void new_user_form_sends_badrequest_for_empty_email() {
        result = (PlainResult) SignupController.signUpFormSubmit();
        assert (result.header().status() == 400);

    }

    @Test
    public void new_user_form_validates_email() {

    }

}
