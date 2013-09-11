package domain

trait Person {
    def id: String
    def name: String
    def picture: String
    def team: Option[Team]
}
