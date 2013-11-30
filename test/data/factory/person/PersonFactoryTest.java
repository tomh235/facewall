package data.factory.person;

import data.dto.PersonDTO;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.PersonMapper;
import data.mapper.TeamMapper;
import domain.Person;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.Arrays;
import java.util.List;

import static data.mapper.MutablePersonMatcher.aMutablePerson;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.CollectionMatcher.contains;

@RunWith(MockitoJUnitRunner.class)
public class PersonFactoryTest {

    @Mock PersonMapper mockPersonMapper;
    @Mock TeamMapper mockTeamMapper;
    @Mock LazyMutableTeamFactory mockLazyMutableTeamFactory;

    private PersonFactory personFactory;

    @Before
    public void setUp() throws Exception {
        personFactory = new PersonFactory(mockPersonMapper, mockTeamMapper, mockLazyMutableTeamFactory);
    }

    @Test
    public void create_person_delegates_to_personMapper() {
        Person expectedPerson1 = mock(Person.class);
        Person expectedPerson2 = mock(Person.class);

        when(mockPersonMapper.map(any(MutablePerson.class), any(Node.class)))
            .thenReturn(expectedPerson1)
            .thenReturn(expectedPerson2);
        List<PersonDTO> dtos = asList(
            mock(PersonDTO.class), mock(PersonDTO.class)
        );
        List<Person> result = personFactory.createPersons(dtos);

        assertThat(result, contains(
            sameInstance(expectedPerson1),
            sameInstance(expectedPerson2)
        ));
    }

    @Test
    public void create_person_verifyInteractions() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedTeamNode1 = mock(Node.class);

        Node expectedPersonNode2 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        MutableTeam expectedMutableTeam = mock(MutableTeam.class);
        when(mockLazyMutableTeamFactory.createLazyMutableTeam())
            .thenReturn(expectedMutableTeam)
            .thenReturn(expectedMutableTeam);

        List<PersonDTO> dtos = asList(
            new PersonDTO(expectedPersonNode1, expectedTeamNode1),
            new PersonDTO(expectedPersonNode2, expectedTeamNode2)
        );
        personFactory.createPersons(dtos);

        verify(mockLazyMutableTeamFactory, times(2)).createLazyMutableTeam();

        verify(mockTeamMapper).map(expectedMutableTeam, expectedTeamNode1);
        verify(mockPersonMapper).map(any(DefaultMutablePerson.class), eq(expectedPersonNode1));

        verify(mockTeamMapper).map(expectedMutableTeam, expectedTeamNode2);
        verify(mockPersonMapper).map(any(DefaultMutablePerson.class), eq(expectedPersonNode2));
    }

    @Test
    public void create_person_sets_team_on_defaultPersonBuilder() {
        Team expectedTeam = mock(Team.class);
        when(mockTeamMapper.map(any(MutableTeam.class), any(Node.class))).thenReturn(expectedTeam);

        List<PersonDTO> dtos = asList(mock(PersonDTO.class));
        personFactory.createPersons(dtos);

        verify(mockPersonMapper).map(
            argThat(is(aMutablePerson()
                .whoseTeamHasBeenSetTo(
                    sameInstance(expectedTeam)))),
            any(Node.class));
    }
}
