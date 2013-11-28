package model;

import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints.*;

public class UserModel {
    @Required(message = "An Email address is required")
    @Email(message = "Please enter a valid email")
    public String email;
    @Required(message = "An image URL is a required")
    @URL(message = "Please enter a valid URL")
    public String imgURL;
    @Required(message = "An Email is required")
    public String name;
    @Required(message = "A Role is required")
    public String role;
    @Required(message = "A Location is required")
    public String location;
    @Required(message = "A Team is required")
    public String team;
    public String scrum;
}
