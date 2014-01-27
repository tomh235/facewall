package facade.modelmapper;

import data.datatype.PersonId;
import domain.MockPerson;
import domain.StubbedTeam;
import domain.Person;
import model.PersonDetailsModel;
import org.junit.Test;

import java.util.Arrays;

import static model.PersonDetailsModelMatcher.aPersonDetailsModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersonDetailsModelMapperTest{
    PersonDetailsModelMapper personDetailsModelMapper = new PersonDetailsModelMapper();

    @Test
    public void should_map_person() {
       MockPerson person = new MockPerson(PersonId.newPersonId("id"), "Hello World", "picture", null, "email@testemail.com");
       person.setTeam(new StubbedTeam("blah", "blah", Arrays.<Person>asList(person)));

       PersonDetailsModel result = personDetailsModelMapper.map(person);

       assertThat(result, is(aPersonDetailsModel().named("Hello World")));
    }
}
