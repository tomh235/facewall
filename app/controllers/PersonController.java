package controllers;

import data.datatype.PersonId;
import facade.PersonDetailsFacade;
import play.mvc.Controller;
import play.mvc.Result;

import static application.Facewall.facewall;
import static util.freemarker.TemplateHelper.view;
import static util.freemarker.TemplateHelper.withArgs;

public class PersonController extends Controller {

    private static final PersonDetailsFacade personDetailsFacade = facewall().personDetailsFacade;

    public static Result getPerson(String id) {
        PersonId personId = PersonId.newPersonId(id);
        return ok(view("singleperson.ftl", withArgs("person", personDetailsFacade.createPersonDetailsModel(personId))));
    }
}
