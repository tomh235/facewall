package uk.co.o2.facewall.data.dao.database.query;

import uk.co.o2.facewall.util.AbstractWrappingDataType;

public class TeamNodeKey extends AbstractWrappingDataType<String> {
    protected TeamNodeKey(String value) {
        super(value);
    }

    static public TeamNodeKey newTeamNodeKey(String value) {
        return new TeamNodeKey(value);
    }
}
