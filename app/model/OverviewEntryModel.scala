package model

case class OverviewEntryModel(teamHeader: String, name: String, picture: String, colour: String){
/*    override def equals(that: Any) : Boolean = {
        that match {
          case over : OverviewEntryModel =>
              this.name == over.name &&
              this.teamHeader == over.teamHeader &&
              this.picture == over.teamHeader &&
              this.colour == over.colour
          case _ => false
        }
  }
  override def hashCode : Int = {
      com.google.common.base.Objects.hashCode(
          this.teamHeader, this.name, this.picture, this.colour
      )
  }*/
  }