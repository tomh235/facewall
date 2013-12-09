package controllers;

import data.FacewallScalaRepo;
import data.ScalaRepository;
import facade.OverviewFacade;
import play.mvc.Controller;
import play.mvc.Result;

public class OverviewController extends Controller {
    private static final ScalaRepository repo = new FacewallScalaRepo();
    private static final OverviewFacade overviewFacade = new OverviewFacade(repo);

    public static Result overview() {
        return ok(views.html.overview.render(overviewFacade.createOverviewModel()));
    }
}
