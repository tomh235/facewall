package data.factory.person;

import data.builder.PersonBuilder;
import data.builder.TeamBuilder;
import data.dto.PersonDTO;
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

import static data.factory.person.DefaultPersonBuilderMatcher.aDefaultPersonBuilder;
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
    @Mock LazyTeamBuilderFactory mockLazyTeamBuilderFactory;

    private PersonFactory personFactory;

    @Before
    public void setUp() throws Exception {
        personFactory = new PersonFactory(mockPersonMapper, mockTeamMapper, mockLazyTeamBuilderFactory);
    }

    @Test
    public void create_person_delegates_to_personMapper() {
        Person expectedPerson1 = mock(Person.class);
        Person expectedPerson2 = mock(Person.class);

        when(mockPersonMapper.map(any(PersonBuilder.class), any(Node.class)))
            .thenReturn(expectedPerson1)
            .thenReturn(expectedPerson2);
        List<PersonDTO> dtos = Arrays.asList(
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

        TeamBuilder expectedTeamBuilder = mock(TeamBuilder.class);
        when(mockLazyTeamBuilderFactory.newLazyTeam())
            .thenReturn(expectedTeamBuilder)
            .thenReturn(expectedTeamBuilder);

        List<PersonDTO> dtos = Arrays.asList(
            new PersonDTO(expectedPersonNode1, expectedTeamNode1),
            new PersonDTO(expectedPersonNode2, expectedTeamNode2)
        );
        personFactory.createPersons(dtos);

        verify(mockLazyTeamBuilderFactory, times(2)).newLazyTeam();

        verify(mockTeamMapper).map(expectedTeamBuilder, expectedTeamNode1);
        verify(mockPersonMapper).map(any(DefaultPersonBuilder.class), eq(expectedPersonNode1));

        verify(mockTeamMapper).map(expectedTeamBuilder, expectedTeamNode2);
        verify(mockPersonMapper).map(any(DefaultPersonBuilder.class), eq(expectedPersonNode2));
    }

    @Test
    public void create_person_sets_team_on_defaultPersonBuilder() {
        Team expectedTeam = mock(Team.class);
        when(mockTeamMapper.map(any(TeamBuilder.class), any(Node.class))).thenReturn(expectedTeam);

        List<PersonDTO> dtos = Arrays.asList(mock(PersonDTO.class));
        personFactory.createPersons(dtos);

        verify(mockPersonMapper).map(
            argThat(is(aDefaultPersonBuilder()
                .withTheFollowingTeamSet(sameInstance(expectedTeam))
            )), any(Node.class));
    }
}
