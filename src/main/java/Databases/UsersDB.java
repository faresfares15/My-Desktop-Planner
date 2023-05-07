package Databases;

import Models.UserSchema;

import java.util.TreeMap;

public class UsersDB {
    private TreeMap<String, UserSchema> users = new TreeMap<>();
    //TODO: check if the boolean type return is necessary or if we can just throw an exception
    public UserSchema addUser(UserSchema userSchema) throws UniqueUsernameViolationException {
        //the UserController class will create a UserInfo object and pass it to this method
        if (users.containsKey(userSchema.getUsername())) throw new UniqueUsernameViolationException();
        return users.put(userSchema.getUsername(), userSchema);
    }
    public UserSchema updateUser(UserSchema userSchema) throws UniqueUsernameViolationException{
        //TODO: check if we son't need other excpetions in case the userInfo object has null or empty values
        //No need to check if the users exists or nah because only the currentUser can update his own info
        if (users.containsKey(userSchema.getUsername())) throw new UniqueUsernameViolationException();
        return users.replace(userSchema.getUsername(), userSchema);
    }
        public UserSchema deleteUser(String userName) throws UserDoesNotExistException {
        if (!users.containsKey(userName)) throw new UserDoesNotExistException();
        return users.remove(userName);
    }
}
