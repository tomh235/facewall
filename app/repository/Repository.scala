package repository

import domain.{Team, Person}

trait Repository {
    def findTeamForPerson(person: Person): Option[Team]
    def listPersons: List[Person]
    def listTeams: List[Team]
    def findPersonsForTeam(team: Team): List[Person]
}
