package domain;

import database.CreateUser;

public class CreateUserService {

    CreateUser createUser = new CreateUser();

    public CreateUserService() {

    }

    public void createUser(String username, String password, String email, String role) {
        createUser.createUserAccount(username, password, email, role);
    }

    //Den her klasse henter data fra GUI og sender til databasen når der skal oprettes en bruger.

}
