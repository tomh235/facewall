package uk.co.o2.facewall.data.dao;

import uk.co.o2.facewall.data.dao.database.ItemNotFoundException;
import uk.co.o2.facewall.data.dao.database.RelationshipTypes;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.databaseutils.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.Map;

import static uk.co.o2.facewall.data.dao.PersonNodeMatcher.aPersonNode;
import static uk.co.o2.facewall.data.dao.TeamNodeMatcher.aTeamNode;
import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.data.datatype.TeamId.newTeamId;
import static uk.co.o2.facewall.data.dto.PersonInformation.newPersonInformation;
import static uk.co.o2.facewall.databaseutils.FacewallTestDatabaseFactory.createImpermanentFacewallTestDatabase;
import static uk.co.o2.facewall.databaseutils.fixture.Fixtures.newFixtures;
import static uk.co.o2.facewall.databaseutils.fixture.PersonDataFactory.defaultPerson;
import static uk.co.o2.facewall.databaseutils.fixture.TeamDataFactory.defaultTeam;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdminDAOTest {

    private FacewallTestDatabase facewallTestDatabase;
    private AdminDAO adminDAO;

    @Before
    public void setUp() throws Exception {
        facewallTestDatabase = createImpermanentFacewallTestDatabase();
        adminDAO = new AdminDAO(facewallTestDatabase);
    }

    @Test
    public void add_properties_to_node_delegates_to_neo4j_add_properties() throws Exception {
        Node mockNode = mock(Node.class);
        Map<String, String> stubProperties = new HashMap<>();
        stubProperties.put("name", "bob");
        stubProperties.put("imgUrl", "http://www.image.jpeg");

        adminDAO.addPropertiesToNode(mockNode, stubProperties);

        verify(mockNode).setProperty("name", "bob");
        verify(mockNode).setProperty("imgUrl", "http://www.image.jpeg");
    }

    @Test
    public void add_relationships_delegates_to_neo4j_create_relationship_to() throws Exception {
        Node mockNode = mock(Node.class);
        Node mockTeamNode = mock(Node.class);
        Map<Node, RelationshipTypes> stubProperties = new HashMap<>();
        stubProperties.put(mockTeamNode, RelationshipTypes.TEAMMEMBER_OF);

        adminDAO.addRelationshipsToNode(mockNode, stubProperties);

        verify(mockNode).createRelationshipTo(mockTeamNode, RelationshipTypes.TEAMMEMBER_OF);
    }

    @Test
    public void add_person_to_team_looks_person_up_by_id() throws Exception {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeamlessPersons(
                        defaultPerson()
                            .withProperty("id", "person-id")
                )
                .withTeams(
                        defaultTeam()
                            .withProperty("id", "team-id")
                )
        );

        adminDAO.addPersonToTeam(newPersonId("person-id"), newTeamId("team-id"));

        Node result = facewallTestDatabase.findPersonById("person-id");
        assertThat(result, is(aPersonNode()
                .relatedToTeamNode(aTeamNode()
                        .withId("team-id")
                )
        ));
    }

    @Test(expected = ItemNotFoundException.class)
    public void add_person_to_team_throws_item_not_found_given_no_person_exists_with_that_id() throws Exception {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeams(defaultTeam()
                        .withProperty("id", "existent-team")
                )
        );

        adminDAO.addPersonToTeam(newPersonId("non-existent-person"), newTeamId("existent-team"));
    }

    @Test(expected = ItemNotFoundException.class)
    public void add_person_to_team_throws_item_not_found_given_no_team_exists_with_that_id() throws Exception {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeamlessPersons(
                        defaultPerson()
                            .withProperty("id", "existent-person")
                )
        );

        adminDAO.addPersonToTeam(newPersonId("existent-person"), newTeamId("non-existent-team"));
    }

    @Test
    public void save_person_information_saves_node_with_id() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withId("person-id")
                .build();

        adminDAO.savePersonInformation(personInformation);

        assertThat(facewallTestDatabase.findPersonById("person-id"), is(notNullValue()));
    }

    @Test
    public void save_person_information_saves_node_with_properties() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withId("person-to-save")
                .named("Jonathan")
                .withPicture("picture.jpg")
                .build();

        adminDAO.savePersonInformation(personInformation);
        Node result = facewallTestDatabase.findPersonById("person-to-save");

        assertThat(result, is(aPersonNode()
                .withName("Jonathan")
                .withPicture("picture.jpg")
        ));
    }
}
