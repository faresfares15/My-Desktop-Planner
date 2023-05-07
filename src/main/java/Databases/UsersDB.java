package Databases;

import Models.UserInfo;

import java.util.TreeMap;

public class UsersDB {
    TreeMap<String, UserInfo> users = new TreeMap<>();
    //TODO: check if the boolean type return is necessary or if we can just throw an exception
    public UserInfo addUser(UserInfo userInfo) throws UniqueUsernameViolationException {
        //the UserController class will create a UserInfo object and pass it to this method
        if (users.containsKey(userInfo.getUsername())) throw new UniqueUsernameViolationException();
        return users.put(userInfo.getUsername(), userInfo);
    }
    public UserInfo updateUser(UserInfo userInfo) throws UniqueUsernameViolationException{
        //TODO: check if we son't need other excpetions in case the userInfo object has null or empty values
        //No need to check if the users exists or nah because only the currentUser can update his own info
        if (users.containsKey(userInfo.getUsername())) throw new UniqueUsernameViolationException();
        return users.replace(userInfo.getUsername(), userInfo);
    }
        public UserInfo deleteUser(String userName) throws UserDoesNotExistException {
        if (!users.containsKey(userName)) throw new UserDoesNotExistException();
        return users.remove(userName);
    }
}
