package controllers

import play.api.mvc._
import repository.Repository

object Application extends Controller {
    val repository = new Repository()

    def index = Action {
        Ok(views.html.index(repository.listPersons))
    }
}