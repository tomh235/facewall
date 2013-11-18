package controllers;

import model.User;
import facade.OverviewFacade;
import play.api.templates.Html;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import data.FacewallScalaRepo;
import views.html.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static play.data.Form.form;

public class NewUserController extends Controller {

    private static FacewallScalaRepo repo = new FacewallScalaRepo();
    private static OverviewFacade overviewFacade = new OverviewFacade(repo);
    private static Form<User> userForm = form(User.class);
    private static Map<String,Object> formData = new HashMap();

    public static Result newUser() {
//        throw new RuntimeException("not implemented");
        return ok(newuser.render());
    }

    public static Result newUserSubmit() {

        User user = userForm.bindFromRequest().get();

        //formData.put("name", .getAttribute("name");
        //System.out.println(request.getAttribute("name").toString());
        //User user = userForm.bind(formData).get();
        return ok(overview.render(overviewFacade.createOverviewModel()));
    }
}
