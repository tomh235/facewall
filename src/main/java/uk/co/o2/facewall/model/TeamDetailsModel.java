package uk.co.o2.facewall.model;

public class TeamDetailsModel implements SearchResultsModel {
    public String name;
    public String colour;
    public int size;

    public TeamDetailsModel(String name, String colour, int size) {
        this.name = name;
        this.colour = colour;
        this.size = size;
    }
}
