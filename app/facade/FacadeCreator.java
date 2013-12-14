package facade;

import data.AdminRepository;
import data.Repository;
import data.dao.AdminDAO;
import data.dao.database.AdminDB;
import data.mapper.PersonNodeMapper;
import facade.validators.TeamValidator;
import org.neo4j.rest.graphdb.GraphDatabaseFactory;
import requestmapper.PersonMapper;
import requestmapper.QueryMapper;

public class FacadeCreator {
    public static SignUpFacade createSignUpFacade(Repository repository) {
        QueryMapper queryMapper = new QueryMapper();
        PersonMapper personMapper = new PersonMapper();
        TeamValidator teamValidator = new TeamValidator(repository, queryMapper);
        AdminRepository adminRepository = new AdminRepository(new AdminDAO(new AdminDB(
            GraphDatabaseFactory.databaseFor("http://localhost:7474/db/data/")),new PersonNodeMapper()
        ));

        return new SignUpFacade(adminRepository, repository, personMapper, queryMapper, teamValidator);
    }
}
