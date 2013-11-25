package data.factory;

import domain.Person;

public abstract class PersonBuilder {

    protected String name;
    protected String picture;

    public final PersonBuilder named(String name) {
        return null;
    }
    public final PersonBuilder whosePictureIs(String pictureLocation) {
        return null;
    }

    public abstract Person build();
}
