package facade.modelmapper

import model.PersonDetailsModel
import domain.Person

class PersonDetailsModelMapper {
    def map(person: Person): PersonDetailsModel = PersonDetailsModel(person.name)
}
