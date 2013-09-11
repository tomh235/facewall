package domain

class MockPerson (val id: String, val name: String, val picture: String, private var aTeam: Option[Team] = None) extends Person {
    def team: Option[Team] = aTeam
    def setTeam(team: Team) { aTeam = Some(team) }
}
