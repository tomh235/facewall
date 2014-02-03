package data.dao.database.query;

import util.AbstractWrappingDataType;

public class TeamNodeKey extends AbstractWrappingDataType<String> {
    protected TeamNodeKey(String value) {
        super(value);
    }

    static public TeamNodeKey newTeamNodeKey(String value) {
        return new TeamNodeKey(value);
    }
}
