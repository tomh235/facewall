package data.dto;

import data.datatype.PersonId;

import static data.datatype.PersonId.newPersonId;
import static data.datatype.PersonId.noPersonId;

public class PersonInformation {

    private static final PersonInformation noPersonInformation = newPersonInformation().build();

    private final PersonId id;
    private final String name;
    private final String picture;
    private final String email;
    private final String role;


    private PersonInformation(Builder builder) {
        id = builder.id;
        name = builder.name;
        picture = builder.picture;
        email = builder.email;
        role = builder.role;
    }

    public static Builder newPersonInformation() {
        return new Builder();
    }

    public static PersonInformation noPersonInformation() {
        return noPersonInformation;
    }

    public PersonId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getEmail() {
        return email;
    }

    public String getRole(){
        return role;
    }

    public static class Builder {
        private PersonId id = noPersonId();
        private String name = "";
        private String picture = "";
        private String email = "";
        private String role = "";

        public PersonInformation build() {
            return new PersonInformation(this);
        }

        public Builder withId(String id) {
            this.id = newPersonId(id);
            return this;
        }

        public Builder named(String name) {
            this.name = name;
            return this;
        }

        public Builder withPicture(String picture) {
            this.picture = picture;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }
    }
}
