package model;

import java.util.List;

public class DefaultSearchResultsModel implements SearchResultsModel {
    public final List<PersonSearchResult> persons;
    public final List<TeamSearchResult> teams;

    public DefaultSearchResultsModel(List<PersonSearchResult> persons, List<TeamSearchResult> teams) {
        this.persons = persons;
        this.teams = teams;
    }
}