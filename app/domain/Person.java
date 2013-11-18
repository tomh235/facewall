package domain;

public interface Person {
    // The id shouldn't be exposed, it's purely a database concern
    String id();

    String name();
    String picture();
    Team team();
}
