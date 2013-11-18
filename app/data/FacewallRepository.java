package data;

import data.dao.FacewallDAO;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.factory.PersonFactory;
import data.factory.TeamFactory;
import domain.Person;
import domain.Team;

class FacewallRepository implements Repository {
    final PersonFactory personFactory;
    final TeamFactory teamFactory;

    final FacewallDAO dao;

    FacewallRepository(PersonFactory personFactory, TeamFactory teamFactory, FacewallDAO dao) {
        this.personFactory = personFactory;
        this.teamFactory = teamFactory;
        this.dao = dao;
    }

    @Override public Person findPerson(PersonId personId) {
        PersonDTO dto = dao.fetchPerson(personId);
        return personFactory.createPerson(dto);
    }

    @Override public Team findTeam(TeamId teamId) {
        TeamDTO dto = dao.fetchTeam(teamId);
        return teamFactory.createTeam(dto);
    }
}
