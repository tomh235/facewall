package facade;

import data.FacewallRepository;
import data.dto.PersonDTO;
import model.UserModel;

public class SignUpFacade {
    private final FacewallRepository repository;

    public SignUpFacade (FacewallRepository repository) {
        this.repository= repository;
    }

    public void delegateNewUserToRepository(UserModel userModel) {

    }
}
