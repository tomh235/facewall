package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.dao.AdminDAO;
import uk.co.o2.facewall.data.dao.database.ItemNotFoundException;
import uk.co.o2.facewall.data.datatype.TeamId;
import uk.co.o2.facewall.data.dto.TeamInformation;
import uk.co.o2.facewall.domain.Person;

import java.util.List;

class MutableTeam extends AbstractTeam {

    private final AdminDAO adminDAO;

    private List<Person> members;

    public MutableTeam(AdminDAO adminDAO, TeamInformation teamInformation) {
        super(teamInformation);
        this.adminDAO = adminDAO;
    }

    final public TeamId getId() {
        return teamInformation.getId();
    }

    @Override public List<Person> members() {
        return members;
    }

    //FixMe: This method is guaranteed to leave the added member in an invalid state.
    @Override
    public void addMember(Person member) {
        try {
            adminDAO.addPersonToTeam(member.getId(), getId());
            members.add(member);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException("Could not find team or person in database", e);
        }
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }
}
