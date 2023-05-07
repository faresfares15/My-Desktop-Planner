package Controllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Databases.UsersDB;
import Models.UserSchema;

public class GoogleAuthentificator implements Authentificator {

    //TODO: implement the interface methods

    @Override
    public UserSchema signUp(String username, String password, UsersDB usersDB) throws UniqueUsernameViolationException,
            UserNameNotProvidedException, PasswordNotProvidedException {
        return null;
    }

    @Override
    public UserSchema login(String username, String password, UsersDB usersDB) throws UserDoesNotExistException,
            WrongPasswordException {
        return null;
    }
}
