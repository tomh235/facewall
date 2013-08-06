package controllers

import play.api.mvc._
import repository.PersonRepo

object Application extends Controller {
    val repository = new PersonRepo()

    def index = Action {
        Ok(views.html.index(repository.getEveryone))
    }
}