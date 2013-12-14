package data.factory.person;

import data.dao.FacewallDAO;
import data.datatype.TeamId;
import data.dto.TeamDTO;
import data.factory.LazyMutableTeamFactory;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.PersonDTOMapper;
import domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.List;

import static data.datatype.TeamId.newTeamId;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.IterableMatchers.containsExhaustivelyInAnyOrder;

@RunWith(MockitoJUnitRunner.class)
public class LazyMutableTeamFactoryTest {

    @Mock FacewallDAO mockFacewallDAO;
    @Mock PersonDTOMapper mockPersonDTOMapper;

    @InjectMocks
    private LazyMutableTeamFactory lazyMutableTeamFactory;

    @Test
    public void lazy_mutable_team_members_delegates_to_person_mapper() {
        Person expectedPerson1 = mock(Person.class);
        Person expectedPerson2 = mock(Person.class);

        when(mockPersonDTOMapper.map(any(MutablePerson.class), any(Node.class)))
            .thenReturn(expectedPerson1)
            .thenReturn(expectedPerson2);

        when(mockFacewallDAO.fetchTeam(any(TeamId.class))).thenReturn(
            new TeamDTO(mock(Node.class), asList(mock(Node.class), mock(Node.class)))
        );

        List<Person> result = lazyMutableTeamFactory.createLazyMutableTeam().members();

        assertThat(result, containsExhaustivelyInAnyOrder(
            sameInstance(expectedPerson1),
            sameInstance(expectedPerson2))
        );
    }

    @Test
    public void lazy_mutable_team_members_verify_dao_interaction() {
        when(mockFacewallDAO.fetchTeam(any(TeamId.class))).thenReturn(
            new TeamDTO(mock(Node.class), asList(mock(Node.class)))
        );

        MutableTeam mutableTeam = lazyMutableTeamFactory.createLazyMutableTeam();
        TeamId expectedTeamId = newTeamId("expected id");
        mutableTeam.setId(expectedTeamId);

        mutableTeam.members();

        verify(mockFacewallDAO).fetchTeam(expectedTeamId);
    }

    @Test
    public void lazy_mutable_team_members_verify_person_mapper() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);

        when(mockFacewallDAO.fetchTeam(any(TeamId.class))).thenReturn(new TeamDTO(
                mock(Node.class),
                asList(expectedPersonNode1, expectedPersonNode2)
        ));

        lazyMutableTeamFactory.createLazyMutableTeam().members();

        verify(mockPersonDTOMapper).map(any(MutablePerson.class), eq(expectedPersonNode1));
        verify(mockPersonDTOMapper).map(any(MutablePerson.class), eq(expectedPersonNode2));
    }
}
