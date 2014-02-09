package uk.co.o2.facewall.data.dto;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

public class TeamDTOMatcher extends CompositeMatcher<TeamDTO> {

    private TeamDTOMatcher() {}

    public static TeamDTOMatcher aTeamDTO() {
        return new TeamDTOMatcher();
    }

    public TeamDTOMatcher withTeamInformation(final Matcher<TeamInformation> teamInformationMatcher) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override public boolean matchesSafely(TeamDTO teamDTO) {
                return teamInformationMatcher.matches(teamDTO.teamInformation);
            }

            @Override public void describeTo(Description description) {
                description.appendText("where the team information is: ").appendDescriptionOf(teamInformationMatcher);
            }
        });
        return this;
    }

    public TeamDTOMatcher whereMemberInformation(final Matcher<Iterable<PersonInformation>> memberInformationMatcher) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override public boolean matchesSafely(TeamDTO teamDTO) {
                return memberInformationMatcher.matches(teamDTO.memberInformation);
            }

            @Override public void describeTo(Description description) {
                description.appendText("where the members' information: ").appendDescriptionOf(memberInformationMatcher);
            }
        });
        return this;
    }
}
