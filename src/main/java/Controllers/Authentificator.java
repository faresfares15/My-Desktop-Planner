package Controllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Databases.UsersDB;
import Models.UserSchema;

public interface Authentificator {

    public UserSchema signUp(String username, String password, UsersDB usersDB) throws UniqueUsernameViolationException,
            UserNameNotProvidedException, PasswordNotProvidedException;
    public UserSchema login(String username, String password, UsersDB usersDB) throws UserDoesNotExistException,
            WrongPasswordException, UserNameNotProvidedException, PasswordNotProvidedException;
}
