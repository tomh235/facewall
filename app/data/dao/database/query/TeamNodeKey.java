package data.dao.database.query;

import util.AbstractWrappingDataType;

public class TeamNodeKey extends AbstractWrappingDataType {
    protected TeamNodeKey(Object value) {
        super(value);
    }

    static public TeamNodeKey newTeamNodeKey(String value) {
        return new TeamNodeKey(value);
    }
}
