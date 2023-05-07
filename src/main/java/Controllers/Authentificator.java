package Controllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Databases.UsersDB;
import Models.UserInfo;

public interface Authentificator {

    public UserInfo signUp(String username, String password, UsersDB usersDB) throws UniqueUsernameViolationException,
            UserNameNotProvidedException, PasswordNotProvidedException;
    public UserInfo login(String username, String password, UsersDB usersDB) throws UserDoesNotExistException,
            WrongPasswordException, UserNameNotProvidedException, PasswordNotProvidedException;
}
