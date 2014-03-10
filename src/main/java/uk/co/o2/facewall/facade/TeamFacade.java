package uk.co.o2.facewall.facade;

import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.facade.modelmapper.TeamDetailsModelMapper;
import uk.co.o2.facewall.model.TeamDetailsModel;
import uk.co.o2.facewall.model.TeamListModel;

import java.util.ArrayList;
import java.util.List;

public class TeamFacade {

    private TeamRepository teamRepository;
    private TeamDetailsModelMapper teamDetailsModelMapper;

    public TeamFacade(TeamRepository teamRepository, TeamDetailsModelMapper teamDetailsModelMapper) {
        this.teamRepository = teamRepository;
        this.teamDetailsModelMapper =  teamDetailsModelMapper;
    }

    public TeamListModel createTeamListModel() {
        List<TeamDetailsModel> teamDetailsModels = new ArrayList<>();

        List<Team> teamList = teamRepository.listTeams();

        for(Team team : teamList) {
            teamDetailsModels.add(teamDetailsModelMapper.map(team));
        }

        return new TeamListModel(teamDetailsModels);
    }

}
