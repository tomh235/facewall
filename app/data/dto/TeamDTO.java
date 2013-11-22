package data.dto;

public abstract class TeamDTO {
    public final String id;
    public final String name;
    public final String colour;

    public TeamDTO(String id, String name, String colour) {
        this.id = id;
        this.name = name;
        this.colour = colour;
    }
}
