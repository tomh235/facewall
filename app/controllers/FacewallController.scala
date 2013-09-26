package controllers

import play.api.mvc._
import repository.FacewallRepo
import facade.OverviewFacade
import facade.SearchFacade
import play.api.templates.Html
import controllers.requestmapper.QueryMapper
import facade.modelmapper.SearchResultsModelMapper

object FacewallController extends Controller {
    val repo = new FacewallRepo()
    val overviewFacade = new OverviewFacade(repo)
    val searchFacade = new SearchFacade(repo, new SearchResultsModelMapper())
    val queryMapper = new QueryMapper()

    def overview = Action {
        val view: Html = views.html.overview(overviewFacade.createOverviewModel)
        Ok(view)
    }

    def search = Action {
        val view: Html = views.html.search()
        Ok(view)
    }

    def searchResults = Action { request: Request[AnyContent] =>
        val query = queryMapper.map(request)
        val createSearchResultsModel = searchFacade.createSearchResultsModel(query)
        val view = views.html.searchresults(createSearchResultsModel)
        Ok(view)
    }
}