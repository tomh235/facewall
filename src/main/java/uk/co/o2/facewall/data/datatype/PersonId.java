package uk.co.o2.facewall.data.datatype;

import uk.co.o2.facewall.util.AbstractWrappingDataType;

public class PersonId extends AbstractWrappingDataType<String> {
    private PersonId(String value) {
        super(value);
    }

    public static PersonId newPersonId(String id) {
        return id == null
            ? noPersonId()
            : new PersonId(id);
    }

    public static PersonId noPersonId() {
        return new PersonId("");
    }
}
