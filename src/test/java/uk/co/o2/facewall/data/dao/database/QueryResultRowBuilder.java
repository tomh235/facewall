package uk.co.o2.facewall.data.dao.database;

import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.TeamInformation;

import static uk.co.o2.facewall.data.dto.PersonInformation.newPersonInformation;
import static uk.co.o2.facewall.data.dto.PersonInformation.noPersonInformation;
import static uk.co.o2.facewall.data.dto.TeamInformation.newTeamInformation;
import static uk.co.o2.facewall.data.dto.TeamInformation.noTeamInformation;

public class QueryResultRowBuilder {

    private PersonInformation personInformation = noPersonInformation();
    private TeamInformation teamInformation = noTeamInformation();

    private QueryResultRowBuilder() {}

    public static QueryResultRowBuilder blankRow() {
        return new QueryResultRowBuilder();
    }

    public static QueryResultRowBuilder teamlessPersonRow() {
        return blankRow().withPerson(newPersonInformation()
                .withId("some-id")
                .named("some name"));
    }

    public static QueryResultRowBuilder defaultRow() {
        return blankRow()
                .withPerson(newPersonInformation()
                        .withId("some-id")
                        .named("some name"))
                .withTeam(newTeamInformation()
                        .withId("some-team-id")
                        .named("team name"));
    }

    public QueryResultRow build() {
        return new QueryResultRow() {
            @Override
            public PersonInformation getPerson() {
                return personInformation;
            }

            @Override
            public TeamInformation getTeam() {
                return teamInformation;
            }
        };
    }

    public QueryResultRowBuilder withPerson(PersonInformation.Builder personInformationBuilder) {
        personInformation = personInformationBuilder.build();
        return this;
    }

    public QueryResultRowBuilder withTeam(TeamInformation.Builder teamInformationBuilder) {
        teamInformation = teamInformationBuilder.build();
        return this;
    }
}
