package data.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import data.dto.PersonDTO;

public class JacksonMappedPersonDTO extends PersonDTO {

    @JsonCreator JacksonMappedPersonDTO(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("picture") String picture)
    { super(id, name, picture); }
}
