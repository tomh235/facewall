package uk.co.o2.facewall.data.dao;

import uk.co.o2.facewall.data.dao.database.RelationshipTypes;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import uk.co.o2.facewall.util.CompositeMatcher;

import java.util.List;

public class TeamNodeMatcher extends CompositeMatcher<Node> {
    private TeamNodeMatcher(){
        super();
    }

    public static TeamNodeMatcher aTeamNode() {
        return new TeamNodeMatcher();
    }

    public TeamNodeMatcher withId(final String id) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return id.equals(target.getProperty("id"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with an id of ").appendValue(id);
            }
        });
        return this;
    }

    public TeamNodeMatcher withName(final String name) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return name.equals(target.getProperty("name"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("named ").appendValue(name);
            }
        });
        return this;
    }

    public TeamNodeMatcher withColour(final String colour) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return colour.equals(target.getProperty("colour"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with colour: ").appendValue(colour);
            }
        });
        return this;
    }

    public TeamNodeMatcher relatedToPersonNodes(final Matcher<List<Node>> personNodes) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return personNodes.matches(target.getRelationships(RelationshipTypes.TEAMMEMBER_OF, Direction.INCOMING));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with members: ").appendDescriptionOf(personNodes);
            }
        });
        return this;
    }
}