package facade;

import data.Repository;
import domain.MockPerson;
import domain.MockTeam;
import domain.Person;
import domain.Team;
import facade.modelmapper.OverviewModelMapper;
import model.OverviewEntryModel;
import model.OverviewEntryModelMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static domain.NoTeam.noTeam;
import static model.OverviewEntryModelMatcher.anOverviewEntryModel;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static util.IterableMatchers.containsExhaustivelyInOrder;

@RunWith(MockitoJUnitRunner.class)
public class OverviewFacadeTest {
    @Mock Repository mockRepo = mock(Repository.class);
    private OverviewFacade overviewFacade;

    @Before
    public void setUp() throws Exception {
        overviewFacade = new OverviewFacade(mockRepo, new OverviewModelMapper());
    }

    @Test
    public void map_repo_to_domain_objects_to_overview_model_test() {
        MockPerson ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img", null);
        MockPerson ecom_member2 = new MockPerson("4", "ecom_member2", "pic2.img", null);
        MockPerson pr_member    = new MockPerson("5", "pr_member", "pic3.img", null);

        Team ecom = new MockTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1, ecom_member2)));
        Team productResources = new MockTeam("productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        pr_member.setTeam(productResources);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(ecom_member1, ecom_member2, pr_member));
        when(mockRepo.listPersons()).thenReturn(persons);

        OverviewEntryModelMatcher ecom1Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member1");
        OverviewEntryModelMatcher ecom2Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member2");
        OverviewEntryModelMatcher productResourcesMatcher = anOverviewEntryModel().withTeamHeader("productResources").named("pr_member");

        List<OverviewEntryModel> result = overviewFacade.createOverviewModel();
        assertThat(result, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, productResourcesMatcher));

        verify(mockRepo).listPersons();
    }

    @Test
    public void orders_overview_alphabetically_by_name_when_same_team(){
        MockPerson ecom_member1 = new MockPerson("3", "bob", "pic1.img", null);
        MockPerson ecom_member2 = new MockPerson("4", "dave", "pic2.img", null);
        MockPerson ecom_member3 = new MockPerson("4", "dave2", "pic2.img", null);
        MockPerson ecom_member4 = new MockPerson("7", "rick", "pic5.img", null);

        Team ecom = new MockTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        ecom_member3.setTeam(ecom);
        ecom_member4.setTeam(ecom);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(ecom_member3,ecom_member2,ecom_member1,ecom_member4));
        when(mockRepo.listPersons()).thenReturn(persons);

        OverviewEntryModelMatcher ecom1Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("bob");
        OverviewEntryModelMatcher ecom2Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("dave");
        OverviewEntryModelMatcher ecom3Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("dave2");
        OverviewEntryModelMatcher ecom4Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("rick");

        List<OverviewEntryModel> result = overviewFacade.createOverviewModel();
        assertThat(result, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, ecom3Matcher, ecom4Matcher));

        verify(mockRepo).listPersons();

    }

    @Test
    public void orders_overviews_alphabetically_by_team_test() {
        MockPerson ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img", null);
        MockPerson ecom_member2 = new MockPerson("7", "ecom_member2", "pic5.img", null);
        MockPerson pr_member = new MockPerson("4", "pr_member", "pic2.img", null);

        Team ecom = new MockTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));
        Team productResources = new MockTeam("productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        pr_member.setTeam(productResources);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(ecom_member2, pr_member, ecom_member1));
        when(mockRepo.listPersons()).thenReturn(persons);

        OverviewEntryModelMatcher ecom1Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member1");
        OverviewEntryModelMatcher ecom2Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member2");
        OverviewEntryModelMatcher productResourcesMatcher = anOverviewEntryModel().withTeamHeader("productResources").named("pr_member");

        List<OverviewEntryModel> result = overviewFacade.createOverviewModel();
        assertThat(result, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, productResourcesMatcher));

        verify(mockRepo).listPersons();
    }

    @Test
    public void orders_overview_alphabetically_by_team_with_teamless_last_test() {
        MockPerson ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img", null);
        MockPerson ecom_member2 = new MockPerson("7", "ecom_member2", "pic5.img", null);
        MockPerson pr_member = new MockPerson("4", "pr_member", "pic2.img", null);
        MockPerson teamless_member1 = new MockPerson("5", "teamless_member1", "pic3.img", null);
        MockPerson teamless_member2 = new MockPerson("6", "teamless_member2", "pic4.img", null);

        Team ecom = new MockTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));
        Team productResources = new MockTeam("productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        pr_member.setTeam(productResources);
        teamless_member1.setTeam(noTeam());
        teamless_member2.setTeam(noTeam());

        List<Person> persons = new ArrayList<Person>(Arrays.asList(teamless_member1, ecom_member1, teamless_member2, ecom_member2, pr_member));
        when(mockRepo.listPersons()).thenReturn(persons);

        OverviewEntryModelMatcher ecom1Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member1");
        OverviewEntryModelMatcher ecom2Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member2");
        OverviewEntryModelMatcher productResourcesMatcher = anOverviewEntryModel().withTeamHeader("productResources").named("pr_member");
        OverviewEntryModelMatcher teamless1Matcher = anOverviewEntryModel().withTeamHeader("").named("teamless_member1");
        OverviewEntryModelMatcher teamless2Matcher = anOverviewEntryModel().withTeamHeader("").named("teamless_member2");

        List<OverviewEntryModel> result = overviewFacade.createOverviewModel();
        assertThat(result, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, productResourcesMatcher, teamless1Matcher, teamless2Matcher));

        verify(mockRepo).listPersons();
    }
}
