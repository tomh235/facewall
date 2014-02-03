package data.dto;

public class PersonDTO {
    public final PersonInformation personInformation;
    public final TeamInformation teamInformation;

    public PersonDTO(PersonInformation personInformation, TeamInformation teamInformation) {
        this.personInformation = personInformation;
        this.teamInformation = teamInformation;
    }
}
