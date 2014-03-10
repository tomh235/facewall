package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.dao.FacewallDAO;
import uk.co.o2.facewall.data.datatype.TeamId;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.domain.Team;

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

    public Team findTeamByName(Query name) {
        return teamsFactory.createTeam(dao.fetchTeam(name));
    }
}
