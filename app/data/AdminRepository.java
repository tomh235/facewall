package data;

import data.dao.AdminDAO;
import domain.Person;

public class AdminRepository {

    private final AdminDAO dao;

    public AdminRepository(AdminDAO dao) {
        this.dao = dao;
    }

    public void addPerson(Person person) {
        dao.addPerson(person);
    }
}
