package data.dto;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class TeamDTOMatcher extends CompositeMatcher<TeamDTO> {

    private TeamDTOMatcher() {}

    public static TeamDTOMatcher aTeamDTO() {
        return new TeamDTOMatcher();
    }

    public TeamDTOMatcher withId(final String id) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override
            public boolean matchesSafely(TeamDTO dto) {
                return id.equals(dto.id);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with an Id of ").appendValue(id);
            }
        });
        return this;
    }

    public TeamDTOMatcher named(final String name) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override
            public boolean matchesSafely(TeamDTO dto) {
                return name.equals(dto.name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("named ").appendValue(name);
            }
        });
        return this;
    }

    public TeamDTOMatcher coloured(final String colour) {
        add(new TypeSafeMatcher<TeamDTO>() {
            @Override
            public boolean matchesSafely(TeamDTO dto) {
                return colour.equals(dto.colour);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" coloured ").appendValue(colour);
            }
        });
        return this;
    }
}
