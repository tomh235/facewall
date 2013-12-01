package data.fixture;

public class Fixtures {
    public final Iterable<TeamData> teams;

    private Fixtures(Iterable<TeamData> teamData) {
        this.teams = teamData;
    }
}
