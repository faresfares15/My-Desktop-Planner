package Controllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Databases.UsersDB;
import Models.UserInfo;

public class GoogleAuthentificator implements Authentificator {

    //TODO: implement the interface methods

    @Override
    public UserInfo signUp(String username, String password, UsersDB usersDB) throws UniqueUsernameViolationException,
            UserNameNotProvidedException, PasswordNotProvidedException {
        return null;
    }

    @Override
    public UserInfo login(String username, String password, UsersDB usersDB) throws UserDoesNotExistException,
            WrongPasswordException {
        return null;
    }
}
