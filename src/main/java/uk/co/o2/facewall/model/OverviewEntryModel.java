package uk.co.o2.facewall.model;

public class OverviewEntryModel {
    public String teamHeader;
    public String name;
    public String picture;
    public String colour;
    public String link;

    public OverviewEntryModel(String teamHeader, String name, String picture, String colour, String link) {
        this.teamHeader = teamHeader;
        this.name = name;
        this.picture = picture;
        this.colour = colour;
        this.link = link;
    }
}