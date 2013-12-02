package facade.modelmapper;

import model.PersonDetailsModel;
import org.junit.Test;
import domain.MockPerson;

import static model.PersonDetailsModelMatcher.aPersonDetailsModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersonDetailsModelMapperTest{
    PersonDetailsModelMapper personDetailsModelMapper = new PersonDetailsModelMapper();

    @Test
    public void should_map_person() {
       MockPerson person = new MockPerson("id", "Hello World", "picture", null);
       PersonDetailsModel result = personDetailsModelMapper.map(person);

       assertThat(result, is(aPersonDetailsModel().named("Hello World")));
    }
}
