package data.factory.person;

import data.dao.FacewallDAO;
import data.datatype.TeamId;
import data.factory.LazyMutableTeamFactory;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.PersonMapper;
import domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.Arrays;
import java.util.List;

import static data.datatype.TeamId.newTeamId;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.CollectionMatcher.contains;

@RunWith(MockitoJUnitRunner.class)
public class LazyMutableTeamFactoryTest {

    @Mock FacewallDAO mockDAO;
    @Mock PersonMapper mockPersonMapper;
    private LazyMutableTeamFactory lazyMutableTeamFactory;

    @Before
    public void setUp() throws Exception {
        lazyMutableTeamFactory = new LazyMutableTeamFactory(mockDAO, mockPersonMapper);
    }

    @Test
    public void lazy_mutable_team_members_delegates_to_person_mapper() {
        Person expectedPerson1 = mock(Person.class);
        Person expectedPerson2 = mock(Person.class);

        when(mockPersonMapper.map(any(MutablePerson.class), any(Node.class)))
            .thenReturn(expectedPerson1)
            .thenReturn(expectedPerson2);

        when(mockDAO.fetchTeamMembers(any(TeamId.class))).thenReturn(asList(
                mock(Node.class), mock(Node.class)
        ));

        List<Person> result = lazyMutableTeamFactory.createLazyMutableTeam().members();

        assertThat(result, contains(
            sameInstance(expectedPerson1),
            sameInstance(expectedPerson2))
        );
    }

    @Test
    public void lazy_mutable_team_members_verify_dao_interaction() {
        MutableTeam mutableTeam = lazyMutableTeamFactory.createLazyMutableTeam();
        TeamId expectedTeamId = newTeamId("expected id");
        mutableTeam.setId(expectedTeamId);

        mutableTeam.members();

        verify(mockDAO).fetchTeamMembers(expectedTeamId);
    }

    @Test
    public void lazy_mutable_team_members_verify_person_mapper() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);
        when(mockDAO.fetchTeamMembers(any(TeamId.class))).thenReturn(asList(
                expectedPersonNode1,
                expectedPersonNode2
        ));

        lazyMutableTeamFactory.createLazyMutableTeam().members();

        verify(mockPersonMapper).map(any(MutablePerson.class), eq(expectedPersonNode1));
        verify(mockPersonMapper).map(any(MutablePerson.class), eq(expectedPersonNode2));
    }
}
