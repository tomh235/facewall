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
        MockPerson ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img", null);
        MockPerson ecom_member2 = new MockPerson("7", "ecom_member2", "pic5.img", null);
        MockPerson pr_member = new MockPerson("4", "pr_member", "pic2.img", null);
        MockPerson teamless_member1 = new MockPerson("5", "teamless_member1", "pic3.img", null);
        MockPerson teamless_member2 = new MockPerson("6", "teamless_member2", "pic4.img", null);

        Team ecom = new MockTeam("1", "ecom", "blue", new ArrayList<Person>(Arrays.asList(ecom_member1)));
        Team productResources = new MockTeam("2", "productResources", "green", new ArrayList<Person>(Arrays.asList(pr_member)));
        Team noTeam = new MockTeam("3", "", "grey", new ArrayList<Person>(Arrays.asList(teamless_member1)));

        ecom_member1.setTeam(ecom);
        ecom_member2.setTeam(ecom);
        pr_member.setTeam(productResources);
        teamless_member1.setTeam(noTeam);
        teamless_member2.setTeam(noTeam);

        List<Person> persons = new ArrayList<Person>(Arrays.asList(teamless_member1, ecom_member2, teamless_member2, pr_member, ecom_member1));
        when(mockRepo.listPersons()).thenReturn(persons);

        List<OverviewEntryModel> expectedResult = new ArrayList<>(Arrays.asList(
                new OverviewEntryModel("ecom",              "ecom_member1",     "pic1.img", "blue"),
                new OverviewEntryModel("ecom",              "ecom_member2",     "pic5.img", "blue"),
                new OverviewEntryModel("productResources",  "pr_member",        "pic2.img", "green"),
                new OverviewEntryModel("",                  "teamless_member1", "pic3.img", "grey"),
                new OverviewEntryModel("",                  "teamless_member2", "pic4.img", "grey")
        ));

        List<OverviewEntryModel> result = facewallFacade.createOverviewModel();
        assertEquals("expected: \n" + expectedResult + "\ngot: \n" + result + ".", expectedResult, result);

        verify(mockRepo).listPersons();
    }
}
