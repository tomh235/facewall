package uk.co.o2.facewall.model;

import java.util.List;

public class TeamDetailsWithPersonsModel {
    public String name;
    public String colour;
    public int size;
    public List<OverviewEntryModel> entries;

    public TeamDetailsWithPersonsModel(String name, String colour, int size, List<OverviewEntryModel> entries) {
        this.name = name;
        this.colour = colour;
        this.size = size;
        this.entries = entries;
    }

}
