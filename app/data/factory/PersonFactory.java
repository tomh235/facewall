package data.factory;

import data.dto.PersonDTO;
import data.mapper.MutableTeam;
import data.mapper.PersonDTOMapper;
import data.mapper.TeamDTOMapper;
import domain.Person;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

import static data.factory.DefaultMutablePerson.newMutablePersonInTeam;

public class PersonFactory {

    private final PersonDTOMapper personDTOMapper;
    private final TeamDTOMapper teamDTOMapper;
    private final LazyMutableTeamFactory lazyMutableTeamFactory;

    public PersonFactory(PersonDTOMapper personDTOMapper, TeamDTOMapper teamDTOMapper, LazyMutableTeamFactory lazyMutableTeamFactory) {
        this.personDTOMapper = personDTOMapper;
        this.teamDTOMapper = teamDTOMapper;
        this.lazyMutableTeamFactory = lazyMutableTeamFactory;
    }

    public List<Person> createPersons(List<PersonDTO> personDTOs) {
        List<Person> personList = new ArrayList<>();

        for (PersonDTO dto : personDTOs) {
            personList.add(createPerson(dto));
        }
        return personList;
    }

    private Person createPerson(PersonDTO dto) {
        MutableTeam mutableTeam = lazyMutableTeamFactory.createLazyMutableTeam();
        Team lazyTeam = teamDTOMapper.map(mutableTeam, dto.teamNode);

        DefaultMutablePerson defaultMutablePerson = newMutablePersonInTeam(lazyTeam);

        return personDTOMapper.map(defaultMutablePerson, dto.personNode);
    }
}
