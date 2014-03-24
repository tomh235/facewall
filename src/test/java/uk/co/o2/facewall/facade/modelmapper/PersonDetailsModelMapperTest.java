package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.domain.PersonStub;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.model.PersonDetailsModel;
import org.junit.Test;

import java.util.Arrays;

import static uk.co.o2.facewall.model.PersonDetailsModelMatcher.aPersonDetailsModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersonDetailsModelMapperTest{
    PersonDetailsModelMapper personDetailsModelMapper = new PersonDetailsModelMapper();

    @Test
    public void should_map_person() {
       PersonStub person = new PersonStub(PersonId.newPersonId("email@example.com"), "Hello World", "picture", "BA", null);
       person.setTeam(new StubbedTeam("blah", "blah", Arrays.<Person>asList(person)));

       PersonDetailsModel result = personDetailsModelMapper.map(person);

       assertThat(result, is(aPersonDetailsModel()
               .withEmail("email@example.com")
               .named("Hello World")
               .withPicture("picture")
               .withRole("BA")
       ));
    }
}
