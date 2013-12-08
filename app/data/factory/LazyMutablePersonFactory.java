package data.factory;

import data.dao.TraversingDAO;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.TeamDTOMapper;
import domain.Team;
import org.neo4j.graphdb.Node;

public class LazyMutablePersonFactory implements MutablePersonFactory {
    private final TraversingDAO dao;
    private final LazyMutableTeamFactory lazyMutableTeamFactory;
    private final TeamDTOMapper teamDTOMapper;

    public LazyMutablePersonFactory(TraversingDAO traversingDAO, LazyMutableTeamFactory lazyMutableTeamFactory, TeamDTOMapper teamDTOMapper) {
        this.dao = traversingDAO;
        this.lazyMutableTeamFactory = lazyMutableTeamFactory;
        this.teamDTOMapper = teamDTOMapper;
    }

    @Override public MutablePerson createMutablePerson() {
        return new MutablePerson() {
            @Override public Team team() {
                MutableTeam mutableTeam = lazyMutableTeamFactory.createLazyMutableTeam();
                Node teamNode = dao.fetchTeamForPerson(id);

                return teamDTOMapper.map(mutableTeam, teamNode);
            }
        };
    }
}
