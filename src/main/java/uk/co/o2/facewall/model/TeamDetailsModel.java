package uk.co.o2.facewall.model;

public class TeamDetailsModel implements SearchResultsModel {
    public String name;
    public String colour;

    public TeamDetailsModel(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }
}
