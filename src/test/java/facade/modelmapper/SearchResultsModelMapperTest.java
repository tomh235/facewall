package facade.modelmapper;

import domain.MockPerson;
import domain.Person;
import domain.StubbedTeam;
import domain.Team;
import model.DefaultSearchResultsModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static data.datatype.PersonId.newPersonId;
import static model.PersonSearchResultMatcher.aPersonSearchResult;
import static model.TeamSearchResultMatcher.aTeamSearchResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static util.IterableMatchers.containsExhaustivelyInOrder;

public class SearchResultsModelMapperTest {

    SearchResultsModelMapper searchResultsModelMapper = new SearchResultsModelMapper();

    @Test
    public void should_map_persons_into_person_search_results(){

        Team team = mock(Team.class);
        when(team.name()).thenReturn("a team");

        MockPerson fred1 = new MockPerson(newPersonId("1"), "fred smith", "pic1.img", team, "email1@testemail.com");
        MockPerson fred2 = new MockPerson(newPersonId("2"), "fred bailey", "pic3.img", team, "email2@testemail.com");

        List<Person> personList = new ArrayList<>();
        personList.add(fred1);
        personList.add(fred2);
        List<Team> teamList = new ArrayList<>();

        DefaultSearchResultsModel result = searchResultsModelMapper.map(personList, teamList);
        assertThat(result.persons, containsExhaustivelyInOrder(
            aPersonSearchResult().named("fred smith").withPicture("pic1.img").inTeam("a team").withEmail("email1@testemail.com"),
            aPersonSearchResult().named("fred bailey").withPicture("pic3.img").inTeam("a team").withEmail("email2@testemail.com")
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

        MockPerson teamless = new MockPerson(newPersonId("1"), "teamless", "teamless.img", team, "email@testemail.com");

        List teamlessList = new ArrayList<>();
        List<Team> emptyTeamList = new ArrayList<>();

        teamlessList.add(teamless);

        DefaultSearchResultsModel result = searchResultsModelMapper.map(teamlessList, emptyTeamList);
        assertThat(result.persons, containsExhaustivelyInOrder(
            aPersonSearchResult().inTeam("")
        ));

    }
}
