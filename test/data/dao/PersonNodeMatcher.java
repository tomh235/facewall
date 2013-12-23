package data.dao;

import data.dao.database.RelationshipTypes;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import util.CompositeMatcher;

public class PersonNodeMatcher extends CompositeMatcher<Node>{
    private PersonNodeMatcher(){
        super();
    }

    public static PersonNodeMatcher aPersonNode() {
        return new PersonNodeMatcher();
    }

    public PersonNodeMatcher withId(final String id) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return id.equals(target.getId());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with an id of ").appendValue(id);
            }
        });
        return this;
    }

    public PersonNodeMatcher withName(final String name) {
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

    public PersonNodeMatcher withPicture(final String picture) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return picture.equals(target.getProperty("imgURL"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("whose picture is at ").appendValue(picture);
            }
        });
        return this;
    }

    public PersonNodeMatcher withEmail(final String email) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return email.equals(target.getProperty("email"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("whose email is at ").appendValue(email);
            }
        });
        return this;
    }

    public PersonNodeMatcher locatedIn(final String location) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return location.equals(target.getProperty("location"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("located in ").appendValue(location);
            }
        });
        return this;
    }

    public PersonNodeMatcher relatedToTeamNode(final Matcher<Node> teamNode) {
        add(new TypeSafeMatcher<Node>() {

            @Override
            public boolean matchesSafely(Node target) {
                return teamNode.matches(target.getSingleRelationship(RelationshipTypes.TEAMMEMBER_OF, Direction.OUTGOING));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("in the team: ").appendDescriptionOf(teamNode);
            }
        });
        return this;
    }
}
