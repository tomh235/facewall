package data.dao;

import com.google.common.collect.ImmutableMap;
import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.dto.TeamDTOMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import static data.dao.MockNodeFactory.createMockNodeWithProperties;
import static data.dao.database.IndexQueryMatcher.anIndexQuery;
import static data.dto.PersonDTOMatcher.aPersonDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDAOTest {

    private static final String teamIndexName = "Teams";
    private static final String personIndexName = "Persons";
    private static final String indexKey = "id";

    @Mock FacewallDB mockDB;
    private FacewallDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new FacewallDAO(mockDB);
    }

    @Test
    public void fetchPerson_found() {
        Node personNode = createMockNodeWithProperties(ImmutableMap.<String, Object>of(
                "id", "1",
                "name", "person",
                "picture", "picture.img"
        ));
        when(mockDB.retrieveNodeFromIndex(any(IndexQuery.class))).thenReturn(personNode);

        PersonDTO result = dao.fetchPerson(new PersonId("1"));
        assertThat(result, is(aPersonDTO()
            .withId("1")
            .named("person")
            .whosePictureIs("picture.img")
        ));
    }
    
    @Test
    public void fetchPerson_found_verifyInteractions() {
        Node personNode = createMockNodeWithProperties(ImmutableMap.<String, Object>of(
                "id", "1",
                "name", "person",
                "picture", "picture.img"
        ));
        when(mockDB.retrieveNodeFromIndex(any(IndexQuery.class))).thenReturn(personNode);

        dao.fetchPerson(new PersonId("1"));
        verify(mockDB).retrieveNodeFromIndex(argThat(is(anIndexQuery()
                .queryingOnAnIndexNamed(personIndexName)
                .queryingOnTheKey(indexKey)
                .queryingForTheValue("1")
        )));
    }

    @Test
    public void fetchTeam_found() {
        Node teamNode = createMockNodeWithProperties(ImmutableMap.<String, Object>of(
                "id", "2",
                "name", "team",
                "colour", "blue"
        ));
        when(mockDB.retrieveNodeFromIndex(any(IndexQuery.class))).thenReturn(teamNode);

        TeamDTO result = dao.fetchTeam(new TeamId("2"));
        assertThat(result, is(TeamDTOMatcher.aTeamDTO()
            .withId("2")
            .named("team")
            .coloured("blue")
        ));
    }

    @Test
    public void fetchTeam_found_verifyInteractions() {
        Node personNode = createMockNodeWithProperties(ImmutableMap.<String, Object>of(
                "id", "2",
                "name", "team",
                "colour", "blue"
        ));
        when(mockDB.retrieveNodeFromIndex(any(IndexQuery.class))).thenReturn(personNode);

        dao.fetchTeam(new TeamId("2"));
        verify(mockDB).retrieveNodeFromIndex(argThat(is(anIndexQuery()
                .queryingOnAnIndexNamed(teamIndexName)
                .queryingOnTheKey(indexKey)
                .queryingForTheValue("2")
        )));
    }
}
