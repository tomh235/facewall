package data.datatype;

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
