package uk.co.o2.facewall.facade;

import uk.co.o2.facewall.data.PersonRepository;
import uk.co.o2.facewall.domain.PersonStub;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.facade.modelmapper.OverviewModelMapper;
import uk.co.o2.facewall.model.OverviewEntryModelMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.o2.facewall.model.OverviewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.domain.NoTeam.noTeam;
import static uk.co.o2.facewall.model.OverviewEntryModelMatcher.anOverviewEntryModel;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInOrder;

@RunWith(MockitoJUnitRunner.class)
public class OverviewFacadeTest {
    @Mock PersonRepository mockRepo;
    private OverviewFacade overviewFacade;

    @Before
    public void setUp() throws Exception {
        overviewFacade = new OverviewFacade(mockRepo, new OverviewModelMapper());
    }

    @Test
    public void map_repo_to_domain_objects_to_overview_model_test() {
        PersonStub ecom_member1 = new PersonStub(newPersonId("3"), "ecom_member1", "pic1.img", "BA", null);
        PersonStub ecom_member2 = new PersonStub(newPersonId("4"), "ecom_member2", "pic2.img", "BA", null);
        PersonStub pr_member    = new PersonStub(newPersonId("5"), "pr_member", "pic3.img", "BA", null);

        Team ecom = new StubbedTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1, ecom_member2)));
        Team productResources = new StubbedTeam("productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        pr_member.setTeam(productResources);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(ecom_member1, ecom_member2, pr_member));
        when(mockRepo.listPersons()).thenReturn(persons);

        OverviewEntryModelMatcher ecom1Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member1");
        OverviewEntryModelMatcher ecom2Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member2");
        OverviewEntryModelMatcher productResourcesMatcher = anOverviewEntryModel().withTeamHeader("productResources").named("pr_member");

        OverviewModel result = overviewFacade.createOverviewModel();
        assertThat(result.entries, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, productResourcesMatcher));

        verify(mockRepo).listPersons();
    }

    @Test
    public void orders_overview_alphabetically_by_name_when_same_team(){
        PersonStub ecom_member1 = new PersonStub(newPersonId("3"), "bob", "pic1.img", "BA", null);
        PersonStub ecom_member2 = new PersonStub(newPersonId("4"), "dave", "pic2.img", "BA", null);
        PersonStub ecom_member3 = new PersonStub(newPersonId("4"), "dave2", "pic2.img", "BA", null);
        PersonStub ecom_member4 = new PersonStub(newPersonId("7"), "rick", "pic5.img", "BA", null);

        Team ecom = new StubbedTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        ecom_member3.setTeam(ecom);
        ecom_member4.setTeam(ecom);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(ecom_member3, ecom_member2, ecom_member1, ecom_member4));
        when(mockRepo.listPersons()).thenReturn(persons);

        OverviewEntryModelMatcher ecom1Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("bob");
        OverviewEntryModelMatcher ecom2Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("dave");
        OverviewEntryModelMatcher ecom3Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("dave2");
        OverviewEntryModelMatcher ecom4Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("rick");

        OverviewModel result = overviewFacade.createOverviewModel();
        assertThat(result.entries, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, ecom3Matcher, ecom4Matcher));

        verify(mockRepo).listPersons();

    }

    @Test
    public void orders_overviews_alphabetically_by_team_test() {
        PersonStub ecom_member1 = new PersonStub(newPersonId("3"), "ecom_member1", "pic1.img", "BA", null);
        PersonStub ecom_member2 = new PersonStub(newPersonId("7"), "ecom_member2", "pic5.img", "BA", null);
        PersonStub pr_member = new PersonStub(newPersonId("4"), "pr_member", "pic2.img", "BA", null);

        Team ecom = new StubbedTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));
        Team productResources = new StubbedTeam("productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        pr_member.setTeam(productResources);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(ecom_member2, pr_member, ecom_member1));
        when(mockRepo.listPersons()).thenReturn(persons);

        OverviewEntryModelMatcher ecom1Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member1");
        OverviewEntryModelMatcher ecom2Matcher = anOverviewEntryModel().withTeamHeader("ecom").named("ecom_member2");
        OverviewEntryModelMatcher productResourcesMatcher = anOverviewEntryModel().withTeamHeader("productResources").named("pr_member");

        OverviewModel result = overviewFacade.createOverviewModel();
        assertThat(result.entries, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, productResourcesMatcher));

        verify(mockRepo).listPersons();
    }

    @Test
    public void orders_overview_alphabetically_by_team_with_teamless_last_test() {
        PersonStub ecom_member1 = new PersonStub(newPersonId("3"), "ecom_member1", "pic1.img", "BA", null);
        PersonStub ecom_member2 = new PersonStub(newPersonId("7"), "ecom_member2", "pic5.img", "BA", null);
        PersonStub pr_member = new PersonStub(newPersonId("4"), "pr_member", "pic2.img", "BA", null);
        PersonStub teamless_member1 = new PersonStub(newPersonId("5"), "teamless_member1", "pic3.img", "BA", null);
        PersonStub teamless_member2 = new PersonStub(newPersonId("6"), "teamless_member2", "pic4.img", "BA", null);

        Team ecom = new StubbedTeam("ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));
        Team productResources = new StubbedTeam("productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));

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

        OverviewModel result = overviewFacade.createOverviewModel();
        assertThat(result.entries, containsExhaustivelyInOrder(ecom1Matcher, ecom2Matcher, productResourcesMatcher, teamless1Matcher, teamless2Matcher));

        verify(mockRepo).listPersons();
    }
}
