package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.domain.PersonStub;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.DefaultSearchResultsModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.model.PersonSearchResultMatcher.aPersonSearchResult;
import static uk.co.o2.facewall.model.TeamSearchResultMatcher.aTeamSearchResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInOrder;

public class SearchResultsModelMapperTest {

    SearchResultsModelMapper searchResultsModelMapper = new SearchResultsModelMapper();

    @Test
    public void should_map_persons_into_person_search_results(){

        Team team = mock(Team.class);
        when(team.name()).thenReturn("a team");

        PersonStub fred1 = new PersonStub(newPersonId("1"), "fred smith", "pic1.img", "email1@testemail.com", "Developer", team);
        PersonStub fred2 = new PersonStub(newPersonId("2"), "fred bailey", "pic3.img", "email2@testemail.com", "BA", team);

        List<Person> personList = new ArrayList<>();
        personList.add(fred1);
        personList.add(fred2);
        List<Team> teamList = new ArrayList<>();

        DefaultSearchResultsModel result = searchResultsModelMapper.map(personList, teamList);
        assertThat(result.persons, containsExhaustivelyInOrder(
            aPersonSearchResult().named("fred smith").withPicture("pic1.img").inTeam("a team").withEmail("email1@testemail.com").withRole("Developer"),
            aPersonSearchResult().named("fred bailey").withPicture("pic3.img").inTeam("a team").withEmail("email2@testemail.com").withRole("BA")
        ));
    }

    @Test

    public void should_map_teams_into_team_search_results() {

        List<Person> list1 = new ArrayList<>();
        List<Person> list2 = new ArrayList<>();

        StubbedTeam ecom = new StubbedTeam("Ecom", "blue", list1);
        StubbedTeam anotherTeam = new StubbedTeam("another team", "red", list2);

        List<Person> list3 = new ArrayList<>();
        List list4 = new ArrayList<>();

        list4.add(ecom);
        list4.add(anotherTeam);

        DefaultSearchResultsModel result = searchResultsModelMapper.map(list3, list4);
        assertThat(result.teams, containsExhaustivelyInOrder(
            aTeamSearchResult().named("Ecom"),
            aTeamSearchResult().named("another team")
        ));
    }

    @Test
    public void should_map_persons_team_name_as_empty_string_if_person_is_not_in_a_team() {
        Team team = mock(Team.class);
        when(team.name()).thenReturn("");

        PersonStub teamless = new PersonStub(newPersonId("1"), "teamless", "teamless.img", "email@testemail.com", "BA", team);

        List teamlessList = new ArrayList<>();
        List<Team> emptyTeamList = new ArrayList<>();

        teamlessList.add(teamless);

        DefaultSearchResultsModel result = searchResultsModelMapper.map(teamlessList, emptyTeamList);
        assertThat(result.persons, containsExhaustivelyInOrder(
            aPersonSearchResult().inTeam("")
        ));

    }
}
