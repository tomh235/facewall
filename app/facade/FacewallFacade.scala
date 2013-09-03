package facade

import model.OverviewEntry
import domain.Person
import repository.Repository

class FacewallFacade(val repository: Repository) {
    def createOverviewModel: List[OverviewEntry] = {
        val alphabeticallyByTeamNameThenName: (Person, Person) => Boolean = { (person1, person2) =>
            val person1TeamName = person1.getTeam.fold("zzzzzzzz")(_.name)
            val person2TeamName = person2.getTeam.fold("zzzzzzzz")(_.name)
            if (person1TeamName != person2TeamName) person1TeamName < person2TeamName
            else person1.name < person2.name
        }

        val sortedPersons = repository.listPersons.sortWith(alphabeticallyByTeamNameThenName)
        sortedPersons.map { person =>
            OverviewEntry(person.getTeam.fold("")(_.name), person.name, person.picture, person.getTeam.fold("")(_.colour))
        }
    }
}
