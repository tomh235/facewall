package data.factory;

import data.dao.FacewallDAO;
import data.dto.PersonDTO;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.TeamDTOMapper;
import domain.Team;

public class LazyMutablePersonFactory implements MutablePersonFactory {
    private final FacewallDAO facewallDAO;
    private final LazyMutableTeamFactory lazyMutableTeamFactory;
    private final TeamDTOMapper teamDTOMapper;

    public LazyMutablePersonFactory(FacewallDAO facewallDAO, LazyMutableTeamFactory lazyMutableTeamFactory, TeamDTOMapper teamDTOMapper) {
        this.facewallDAO = facewallDAO;
        this.lazyMutableTeamFactory = lazyMutableTeamFactory;
        this.teamDTOMapper = teamDTOMapper;
    }

    @Override public MutablePerson createMutablePerson() {
        return new MutablePerson() {
            @Override public Team team() {
                MutableTeam mutableTeam = lazyMutableTeamFactory.createLazyMutableTeam();
                PersonDTO dto = facewallDAO.fetchPerson(id);

                return teamDTOMapper.map(mutableTeam, dto.teamNode);
            }
        };
    }
}
