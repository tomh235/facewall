package model;

public class OverviewEntryModel {
    public final String teamHeader;
    public final String name;
    public final String picture;
    public final String colour;
    public final String link;

    public OverviewEntryModel(String teamHeader, String name, String picture, String colour, String link) {
        this.teamHeader = teamHeader;
        this.name = name;
        this.picture = picture;
        this.colour = colour;
        this.link = link;
    }
}