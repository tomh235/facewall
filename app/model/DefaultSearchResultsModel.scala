package model

case class DefaultSearchResultsModel(persons: List[PersonSearchResult], teams: List[TeamSearchResult]) extends SearchResultsModel
