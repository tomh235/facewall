package facade;

import data.Repository;
import model.UserModel;

public class SignUpFacade {
    private final Repository repository;

    public SignUpFacade (Repository repository) {
        this.repository = repository;
    }

    public void delegateNewUserToRepository(UserModel userModel) {

    }
}
