package controllers

import play.api._
import play.api.mvc._
import play.api.templates.Html
import domain.Person

object Application extends Controller {

    def index = Action {
        Ok(views.html.index(List(Person("http://placekitten.com/200/300", "kitten"))))
    }
}