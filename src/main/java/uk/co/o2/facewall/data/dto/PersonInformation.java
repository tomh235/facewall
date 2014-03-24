package uk.co.o2.facewall.data.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import uk.co.o2.facewall.data.datatype.PersonId;

import javax.validation.Valid;

import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.data.datatype.PersonId.noPersonId;

public class PersonInformation {

    private static final PersonInformation noPersonInformation = newPersonInformation().build();


    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email is invalid")
    private final PersonId id;
    @NotBlank(message = "Name must not be blank")
    @Length(max=50, message = "Length must be less than 50 characters")
    private final String name;


    private final String email;
    @NotBlank(message = "Role must not be blank")
    private final String role;
    @NotBlank(message = "Picture must not be blank")
    @URL(message = "The URL is invalid")
    private final String picture;


    private PersonInformation(Builder builder) {
        id = builder.id;
        name = builder.name;
        picture = builder.picture;
        email = null;
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

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }
    }
}
