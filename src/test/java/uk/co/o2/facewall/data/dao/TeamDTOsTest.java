package uk.co.o2.facewall.data.dao;

import org.junit.Before;
import org.junit.Test;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.TeamInformation;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.o2.facewall.data.dto.PersonInformation.newPersonInformation;
import static uk.co.o2.facewall.data.dto.PersonInformationMatcher.aPersonInformation;
import static uk.co.o2.facewall.data.dto.TeamInformation.newTeamInformation;
import static uk.co.o2.facewall.util.IterableMatchers.contains;
import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInAnyOrder;

public class TeamDTOsTest {
    private TeamDTOs dtos;
    private PersonInformation firstPersonInformation;
    private PersonInformation secondPersonInformation;
    private TeamInformation aTeamInformation;
    private static final String PERSON1_ID = "12345";
    private static final String PERSON2_ID = "678910";
    private static final String TEAM_ID = "23456";
    private static final String TEAM_NAME = "Farmers";
    private static final String TEAM_COLOUR = "Green";


    @Before
    public void setup_each() {
        dtos = new TeamDTOs();
        firstPersonInformation = newPersonInformation().withId(PERSON1_ID).build();
        secondPersonInformation = newPersonInformation().withId(PERSON2_ID).build();
        aTeamInformation = newTeamInformation().named(TEAM_NAME).coloured(TEAM_COLOUR).withId(TEAM_ID).build();
    }

    @Test
    public void add_member_to_team_adds_member_to_existing_team() throws Exception {
        dtos.addMemberToTeam(aTeamInformation, firstPersonInformation);
        List<PersonInformation> result = dtos.iterator().next().memberInformation;
        assertThat(result, containsExhaustivelyInAnyOrder(
                aPersonInformation().withId(PERSON1_ID)));
    }

    @Test
    public void add_member_to_team_adds_team_and_member_for_new_team() throws Exception {
        dtos.addMemberToTeam(aTeamInformation, firstPersonInformation);
        dtos.addMemberToTeam(aTeamInformation, secondPersonInformation);
        List<PersonInformation> result = dtos.iterator().next().memberInformation;
        assertThat(result, contains(
                aPersonInformation().withId(PERSON2_ID)));
    }
}
