package uk.co.o2.facewall.data.dao.database.query;

import uk.co.o2.facewall.util.AbstractWrappingDataType;

public class PersonNodeKey extends AbstractWrappingDataType<String> {

    protected PersonNodeKey(String value) {
        super(value);
    }

    static public PersonNodeKey newPersonNodeKey(String value) {
        return new PersonNodeKey(value);
    }
}
