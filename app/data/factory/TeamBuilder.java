package data.factory;

import domain.Team;

public interface TeamBuilder {

    public TeamBuilder named(String name);
    public TeamBuilder coloured(String colour);

    public Team build();
}
