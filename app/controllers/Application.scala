package controllers

import play.api._
import play.api.mvc._
import play.api.templates.Html
import domain.Person
import repository.KittenRepo

object Application extends Controller {

    val repository = new KittenRepo()

    def index = Action {
        Ok(views.html.index(repository.getKittens))
    }
}