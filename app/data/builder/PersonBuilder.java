package data.builder;

import domain.Person;

public interface PersonBuilder {

    public PersonBuilder withId(String id);
    public PersonBuilder named(String name);
    public PersonBuilder whosePictureIs(String pictureLocation);

    public Person build();
}
