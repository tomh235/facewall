package domain

trait Query {
    def toRegEx: String
}
