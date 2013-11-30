package data.factory.person;

import data.builder.TeamBuilder;
import data.dto.PersonDTO;
import data.mapper.PersonMapper;
import data.mapper.TeamMapper;
import domain.Person;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

import static data.factory.person.DefaultPersonBuilder.newDefaultPerson;

public class PersonFactory {

    private final PersonMapper personMapper;
    private final TeamMapper teamMapper;
    private final LazyTeamBuilderFactory lazyTeamBuilderFactory;

    public PersonFactory(PersonMapper personMapper, TeamMapper teamMapper, LazyTeamBuilderFactory lazyTeamBuilderFactory) {
        this.personMapper = personMapper;
        this.teamMapper = teamMapper;
        this.lazyTeamBuilderFactory = lazyTeamBuilderFactory;
    }

    public List<Person> createPersons(List<PersonDTO> personDTOs) {
        List<Person> persons = new ArrayList<>();

        for (PersonDTO dto : personDTOs) {
            persons.add(createPerson(dto));
        }
        return persons;
    }

    private Person createPerson(PersonDTO dto) {
        TeamBuilder lazyTeamBuilder = lazyTeamBuilderFactory.newLazyTeam();
        Team lazyTeam = teamMapper.map(lazyTeamBuilder, dto.teamNode);

        DefaultPersonBuilder defaultPersonBuilder = newDefaultPerson()
            .inTeam(lazyTeam);

        return personMapper.map(defaultPersonBuilder, dto.personNode);
    }
}
