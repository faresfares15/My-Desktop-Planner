package Databases;

import Models.User.UserSchema;

import java.io.Serializable;

public interface UserDataBase extends Serializable {
    public UserSchema create(String username, String password) throws UniqueUsernameViolationException;
    public UserSchema create(UserSchema newUser) throws UniqueUsernameViolationException;
    public boolean exists(String username);
    public UserSchema find(String username) throws UserDoesNotExistException;
    public UserSchema update(String oldUsername,UserSchema userSchema) throws UniqueUsernameViolationException,
            UserDoesNotExistException;
    public UserSchema update(UserSchema userSchema) throws UserDoesNotExistException;
    public UserSchema delete(String username) throws UserDoesNotExistException;
}
