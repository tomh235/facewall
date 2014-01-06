package data;

import data.dao.FacewallDAO;
import data.datatype.TeamId;
import data.dto.PersonInformation;
import data.dto.TeamDTO;
import domain.Person;
import domain.Query;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
    private final FacewallDAO dao;
    private final TeamsFactory teamsFactory;

    public TeamRepository(FacewallDAO dao, TeamsFactory teamsFactory) {
        this.dao = dao;
        this.teamsFactory = teamsFactory;
    }

    public List<Team> listTeams() {
        return teamsFactory.createTeams(dao.fetchTeams());
    }

    public List<Team> queryTeams(Query query) {
        return teamsFactory.createTeams(dao.queryTeams(query));
    }

    public Team findTeamById(TeamId id) {
        return teamsFactory.createTeam(dao.fetchTeam(id));
    }
}
