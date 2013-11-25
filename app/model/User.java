package model;

import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints.*;

public class User {
    @Required
    public String name;

    @Required
    @URL
    public String imglink;

    @Required
    public String role;

    @Required
    public String location;

    @Required
    @Email
    public String email;

    @Required
    public String team;

    @Required
    public String scrum;
}
