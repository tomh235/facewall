package controllers

import play.api.mvc._
import repository.FacewallRepo
import facade.FacewallFacade
import play.api.templates.Html
import controllers.requestmapper.QueryMapper

object FacewallController extends Controller {
    val facade = new FacewallFacade(new FacewallRepo())
    val queryMapper = new QueryMapper()

    def overview = Action {
        val view: Html = views.html.overview(facade.createOverviewModel)
        Ok(view)
    }

    def search = Action {
        val view: Html = views.html.search()
        Ok(view)
    }

    def searchResults = Action { request: Request[AnyContent] =>
        val query = queryMapper.map(request)
      val createSearchResultsModel = facade.createSearchResultsModel(query)
        val view = views.html.searchresults(createSearchResultsModel)
      Ok(view)
    }
}