package data.dao.database.query;

import util.AbstractWrappingDataType;

public class PersonNodeKey extends AbstractWrappingDataType<String> {

    protected PersonNodeKey(String value) {
        super(value);
    }

    static public PersonNodeKey newPersonNodeKey(String value) {
        return new PersonNodeKey(value);
    }
}
