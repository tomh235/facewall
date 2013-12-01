package data.fixture;

import java.util.HashMap;

public class TeamData extends HashMap<String, Object> {

    public final Iterable<PersonData> members;

    TeamData(Iterable<PersonData> members) {
        this.members = members;
    }
}
