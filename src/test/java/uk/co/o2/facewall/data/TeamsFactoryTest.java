package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.dao.AdminDAO;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.TeamDTO;
import uk.co.o2.facewall.data.dto.TeamInformation;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.data.datatype.TeamId.newTeamId;
import static uk.co.o2.facewall.data.dto.PersonInformation.newPersonInformation;
import static uk.co.o2.facewall.data.dto.PersonInformation.noPersonInformation;
import static uk.co.o2.facewall.data.dto.TeamInformation.newTeamInformation;
import static uk.co.o2.facewall.data.dto.TeamInformation.noTeamInformation;
import static uk.co.o2.facewall.domain.PersonMatcher.aPerson;
import static uk.co.o2.facewall.domain.TeamMatcher.aTeam;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static uk.co.o2.facewall.util.IterableMatchers.contains;
import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInAnyOrder;

@RunWith(MockitoJUnitRunner.class)
public class TeamsFactoryTest {
    private static final List<PersonInformation> somePersonInformations = asList(noPersonInformation());
    private static final TeamInformation someTeamInformation = noTeamInformation();

    @Mock
    private AdminDAO mockAdminDao;

    @InjectMocks
    private TeamsFactory teamsFactory;

    @Test
    public void create_team_names_from_dtos() throws Exception {
        Iterable<TeamDTO> dtos = asList(
                new TeamDTO(newTeamInformation()
                        .named("arsenal").build(),
                        somePersonInformations
                ),
                new TeamDTO(newTeamInformation()
                        .named("man u").build(),
                        somePersonInformations
                )
        );

        List<Team> result = teamsFactory.createTeams(dtos);

        assertThat(result, containsExhaustivelyInAnyOrder(
            aTeam().named("arsenal"),
            aTeam().named("man u")
        ));
    }

    @Test
    public void create_team_from_dto() throws Exception {
        TeamDTO dto = new TeamDTO(newTeamInformation()
                        .named("arsenal").build(),
                somePersonInformations
                );

        Team result = teamsFactory.createTeam(dto);

        assertThat(result, is(
                aTeam().named("arsenal")
        ));
    }

    @Test
    public void create_team_colour_from_dtos() throws Exception {
        Iterable<TeamDTO> dtos = asList(
                new TeamDTO(newTeamInformation()
                        .coloured("blue").build(),
                        somePersonInformations
                ),
                new TeamDTO(newTeamInformation()
                        .coloured("red").build(),
                        somePersonInformations
                )
        );

        List<Team> result = teamsFactory.createTeams(dtos);

        assertThat(result, containsExhaustivelyInAnyOrder(
            aTeam().withColour("blue"),
            aTeam().withColour("red")
        ));
    }

    @Test
    public void create_team_members_from_dtos() throws Exception {
        Iterable<TeamDTO> dtos = asList(
                new TeamDTO(someTeamInformation,
                        asList(
                                newPersonInformation().named("John").build(),
                                newPersonInformation().named("Jim").build()
                        )
                )
        );

        List<Team> result = teamsFactory.createTeams(dtos);

        assertThat(result, contains(
            aTeam().whereMembers(containsExhaustivelyInAnyOrder(
                    aPerson().named("John"),
                    aPerson().named("Jim")
            ))
        ));
    }

    @Test
    public void create_team_members_from_dto() throws Exception {
        TeamDTO dto = new TeamDTO(someTeamInformation,
                        asList(
                                newPersonInformation().named("John").build(),
                                newPersonInformation().named("Jim").build()
                        )
        );

        Team result = teamsFactory.createTeam(dto);

        assertThat(result, is(
                aTeam().whereMembers(containsExhaustivelyInAnyOrder(
                        aPerson().named("John"),
                        aPerson().named("Jim")
                ))
        ));
    }

    @Test
    public void add_member_to_created_team_delegates_to_dao() throws Exception {
        TeamDTO dto = new TeamDTO(newTeamInformation().withId("team de hugo et charlie").build(),
                somePersonInformations
        );

        Team team = teamsFactory.createTeam(dto);
        Person member = mock(Person.class);
        when(member.getId()).thenReturn(newPersonId("les autres"));

        team.addMember(member);

        verify(mockAdminDao).addPersonToTeam(
                newPersonId("les autres"),
                newTeamId("team de hugo et charlie")
        );
    }

    @Test
    public void add_member_to_created_team_adds_member_to_members_list() throws Exception {
        TeamDTO dto = new TeamDTO(newTeamInformation().named("french team").build(),
                asList(
                        newPersonInformation().named("Hugo").build(),
                        newPersonInformation().named("Charlie").build()
                )
        );

        Team team = teamsFactory.createTeam(dto);
        Person member = mock(Person.class);
        when(member.name()).thenReturn("member-name");

        team.addMember(member);

        assertThat(team, is(aTeam().whereMembers(
            containsExhaustivelyInAnyOrder(
                    aPerson()
                        .named("member-name"),
                    aPerson()
                        .named("Hugo"),
                    aPerson()
                        .named("Charlie")
            )
        )));
    }
}
