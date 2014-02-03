package data.dto;

import org.junit.Test;
import org.neo4j.graphdb.Node;

import java.util.HashMap;

import static data.dao.MockNodeFactory.createMockNodeWithProperties;
import static data.datatype.TeamId.newTeamId;
import static data.dto.TeamInformation.noTeamInformation;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TeamInformationMapperTest {

    private final TeamInformationMapper teamInformationMapper = new TeamInformationMapper();

    @Test
    public void map_id() throws Exception {
        Node mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("id", "some-id");
        }});
        
        TeamInformation result = teamInformationMapper.map(mockNode);
        assertThat(result.getId(), is(newTeamId("some-id")));
    }
    
    @Test
    public void map_name() throws Exception {
        Node mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("name", "blinky's team");
        }});
        
        TeamInformation result = teamInformationMapper.map(mockNode);
        assertThat(result.getName(), is("blinky's team"));
    }

    @Test
    public void map_picture() throws Exception {
        Node mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("colour", "blue");
        }});

        TeamInformation result = teamInformationMapper.map(mockNode);
        assertThat(result.getColour(), is("blue"));
    }

    @Test
    public void map_null_to_no_team_information() throws Exception {
        TeamInformation result = teamInformationMapper.map(null);

        assertThat(result, is(sameInstance(noTeamInformation())));
    }
}
