package domain;

public class MockPerson implements Person {
    public String name;
    public String picture;
    public Team team;

    public String name() {
        return name;
    }

    public String picture() {
        return picture;
    }

    public Team team() {
        return team;
    }

    public MockPerson(String id, String name, String picture, Team team) {
        this.name = name;
        this.picture = picture;
        this.team = team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
