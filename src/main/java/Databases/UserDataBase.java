package Databases;

import Models.User.UserSchema;

public interface UserDataBase {
    public UserSchema create(UserSchema userSchema) throws UniqueUsernameViolationException;
    public UserSchema find(String username) throws UserDoesNotExistException;
    public UserSchema update(String oldUsername,UserSchema userSchema) throws UniqueUsernameViolationException,
            UserDoesNotExistException;
    public UserSchema update(UserSchema userSchema) throws UserDoesNotExistException;
    public UserSchema delete(String username) throws UserDoesNotExistException;
}
