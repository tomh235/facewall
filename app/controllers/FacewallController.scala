package controllers

import play.api.mvc._
import repository.FacewallRepo
import facade.FacewallFacade
import play.api.templates.Html

object FacewallController extends Controller {
    val facade = new FacewallFacade(new FacewallRepo())

    def overview = Action {
        val view: Html = views.html.overview(facade.createOverviewModel)
        Ok(view)
    }
}