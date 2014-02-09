package uk.co.o2.facewall.data.datatype;

import uk.co.o2.facewall.util.AbstractWrappingDataType;

public class TeamId extends AbstractWrappingDataType<String> {
    private TeamId(String value) {
        super(value);
    }

    public static TeamId newTeamId(String id) {
        return new TeamId(id);
    }

    public static TeamId noTeamId() {
        return new TeamId("");
    }
}
