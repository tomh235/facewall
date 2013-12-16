package controllers;

import data.datatype.PersonId;
import facade.PersonDetailsFacade;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.singleperson;

import static application.Facewall.facewall;

public class PersonController extends Controller {

    private static final PersonDetailsFacade personDetailsFacade = facewall().personDetailsFacade;

    public static Result getPerson(String id) {
        PersonId personId = PersonId.newPersonId(id);
        return ok(singleperson.render(personDetailsFacade.createPersonDetailsModel(personId)));
    }
}
