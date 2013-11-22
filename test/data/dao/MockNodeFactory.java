package data.dao;

import org.neo4j.graphdb.Node;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

abstract class MockNodeFactory {
    static Node createMockNodeWithProperties(Map<String, Object> properties) {
        Node mockNode = mock(Node.class);
        when(mockNode.getPropertyKeys()).thenReturn(properties.keySet());

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            when(mockNode.getProperty(entry.getKey())).thenReturn(entry.getValue());
        }

        return mockNode;
    }
}
