package data.builder;

import domain.Team;

public interface TeamBuilder {

    public TeamBuilder withId(String id);
    public TeamBuilder named(String name);
    public TeamBuilder coloured(String colour);

    public Team build();
}
