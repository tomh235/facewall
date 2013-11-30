package facade;

import data.Repository;
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

    @Mock UserModelToPersonMapper userModelToPersonMapper;
    @Mock Repository repo;
    private SignUpFacade signUpFacade = new SignUpFacade(repo);

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
