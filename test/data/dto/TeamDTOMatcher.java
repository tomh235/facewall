package data.dto;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.neo4j.graphdb.Node;
import util.CompositeMatcher;

public class TeamDTOMatcher extends CompositeMatcher<TeamDTO> {

    private TeamDTOMatcher() {}

    public static TeamDTOMatcher aTeamDTO() {
        return new TeamDTOMatcher();
    }

    public TeamDTOMatcher withTeamNode(final Matcher<Node> nodeMatcher) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override public boolean matchesSafely(TeamDTO teamDTO) {
                return nodeMatcher.matches(teamDTO.teamInformation);
            }

            @Override public void describeTo(Description description) {
                description.appendText("where the team node is: ").appendDescriptionOf(nodeMatcher);
            }
        });
        return this;
    }

    public TeamDTOMatcher whereMemberNodes(final Matcher<Iterable<Node>> memberNodeMatcher) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override public boolean matchesSafely(TeamDTO teamDTO) {
                return memberNodeMatcher.matches(teamDTO.memberInformation);
            }

            @Override public void describeTo(Description description) {
                description.appendText("where the member nodes: ").appendDescriptionOf(memberNodeMatcher);
            }
        });
        return this;
    }
}
