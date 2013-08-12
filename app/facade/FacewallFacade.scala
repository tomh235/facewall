package facade

import model.{TeamOverview, PersonOverview}
import domain.Team

class FacewallFacade() {
    def mapTeamOverviewModel(teams: List[Team]): List[TeamOverview] = {
        teams.map { team =>
            val memberOverviews = team.getMembers.map { member => PersonOverview(member.name, member.picture)}
            TeamOverview(memberOverviews)
        }
    }
}
