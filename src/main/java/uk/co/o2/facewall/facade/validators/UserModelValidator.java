package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.co.o2.facewall.data.dto.PersonInformation.newPersonInformation;
import static uk.co.o2.facewall.domain.NoTeam.noTeam;
import static uk.co.o2.facewall.domain.Query.newExactQuery;
import static java.util.UUID.randomUUID;

public class UserModelValidator {

    private final TeamRepository teamRepository;

    public UserModelValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public ValidatedUserModel validate(UserModel userModel) {
        PersonInformation personInformation = createPersonInformation(userModel);
        Map<String, String> errorMap = new HashMap<>();
        Team team;
        List<Team> teamsList = teamRepository.queryTeams(newExactQuery(userModel.team));
        if(teamsList.isEmpty()) {
            errorMap.put("team", "No such team exists.");
            team = noTeam();
        } else {
            team = teamsList.get(0);
        }

        return new ValidatedUserModel(personInformation, team, errorMap);
    }

    public PersonInformation createPersonInformation(UserModel userModel) {
        return newPersonInformation()
                .withId(randomUUID().toString()) // TODO: change to user-chosen permalink (or email)
                .named(userModel.name)
                .withPicture(userModel.imgUrl)
                .withEmail(userModel.email)
                .withRole(userModel.role)
                //.withLocation(userModel.location)
                //.withScrum(userModel.scrum)
                .build();
    }
}
