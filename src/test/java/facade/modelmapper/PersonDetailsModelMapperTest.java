package facade.modelmapper;

import data.datatype.PersonId;
import domain.PersonStub;
import domain.StubbedTeam;
import domain.Person;
import domain.StubbedTeam;
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
       PersonStub person = new PersonStub(PersonId.newPersonId("id"), "Hello World", "picture", "email@testemail.com", "BA", null);
       person.setTeam(new StubbedTeam("blah", "blah", Arrays.<Person>asList(person)));

       PersonDetailsModel result = personDetailsModelMapper.map(person);

       assertThat(result, is(aPersonDetailsModel().named("Hello World")));
    }
}
