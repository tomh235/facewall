package uk.co.o2.facewall.data.dto;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

import static uk.co.o2.facewall.data.datatype.TeamId.newTeamId;

public class TeamInformationMatcher extends CompositeMatcher<TeamInformation> {

    private TeamInformationMatcher() {}

    public static TeamInformationMatcher aTeamInformation() {
        return new TeamInformationMatcher();
    }

    public TeamInformationMatcher withId(final String id) {
        add(new TypeSafeMatcher<TeamInformation>() {
            @Override
            public boolean matchesSafely(TeamInformation teamInformation) {
                return newTeamId(id).equals(teamInformation.getId());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with the id ").appendValue(id);
            }
        });
        return this;
    }
}
