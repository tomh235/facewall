package data.dto;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.neo4j.graphdb.Node;
import util.CompositeMatcher;

public class PersonDTOMatcher extends CompositeMatcher<PersonDTO> {
    private PersonDTOMatcher() {}

    public static PersonDTOMatcher aPersonDTO() {
        return new PersonDTOMatcher();
    }

    public PersonDTOMatcher withPersonNode(final Matcher<Node> nodeMatcher) {
        add(new TypeSafeMatcher<PersonDTO>() {
            @Override public boolean matchesSafely(PersonDTO personDTO) {
                return nodeMatcher.matches(personDTO.personNode);
            }

            @Override public void describeTo(Description description) {
                description.appendText("with a person node matching: ").appendDescriptionOf(nodeMatcher);
            }
        });
        return this;
    }

    public PersonDTOMatcher withTeamNode(final Matcher<Node> nodeMatcher) {
        add(new TypeSafeMatcher<PersonDTO>() {
            @Override public boolean matchesSafely(PersonDTO personDTO) {
                return nodeMatcher.matches(personDTO.teamNode);
            }

            @Override public void describeTo(Description description) {
                description.appendText("with a team node matching: ").appendDescriptionOf(nodeMatcher);
            }
        });
        return this;
    }
}
