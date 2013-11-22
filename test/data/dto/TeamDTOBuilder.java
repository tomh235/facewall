package data.dto;

public class TeamDTOBuilder {
    private String id;
    private String name;
    private String colour;

    private TeamDTOBuilder() {}

    public static TeamDTOBuilder newTeamDTO() {
        return new TeamDTOBuilder();
    }

    public TeamDTOBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public TeamDTOBuilder named(String name) {
        this.name = name;
        return this;
    }

    public TeamDTOBuilder coloured(String colour) {
        this.colour = colour;
        return this;
    }

    public TeamDTO build() {
        return new TeamDTO(id, name, colour) {};
    }
}
