package controllers

import play.api.mvc._
import repository.FacewallRepo

object Application extends Controller {
    val repository = new FacewallRepo()

    def index = Action {
        Ok(views.html.index(repository.listPersons))
    }
}