package facade;

import data.FacewallRepository;
import data.dao.FacewallDAO;
import data.factory.person.PersonFactory;
import data.factory.team.TeamFactory;
import domain.Person;
import model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import requestmapper.UserModelToPersonMapper;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SignUpFacadeTest {
    @Mock PersonFactory mockPersonFactory;
    @Mock TeamFactory mockTeamFactory;
    @Mock FacewallDAO mockFacewallDAO;
    @Mock UserModelToPersonMapper userModelToPersonMapper;

    FacewallRepository facewallRepository = new FacewallRepository(mockPersonFactory, mockTeamFactory, mockFacewallDAO);
    SignUpFacade signUpFacade = new SignUpFacade(facewallRepository);

    // TODO: finish test implementation
    @Test
    public void delegateNewUserToRepository_delegates_correctly() {
        UserModel mockUserModel = mock(UserModel.class);
        Person mockPerson = mock(Person.class);

        when(userModelToPersonMapper.map(mockUserModel)).thenReturn(mockPerson);

        signUpFacade.delegateNewUserToRepository(mockUserModel);

        assertTrue(false);
    }
}
