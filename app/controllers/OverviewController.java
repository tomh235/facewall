package controllers;

import facade.OverviewFacade;
import play.mvc.Controller;
import play.mvc.Result;

import static application.Facewall.facewall;
import static util.freemarker.TemplateHelper.view;
import static util.freemarker.TemplateHelper.withArgs;

public class OverviewController extends Controller {
    private static final OverviewFacade overviewFacade = facewall().overviewFacade;

    public static Result overview() {
        return ok(view("overview.ftl", withArgs("entries", overviewFacade.createOverviewModel())));
    }
}
