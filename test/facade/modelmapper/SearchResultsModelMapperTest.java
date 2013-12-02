package facade.modelmapper;

import domain.MockPerson;
import domain.MockTeam;
import domain.Person;
import domain.Team;
import model.DefaultSearchResultsModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static model.PersonSearchResultMatcher.aPersonSearchResult;
import static model.TeamSearchResultMatcher.aTeamSearchResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static util.CollectionMatcher.contains;

public class SearchResultsModelMapperTest {

    SearchResultsModelMapper searchResultsModelMapper = new SearchResultsModelMapper();

    @Test
    public void should_map_persons_into_person_search_results(){

        Team team = mock(Team.class);
        when(team.name()).thenReturn("a team");

        MockPerson fred1 = new MockPerson("1", "fred smith", "pic1.img", team);
        MockPerson fred2 = new MockPerson("2", "fred bailey", "pic3.img", team);

        List<Person> personList = new ArrayList<>();
        personList.add(fred1);
        personList.add(fred2);
        List<Team> teamList = new ArrayList<>();

        DefaultSearchResultsModel result = searchResultsModelMapper.map(personList, teamList);
        assertThat(result.persons, contains(
                aPersonSearchResult().named("fred smith").withPicture("pic1.img").inTeam("a team"),
                aPersonSearchResult().named("fred bailey").withPicture("pic3.img").inTeam("a team")
        ));
    }

    @Test

    public void should_map_teams_into_team_search_results() {

        List<Person> list1 = new ArrayList<>();
        List<Person> list2 = new ArrayList<>();

        MockTeam ecom = new MockTeam("1", "Ecom", "blue", list1);
        MockTeam anotherTeam = new MockTeam("2", "another team", "red", list2);

        List<Person> list3 = new ArrayList<>();
        List list4 = new ArrayList<>();

        list4.add(ecom);
        list4.add(anotherTeam);

        DefaultSearchResultsModel result = searchResultsModelMapper.map(list3, list4);
        assertThat(result.teams, contains(
                aTeamSearchResult().named("Ecom"),
                aTeamSearchResult().named("another team")
        ));
    }

    @Test
    public void should_map_persons_team_name_as_empty_string_if_person_is_not_in_a_team() {
        Team team = mock(Team.class);
        when(team.name()).thenReturn("");

        MockPerson teamless = new MockPerson("1", "teamless", "teamless.img", team);

        List teamlessList = new ArrayList<>();
        List<Team> emptyTeamList = new ArrayList<>();

        teamlessList.add(teamless);

        DefaultSearchResultsModel result = searchResultsModelMapper.map(teamlessList, emptyTeamList);
        assertThat(result.persons, contains(
                aPersonSearchResult().inTeam("")
        ));

    }
}
