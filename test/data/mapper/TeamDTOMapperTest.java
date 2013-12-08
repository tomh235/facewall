package data.mapper;

import domain.Person;
import domain.Team;
import org.junit.Test;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.List;

import static data.dao.MockNodeFactory.createMockNodeWithProperties;
import static domain.TeamMatcher.aTeam;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TeamDTOMapperTest {

    private final TeamDTOMapper teamDTOMapper = new TeamDTOMapper();

    private static class TestMutableTeam extends MutableTeam {
        @Override public List<Person> members() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void map_name() {
        Node teamNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("name", "expendables");
        }});

        Team result = teamDTOMapper.map(new TestMutableTeam(), teamNode);

        assertThat(result, is(aTeam()
            .named("expendables")
        ));
    }

    @Test
    public void map_colour() {
        Node teamNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("colour", "green");
        }});

        Team result = teamDTOMapper.map(new TestMutableTeam(), teamNode);

        assertThat(result, is(aTeam()
            .withColour("green")
        ));
    }
}
