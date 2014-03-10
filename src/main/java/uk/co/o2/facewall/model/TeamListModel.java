package uk.co.o2.facewall.model;

import java.util.List;

public class TeamListModel {

    public List<TeamDetailsModel> entries;

    public TeamListModel(List<TeamDetailsModel> entries) {
        this.entries = entries;
    }
}
