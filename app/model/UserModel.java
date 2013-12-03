package model;

import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints.*;

public class UserModel {
    @Required(message = "An Email address is required")
    @Email(message = "Please enter a valid email")
    @MaxLength(value = 50, message = "Maximum email length is 50 characters")
    public String email;

    @Required(message = "An image URL is a required")
    @URL(message = "Please enter a valid URL")
    @MaxLength(value = 50, message = "Maximum URL length is 50 characters")
    public String imgURL;

    @Required(message = "An Email is required")
    @MaxLength(value = 50, message = "Maximum name length is 50 characters")
    public String name;

    @Required(message = "A Role is required")
    public String role;

    @Required(message = "A Location is required")
    public String location;

    @Required(message = "A Team is required")
    public String team;
    public String scrum;
}