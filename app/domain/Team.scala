package domain

trait Team {
    def id: String
    def name: String
    def colour: String
    def members: List[Person]
}