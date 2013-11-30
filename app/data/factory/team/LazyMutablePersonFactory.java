package data.factory.team;

import data.TraversingRepository;
import data.mapper.MutablePerson;
import domain.Team;

public class LazyMutablePersonFactory {
    private final TraversingRepository traversingRepository;

    public LazyMutablePersonFactory(TraversingRepository traversingRepository) {
        this.traversingRepository = traversingRepository;
    }

    public MutablePerson createLazyMutablePerson() {
        return new MutablePerson() {
            @Override public Team team() {
                return traversingRepository.findTeamForPerson(id);
            }
        };
    }
}
