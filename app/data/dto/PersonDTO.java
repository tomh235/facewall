package data.dto;

public class PersonDTO {
    public final String id;
    public final String name;
    public final String picture;

    PersonDTO(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }
}
