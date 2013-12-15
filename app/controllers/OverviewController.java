package controllers;

import facade.OverviewFacade;
import play.mvc.Controller;
import play.mvc.Result;

import static application.Facewall.facewall;

public class OverviewController extends Controller {
    private static final OverviewFacade overviewFacade = facewall().overviewFacade;

    public static Result overview() {
        return ok(views.html.overview.render(overviewFacade.createOverviewModel()));
    }
}
