package data.factory.person;

import data.TraversingRepository;
import data.builder.TeamBuilder;
import data.datatype.TeamId;
import domain.Person;
import domain.Team;

import java.util.List;

import static data.datatype.TeamId.newTeamId;
import static data.datatype.TeamId.noTeamId;

class LazyTeamBuilderFactory {

    private final TraversingRepository traversingRepository;

    LazyTeamBuilderFactory(TraversingRepository traversingRepository) {
        this.traversingRepository = traversingRepository;
    }

    TeamBuilder newLazyTeam() {
        return new TeamBuilder() {

            private TeamId builderTeamId = noTeamId();
            private String builderName = "";
            private String builderColour = "";

            @Override public TeamBuilder withId(String id) {
                this.builderTeamId = newTeamId(id);
                return this;
            }

            @Override public TeamBuilder named(String name) {
                this.builderName = name;
                return this;
            }

            @Override public TeamBuilder coloured(String colour) {
                this.builderColour = colour;
                return this;
            }

            @Override public Team build() {
                return new Team() {
                    final TeamId teamId = builderTeamId;
                    final String name = builderName;
                    final String colour = builderColour;

                    @Override public String id() {
                        throw new UnsupportedOperationException("id method not supported");
                    }

                    @Override public String name() {
                        return name;
                    }

                    @Override public String colour() {
                        return colour;
                    }

                    @Override public List<Person> members() {
                        return traversingRepository.findPersonsForTeam(teamId);
                    }
                };
            }
        };
    }
}
