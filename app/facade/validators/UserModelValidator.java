package facade.validators;

import data.TeamRepository;
import data.dto.PersonInformation;
import domain.Team;
import model.UserModel;

import static data.dto.PersonInformation.newPersonInformation;
import static domain.Query.newExactQuery;

public class UserModelValidator {

    private final TeamRepository teamRepository;

    public UserModelValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public ValidatedUserModel validate(UserModel userModel) {
        PersonInformation personInformation = createPersonInformation(userModel);
        Team team = teamRepository.queryTeams(newExactQuery(userModel.team)).get(0);

        return new ValidatedUserModel(personInformation, team);
    }

    public PersonInformation createPersonInformation(UserModel userModel) {
        return newPersonInformation()
                .withId(userModel.email) // TODO: change to user-chosen permalink
                .named(userModel.name)
                .withPicture(userModel.imgURL)
                .withEmail(userModel.email)
                //.withRole(userModel.role)
                //.withLocation(userModel.location)
                //.withScrum(userModel.scrum)
                .build();
    }
}
