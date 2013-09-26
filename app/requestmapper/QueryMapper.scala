package controllers.requestmapper

import domain.Query
import play.api.mvc.{AnyContentAsFormUrlEncoded, AnyContent, Request}

protected [controllers] class QueryMapper {
    def map(request: Request[AnyContent]): Query = {
        val keywords = request.queryString("keywords")(0)
        keywords match {
            case "" => new Query {
                val toRegEx = ""
            }
            case _ => new Query {
                val toRegEx = s"(?i).*$keywords.*"
            }
        }
    }
}
