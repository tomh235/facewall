package data.dto;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.neo4j.graphdb.Node;
import util.CompositeMatcher;

import java.util.List;

public class TeamDTOMatcher extends CompositeMatcher<TeamDTO> {

    private TeamDTOMatcher() {}

    public static TeamDTOMatcher aTeamDTO() {
        return new TeamDTOMatcher();
    }

    public TeamDTOMatcher withTeamNode(final Matcher<Node> nodeMatcher) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override public boolean matchesSafely(TeamDTO teamDTO) {
                return nodeMatcher.matches(teamDTO.teamNode);
            }

            @Override public void describeTo(Description description) {
                description.appendText("where the team node is: ").appendDescriptionOf(nodeMatcher);
            }
        });
        return this;
    }

    public TeamDTOMatcher whereMemberNodes(final Matcher<List<Node>> memberNodeMatcher) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override public boolean matchesSafely(TeamDTO teamDTO) {
                return memberNodeMatcher.matches(teamDTO.memberNodes);
            }

            @Override public void describeTo(Description description) {
                description.appendText("where the member nodes: ").appendDescriptionOf(memberNodeMatcher);
            }
        });
        return this;
    }
}
