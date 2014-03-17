package uk.co.o2.facewall.facade.modelmapper;

import org.junit.Test;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.PersonStub;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.OverviewEntryModel;
import uk.co.o2.facewall.model.TeamDetailsModel;
import uk.co.o2.facewall.model.TeamDetailsWithPersonsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.model.TeamDetailsModelMatcher.aTeamDetailsModel;
import static uk.co.o2.facewall.model.TeamDetailsWithPersonsModelMatcher.aTeamDetailsWithPersonsModel;

public class TeamDetailsModelMapperTest {
    TeamDetailsModelMapper teamDetailsModelMapper = new TeamDetailsModelMapper();

    @Test
    public void should_map_team() {
        PersonStub ecom_member1 = new PersonStub(newPersonId("3"), "ecom_member1", "pic1.img", "email1@testemail.com", "BA", null);
        Team ecom = new StubbedTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));

        TeamDetailsModel result = teamDetailsModelMapper.map(ecom);
        assertThat(result, is(aTeamDetailsModel().named("ecom").withColour("blue").sized(1)));
    }

    @Test
    public void should_map_team_with_persons() {
        PersonStub person1 = new PersonStub(newPersonId("1"), "person1", "pic1.img", "email1@testemail.com", "BA", null);
        PersonStub person2 = new PersonStub(newPersonId("2"), "person2", "pic2.img", "email2@testemail.com", "BA", null);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(person1, person2));

        Team stubTeam = new StubbedTeam("stubTeam", "yellow", persons);

        person1.setTeam(stubTeam);
        person2.setTeam(stubTeam);

        List<OverviewEntryModel> entries = new ArrayList<>();
        OverviewModelMapper overviewModelMapper = new OverviewModelMapper();

        entries.add(overviewModelMapper.map(person1));
        entries.add(overviewModelMapper.map(person2));

        TeamDetailsWithPersonsModel result = teamDetailsModelMapper.mapWithPersons(stubTeam, entries);

        assertThat(result, is(aTeamDetailsWithPersonsModel().named("stubTeam").sized(2).withColour("yellow").containing(entries)));
    }
}
