package domain;

import database.CreateUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateUserService {

    CreateUser createUser;
    Encrypt encrypt = new Encrypt();

    public CreateUserService() {
        this.createUser=new CreateUser();

    }

    public void createUser(String username, String password, String email, String role) {
        createUser.createUserAccount(username, password, email, role);
    }

    //Den her klasse henter data fra GUI og sender til databasen når der skal oprettes en bruger.

    public ObservableList getInfoUser(){
        ObservableList<BatchReport> obList;
        obList = FXCollections.observableArrayList(createUser.getUserInfo());
        return obList;

    }

    public void updateUser(int userID, String username, String password, String email, String role) {
        createUser.updateUser(userID, username, encrypt.encrypt(password), email, role);
    }
    public void deleteUserinDM(int userID){
       createUser=new CreateUser();
        createUser.deleteUserinDB(userID);
    }
}
