package data.factory;

import data.datatype.PersonId;
import data.dto.PersonDTO;
import domain.Person;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static data.dto.PersonDTOBuilder.newPersonDTO;
import static domain.PersonMatcher.aPerson;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonFactoryTest {
    @Mock TraversingRepository mockRepository;
    private PersonFactory personFactory;

    @Before
    public void setUp() throws Exception {
        personFactory = new PersonFactory(mockRepository);
    }

    @Test
    public void map_name() {
        PersonDTO dto = newPersonDTO()
            .named("John")
            .build();

        Person result = personFactory.createPerson(dto);
        assertThat(result, is(aPerson()
            .named("John")
        ));
    }

    @Test
    public void map_picture() {
        PersonDTO dto = newPersonDTO()
            .whosePictureIs("face.img")
            .build();

        Person result = personFactory.createPerson(dto);
        assertThat(result, is(aPerson()
            .withPicture("face.img")
        ));
    }

    @Test
    public void persons_team_delegates_to_repository() {
        Team expectedTeam = mock(Team.class);
        when(mockRepository.findTeamForPerson(any(PersonId.class))).thenReturn(expectedTeam);

        Person result = personFactory.createPerson(newPersonDTO().build());
        assertThat(result, is(aPerson()
            .inTeam(sameInstance(expectedTeam))
        ));
    }

    @Test
    public void persons_team_verify_interactions() {
        PersonDTO dto = newPersonDTO()
            .withId("expected id")
            .build();

        personFactory.createPerson(dto).team();

        verify(mockRepository).findTeamForPerson(new PersonId("expected id"));
    }
}
