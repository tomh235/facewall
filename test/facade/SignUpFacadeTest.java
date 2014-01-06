package facade;

import data.PersonRepository;
import data.dao.AdminDAO;
import data.datatype.PersonId;
import data.dto.PersonInformation;
import domain.Person;
import domain.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static data.datatype.PersonId.newPersonId;
import static data.dto.PersonInformation.newPersonInformation;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpFacadeTest {

    private static final Team someTeam = mock(Team.class);

    @Mock private PersonRepository mockPersonRepository;
    @Mock private AdminDAO adminDAO;

    @InjectMocks
    private SignUpFacade signUpFacade;

    @Test
    public void register_person_saves_person_to_the_database() throws Exception {
        PersonInformation expectedPersonInformation = newPersonInformation().build();
        signUpFacade.registerPerson(expectedPersonInformation, someTeam);

        verify(adminDAO).savePersonInformation(expectedPersonInformation);
    }

    @Test
    public void register_person_retrieves_saved_person_and_adds_to_team() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withId("person-id")
                .build();

        Person expectedPerson = mock(Person.class);
        when(mockPersonRepository.findPersonById(any(PersonId.class))).thenReturn(expectedPerson);

        Team mockTeam = mock(Team.class);
        signUpFacade.registerPerson(personInformation, mockTeam);

        verify(mockPersonRepository).findPersonById(newPersonId("person-id"));
        verify(mockTeam).addMember(expectedPerson);
    }
}
