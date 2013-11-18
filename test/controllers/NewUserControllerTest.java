package controllers;


import org.junit.Test;
import play.api.mvc.PlainResult;
import play.mvc.Result;

public class NewUserControllerTest {

    private static NewUserController newUserController = new NewUserController();

    @Test
    public void newUserShouldRenderNewUserFormPage() {
        PlainResult result = (PlainResult) newUserController.newUser();
        assert (result.header().status() == 200);
    }

}
