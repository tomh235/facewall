package data.factory.person;

import data.TraversingRepository;
import data.mapper.MutableTeam;
import domain.Person;

import java.util.List;

public class LazyMutableTeamFactory {
    private final TraversingRepository traversingRepository;

    public LazyMutableTeamFactory(TraversingRepository traversingRepository) {
        this.traversingRepository = traversingRepository;
    }

    public MutableTeam createLazyMutableTeam() {
        return new MutableTeam() {
            @Override public List<Person> members() {
                return traversingRepository.findPersonsForTeam(id);
            }
        };
    }
}
