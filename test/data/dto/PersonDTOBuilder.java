package data.dto;

public class PersonDTOBuilder {
    private String id;
    private String name;
    private String picture;

    private PersonDTOBuilder() {}

    public static PersonDTOBuilder newPersonDTO() {
        return new PersonDTOBuilder();
    }

    public PersonDTOBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public PersonDTOBuilder named(String name) {
        this.name = name;
        return this;
    }

    public PersonDTOBuilder whosePictureIs(String picture) {
        this.picture = picture;
        return this;
    }

    public PersonDTO build() {
        return new PersonDTO(id, name, picture);
    }
}
