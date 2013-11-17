package repository

import domain.{Team, Person, Query}

trait Repository {
    def findTeamForPerson(person: Person): Team
    def listPersons: java.util.List[Person]
    def listTeams: List[Team]
    def findPersonsForTeam(team: Team): List[Person]
    def queryPersons(query: Query): java.util.List[Person]
    def queryTeams(query: Query): java.util.List[Team]
}
