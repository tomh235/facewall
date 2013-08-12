package domain

import repository.Repository

case class Team(id: String, name: String, repository: Repository) {
    def getMembers: List[Person] = repository.findPersonsForTeam(this)
}
