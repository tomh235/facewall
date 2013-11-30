package data.factory;

import data.dto.TeamDTO;
import data.mapper.MutablePerson;
import data.mapper.PersonMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MembersFactoryTest {

    @Mock MutablePersonFactory mockMutablePersonFactory;
    @Mock PersonMapper mockPersonMapper;
    private MembersFactory membersFactory;

    @Before
    public void setUp() throws Exception {
        membersFactory = new MembersFactory(mockPersonMapper, mockMutablePersonFactory);
    }

    @Test
    public void create_members_passes_nodes_to_mapper() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);
        List<Node> expectedPersonNodesList = asList(expectedPersonNode1, expectedPersonNode2);

        membersFactory.createMembers(expectedPersonNodesList);

        verify(mockPersonMapper).map(any(MutablePerson.class), eq(expectedPersonNode1));
        verify(mockPersonMapper).map(any(MutablePerson.class), eq(expectedPersonNode2));
    }

    @Test
    public void create_members_creates_mutable_person_and_passes_to_mapper() {
        MutablePerson expectedMutablePerson1 = mock(MutablePerson.class);
        MutablePerson expectedMutablePerson2 = mock(MutablePerson.class);

        when(mockMutablePersonFactory.createMutablePerson())
            .thenReturn(expectedMutablePerson1)
            .thenReturn(expectedMutablePerson2);

        List<Node> membersNodes = asList(mock(Node.class), mock(Node.class));
        membersFactory.createMembers(membersNodes);

        verify(mockPersonMapper).map(eq(expectedMutablePerson1), any(Node.class));
        verify(mockPersonMapper).map(eq(expectedMutablePerson2), any(Node.class));
    }
}
