package data;

import data.dao.AdminDAO;
import domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdminRepositoryTest {

    @Mock AdminDAO mockAdminDAO;

    @InjectMocks
    AdminRepository repository;

    @Test
    public void add_person_interacts_with_facewall_dao() {
        Person mockPerson = mock(Person.class);

        repository.addPerson(mockPerson);
        verify(mockAdminDAO).addPerson(mockPerson);
    }
}
