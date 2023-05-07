package Controllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Databases.UsersDB;
import Models.UserInfo;

public class SimpleAuthentificator implements Authentificator {
    //TODO: check the exceptions handling if we're going to do it here or in the controller

    @Override
    public UserInfo signUp(String username, String password, UsersDB usersDB) throws UniqueUsernameViolationException, UserNameNotProvidedException, PasswordNotProvidedException {

        if (username.equals("")) throw new UserNameNotProvidedException();
        if (password.equals("")) throw new PasswordNotProvidedException();
        //TODO: let the database create the UserInfo object, not the authenticator! and inside addUser, create a calendar instance using the Calendar manager
        return usersDB.addUser(new UserInfo(username, password));
    }

    @Override
    public UserInfo login(String username, String password, UsersDB usersDB) throws UserDoesNotExistException,
            WrongPasswordException, UserNameNotProvidedException, PasswordNotProvidedException {

        if (username.equals("")) throw new UserNameNotProvidedException();
        if (password.equals("")) throw new PasswordNotProvidedException();
        if (!usersDB.users.containsKey(username)) throw new UserDoesNotExistException();
        if (!usersDB.users.get(username).getPassword().equals(password)) throw new WrongPasswordException();
        return usersDB.users.get(username);

    }
}
