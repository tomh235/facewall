package facade.requestmapper

import domain.Query

protected [facade] object QueryMapper {
    def map(keywords: String): Query = new Query {
        def toRegEx: String = keywords
    }
}
