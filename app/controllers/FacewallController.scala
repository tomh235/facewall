package controllers

import play.api.mvc._
import repository.FacewallRepo
import facade.FacewallFacade

object FacewallController extends Controller {
    val repository = new FacewallRepo()
    val facade = new FacewallFacade()

    def overview = Action {
        Ok(views.html.overview(facade.mapTeamOverviewModel(repository.listTeams)))
    }
}