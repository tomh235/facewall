package data.dao.database.query;

import data.dao.database.QueryResultRow;
import data.dto.PersonInformation;
import data.dto.PersonInformationMapper;
import data.dto.TeamInformation;
import data.dto.TeamInformationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static data.dao.database.query.FacewallQueryResultsMapperTest.MockCypherResultsBuilder.MockCypherResultsRowBuilder.defaultRow;
import static data.dao.database.query.FacewallQueryResultsMapperTest.MockCypherResultsBuilder.MockCypherResultsRowBuilder.teamlessPersonRow;
import static data.dao.database.query.FacewallQueryResultsMapperTest.MockCypherResultsBuilder.results;
import static data.dao.database.query.FacewallQueryResultsMatcher.areQueryResults;
import static data.dao.database.query.PersonNodeKey.newPersonNodeKey;
import static data.dao.database.query.QueryResultRowMatcher.aRow;
import static data.dao.database.query.TeamNodeKey.newTeamNodeKey;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FacewallQueryResultsMapperTest {
    
    private static final PersonNodeKey person = newPersonNodeKey("person");
    private static final TeamNodeKey team = newTeamNodeKey("team");

    @Mock private PersonInformationMapper mockPersonInformationMapper;
    @Mock private TeamInformationMapper mockTeamInformationMapper;

    @InjectMocks
    private FacewallQueryResultsMapper facewallQueryResultsMapper;

    @Test
    public void map_multiple_rows() throws Exception {
        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(
                        defaultRow(),
                        defaultRow(),
                        defaultRow(),
                        defaultRow(),
                        defaultRow()
                ).build();

        Iterable<QueryResultRow> result = facewallQueryResultsMapper.map(person, team, cypherResults);
        assertThat(result, is(areQueryResults()
                .withRowCount(5)
        ));
    }

    @Test
    public void map_personInformation_for_rows_with_person_and_team() throws Exception {
        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(defaultRow())
                .build();

        PersonInformation expectedPersonInformation = mock(PersonInformation.class);
        when(mockPersonInformationMapper.map(any(Node.class))).thenReturn(expectedPersonInformation);

        Iterable<QueryResultRow> result = facewallQueryResultsMapper.map(person, team, cypherResults);
        assertThat(result, is(areQueryResults().containingARow(
                aRow()
                    .withPersonInformation(sameInstance(expectedPersonInformation)))
        ));
    }

    @Test
    public void map_teamInformation_for_rows_with_person_and_team() throws Exception {
        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(defaultRow())
                .build();

        TeamInformation expectedTeamInformation = mock(TeamInformation.class);
        when(mockTeamInformationMapper.map(any(Node.class))).thenReturn(expectedTeamInformation);

        Iterable<QueryResultRow> result = facewallQueryResultsMapper.map(person, team, cypherResults);
        assertThat(result, is(areQueryResults().containingARow(
                aRow()
                        .withTeamInformation(sameInstance(expectedTeamInformation)))
        ));
    }

    @Test
    public void personInformation_mapped_using_mapper_for_rows_with_person_and_team() throws Exception {
        Node expectedPerson = mock(Node.class);

        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(defaultRow()
                    .withPerson(expectedPerson)
                ).build();

        facewallQueryResultsMapper.map(person, team, cypherResults);

        verify(mockPersonInformationMapper).map(expectedPerson);
    }
    
    @Test
    public void teamInformation_mapped_using_mapper_for_rows_with_person_and_team() throws Exception {
        Node expectedTeam = mock(Node.class);

        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(defaultRow()
                    .withTeam(expectedTeam)
                ).build();

        facewallQueryResultsMapper.map(person, team, cypherResults);

        verify(mockTeamInformationMapper).map(expectedTeam);
    }

    @Test
    public void personInformation_mapped_using_mapper_for_rows_with_teamless_person() throws Exception {
        Node expectedPerson = mock(Node.class);

        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(teamlessPersonRow()
                        .withPerson(expectedPerson)
                ).build();

        facewallQueryResultsMapper.map(person, team, cypherResults);

        verify(mockPersonInformationMapper).map(expectedPerson);
    }
    
    @Test
    public void teamInformation_mapped_using_mapper_for_rows_with_teamless_person() throws Exception {
        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(teamlessPersonRow()
                ).build();

        facewallQueryResultsMapper.map(person, team, cypherResults);

        verify(mockTeamInformationMapper).map(null);
    }

    @Test
    public void personInformation_mapped_using_mapper_for_multiple_rows() throws Exception {
        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(
                        defaultRow(),
                        defaultRow(),
                        defaultRow()
                ).build();

        facewallQueryResultsMapper.map(person, team, cypherResults);

        verify(mockPersonInformationMapper, times(3)).map(any(Node.class));
    }
    
    @Test
    public void teamInformation_mapped_using_mapper_for_multiple_rows() throws Exception {
        Iterable<Map<String, Object>> cypherResults = results()
                .withRows(
                        defaultRow(),
                        defaultRow(),
                        defaultRow(),
                        defaultRow()
                ).build();

        facewallQueryResultsMapper.map(person, team, cypherResults);

        verify(mockTeamInformationMapper, times(4)).map(any(Node.class));
    }

    static class MockCypherResultsBuilder {
        
        private List<Map<String, Object>> queryResults = new ArrayList<>();
        
        public static MockCypherResultsBuilder results() {
            return new MockCypherResultsBuilder();
        }
        
        public MockCypherResultsBuilder withRows(MockCypherResultsRowBuilder... rowBuilders) {
            for (MockCypherResultsRowBuilder rowBuilder : rowBuilders) {
                queryResults.add(rowBuilder.build());
            }
            
            return this;
        }
        
        public Iterable<Map<String, Object>> build() {
            return queryResults;
        }

        static class MockCypherResultsRowBuilder {

            private Map<String, Object> row = new HashMap<>();

            public static MockCypherResultsRowBuilder emptyRow() {
                return new MockCypherResultsRowBuilder();
            }

            public static MockCypherResultsRowBuilder defaultRow() {
                return emptyRow()
                        .withPerson(mock(Node.class))
                        .withTeam(mock(Node.class));
            }

            public static MockCypherResultsRowBuilder teamlessPersonRow() {
                return emptyRow()
                        .withPerson(mock(Node.class));
            }

            public MockCypherResultsRowBuilder withPerson(Node personNode) {
                row.put(person.value, personNode);
                return this;
            }

            public MockCypherResultsRowBuilder withTeam(Node teamNode) {
                row.put(team.value, teamNode);
                return this;
            }

            public Map<String, Object> build() {
                return row;
            }
        }
    }
}
