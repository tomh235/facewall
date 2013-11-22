package data.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import data.dto.PersonDTO;
import data.dto.TeamDTO;

public class JacksonMappedTeamDTO extends TeamDTO {

    @JsonCreator JacksonMappedTeamDTO(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("colour") String colour)
    { super(id, name, colour); }
}
