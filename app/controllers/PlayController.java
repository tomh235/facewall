package controllers;

import play.mvc.Result;
import play.mvc.Controller;

public class PlayController extends Controller {

    public static Result blankPage() {
        return ok();
    }
}
