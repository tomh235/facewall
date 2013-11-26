package model;

import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints.*;

public class UserModel {
    @Required
    public String name;

    @Required
    @URL
    public String imgURL;

    @Required
    public String role;

    @Required
    public String location;

    @Required
    @Email
    public String email;

    @Required
    public String team;

    public String scrum;
}
