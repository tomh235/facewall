package domain

import repository.Repository

case class Person(id: String, name: String, picture: String, repository: Repository) {
    def getTeam = repository.findTeamForPerson(this)
}
