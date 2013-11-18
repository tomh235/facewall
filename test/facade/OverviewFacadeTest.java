package facade;

import domain.MockPerson;
import domain.MockTeam;
import domain.Person;
import domain.Team;
import model.OverviewEntryModel;
import org.junit.Test;
import data.ScalaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OverviewFacadeTest {
    ScalaRepository mockRepo = mock(ScalaRepository.class);
    OverviewFacade facewallFacade = new OverviewFacade(mockRepo);

    @Test
    public void mapRepoToDomainObjectsToOverviewModelTest() {
        MockPerson ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img", null);
        MockPerson ecom_member2 = new MockPerson("4", "ecom_member2", "pic2.img", null);
        MockPerson pr_member    = new MockPerson("5", "pr_member", "pic3.img", null);

        Team ecom = new MockTeam("1", "ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1, ecom_member2)));
        Team productResources = new MockTeam("2", "productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        pr_member.setTeam(productResources);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(ecom_member1, ecom_member2, pr_member));
        when(mockRepo.listPersons()).thenReturn(persons);

        List<OverviewEntryModel> expectedResult = new ArrayList<>(Arrays.asList(
                new OverviewEntryModel("ecom", "ecom_member1", "pic1.img", "blue"),
                new OverviewEntryModel("ecom", "ecom_member2", "pic2.img", "blue"),
                new OverviewEntryModel("productResources", "pr_member", "pic3.img", "green")
        ));

        List<OverviewEntryModel> result = facewallFacade.createOverviewModel();
        assertEquals("expected: " + expectedResult + "\ngot: " + result + ".", expectedResult, result);

        verify(mockRepo).listPersons();
    }

    @Test
    public void orderOverviewsAlphabeticallyByTeamWithTeamlessLastTest() {
        MockPerson ecom_member = new MockPerson("3", "ecom_member", "pic1.img", null);
        MockPerson pr_member = new MockPerson("4", "pr_member", "pic2.img", null);
        MockPerson teamless_member = new MockPerson("5", "teamless_member", "pic3.img", null);

        Team ecom = new MockTeam("1", "ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member)));
        Team productResources = new MockTeam("2", "productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));
        Team noTeam = new MockTeam("3", "", "grey", new ArrayList<Person>(Arrays.asList(teamless_member)));

        ecom_member.setTeam(ecom);
        pr_member.setTeam(productResources);
        teamless_member.setTeam(noTeam);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(teamless_member, pr_member, ecom_member));
        when(mockRepo.listPersons()).thenReturn(persons);

        List<OverviewEntryModel> expectedResult = new ArrayList<>(Arrays.asList(
                new OverviewEntryModel("ecom", "ecom_member", "pic1.img", "blue"),
                new OverviewEntryModel("productResources", "pr_member", "pic2.img", "green"),
                new OverviewEntryModel("", "teamless_member", "pic3.img", "grey")
        ));

        List<OverviewEntryModel> result = facewallFacade.createOverviewModel();
        assertEquals("expected: " + expectedResult + "\ngot: " + result + ".", expectedResult, result);

        verify(mockRepo).listPersons();
    }
}
