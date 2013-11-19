package controllers;

import data.FacewallScalaRepo;
import facade.OverviewFacade;
import model.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.newuser;
import views.html.overview;

import java.util.HashMap;
import java.util.Map;

import static play.data.Form.form;

public class NewUserController extends Controller {

    private static FacewallScalaRepo repo = new FacewallScalaRepo();
    private static OverviewFacade overviewFacade = new OverviewFacade(repo);
    private static Form<User> userForm = form(User.class);
    private static Map<String,Object> formData = new HashMap();

    public static Result newUser() {
        return ok(newuser.render());
    }

    public static Result newUserSubmit() {
        User user = userForm.bindFromRequest().get();
        return ok(overview.render(overviewFacade.createOverviewModel()));
    }
}
