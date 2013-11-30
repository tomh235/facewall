package data.factory.person;

import domain.Person;
import domain.Team;
import org.junit.Test;

import static data.factory.person.DefaultPersonBuilder.newDefaultPerson;
import static domain.PersonMatcher.aPerson;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class DefaultPersonBuilderTest {

    @Test
    public void build_person_with_name() {
        Person result = newDefaultPerson()
            .named("Wing Yip")
            .build();

        assertThat(result, is(aPerson()
            .named("Wing Yip")
        ));
    }

    @Test
    public void build_person_with_picture() {
        Person result = newDefaultPerson()
            .whosePictureIs("map.img")
            .build();

        assertThat(result, is(aPerson()
            .withPicture("map.img")
        ));
    }

    @Test
    public void build_person_with_team() {
        Team expectedTeam = mock(Team.class);
        Person result = newDefaultPerson()
            .inTeam(expectedTeam)
            .build();

        assertThat(result, is(aPerson()
            .inTeam(sameInstance(expectedTeam))
        ));
    }
}