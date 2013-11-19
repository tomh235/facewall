package data;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;

import static data.DatabaseUtils.MEMBER_OF;
import static data.DatabaseUtils.personIndexName;
import static data.DatabaseUtils.teamIndexName;

abstract public class FixturesFactory {

    public static Fixtures createDefaultFixtures() {
        return new Fixtures() {
            @Override public void seedDatabase(GraphDatabaseService db) {

                IndexManager indexManager = db.index();
                Index<Node> personIndex = indexManager.forNodes(personIndexName);
                Index<Node> teamIndex = indexManager.forNodes(teamIndexName);

                Node hugo = db.createNode();
                hugo.setProperty("id", "1");
                hugo.setProperty("name", "Hugo Wainwright");
                hugo.setProperty("picture", "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-frc1/300430_4471674470606_1745866994_n.jpg");

                addNodeToIndex(personIndex, hugo);

                Node shop = db.createNode();
                shop.setProperty("id", "2");
                shop.setProperty("name", "Shop");
                shop.setProperty("colour", "43BF76");

                addNodeToIndex(teamIndex, shop);

                hugo.createRelationshipTo(shop, MEMBER_OF);

                Node fahran = db.createNode();
                fahran.setProperty("id", "3");
                fahran.setProperty("name", "Fahran Wallace");
                fahran.setProperty("picture", "http://withhomeandgarden.com/wp-content/uploads/2011/01/cat-200x300.jpg");

                addNodeToIndex(personIndex, fahran);

                Node ars = db.createNode();
                ars.setProperty("id", "4");
                ars.setProperty("name", "Checkout");
                ars.setProperty("colour", "aaff88");

                addNodeToIndex(teamIndex, ars);

                fahran.createRelationshipTo(ars, MEMBER_OF);

                Node team1 = db.createNode();
                team1.setProperty("id", "5");
                team1.setProperty("name", "Team 1");
                team1.setProperty("colour", "E8A5C6");

                addNodeToIndex(teamIndex, team1);

                for (int id = 6; id <= 12; id++) {
                    Node person = db.createNode();
                    person.setProperty("id", Integer.toString(id));
                    person.setProperty("name", "Person " + Integer.toString(id));
                    person.setProperty("picture", "http://lorempixel.com/300/400/cats/" + Integer.toString(id));
                    person.createRelationshipTo(team1, MEMBER_OF);

                    addNodeToIndex(personIndex, person);
                }

                Node team2 = db.createNode();
                team2.setProperty("id", "13");
                team2.setProperty("name", "Lots of Members");
                team2.setProperty("colour", "BA9827");

                addNodeToIndex(teamIndex, team2);

                for (int id = 13; id <= 32; id++) {
                    Node person = db.createNode();
                    person.setProperty("id", Integer.toString(id));
                    person.setProperty("name", "Person " + Integer.toString(id));
                    person.setProperty("picture", "http://lorempixel.com/300/400/cats/" + Integer.toString(id));
                    person.createRelationshipTo(team2, MEMBER_OF);

                    addNodeToIndex(personIndex, person);
                }

                for (int id = 33; id <= 37; id++) {
                    Node person = db.createNode();
                    person.setProperty("id", Integer.toString(id));
                    person.setProperty("name", "Teamless " + Integer.toString(id));
                    person.setProperty("picture", "http://lorempixel.com/300/400/cats/" + Integer.toString(id));

                    addNodeToIndex(personIndex, person);
                }
            }

            private void addNodeToIndex(Index<Node> index, Node node) {
                index.add(node, "id", node.getProperty("id"));
            }
        };
    }
}
