package facade.modelmapper

import model.PersonDetailsModel
import domain.Person

class PersonDetailsModelMapper {
    def map(person: Person): PersonDetailsModel = new PersonDetailsModel(person.name)
}
