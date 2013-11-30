package data.factory;

import data.dao.FacewallDAO;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.TeamMapper;
import domain.Team;
import org.neo4j.graphdb.Node;

public class LazyMutablePersonFactory implements MutablePersonFactory {
    private final FacewallDAO facewallDAO;
    private final LazyMutableTeamFactory lazyMutableTeamFactory;
    private final TeamMapper teamMapper;

    public LazyMutablePersonFactory(FacewallDAO facewallDAO, LazyMutableTeamFactory lazyMutableTeamFactory, TeamMapper teamMapper) {
        this.facewallDAO = facewallDAO;
        this.lazyMutableTeamFactory = lazyMutableTeamFactory;
        this.teamMapper = teamMapper;
    }

    @Override public MutablePerson createMutablePerson() {
        return new MutablePerson() {
            @Override public Team team() {
                MutableTeam mutableTeam = lazyMutableTeamFactory.createLazyMutableTeam();
                Node teamNode = facewallDAO.fetchTeamForPerson(id);

                return teamMapper.map(mutableTeam, teamNode);
            }
        };
    }
}
