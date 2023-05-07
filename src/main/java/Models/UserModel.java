package Models;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;

import java.util.TreeMap;

public class UserModel {
    private TreeMap<String, UserSchema> users = new TreeMap<>();
    //CRUD operations implementation using an MVC architecture
    public void create(UserSchema userSchema){
        users.put(userSchema.getUsername(), userSchema);
    }

    public UserSchema read(String username) throws UserDoesNotExistException {
        if (!users.containsKey(username)) throw new UserDoesNotExistException();
        return users.get(username);
    }

    public void update(String oldUsername,UserSchema userSchema) throws UniqueUsernameViolationException,
            UserDoesNotExistException {
        // In case he changes his username
        if (!users.containsKey(oldUsername)) throw new UserDoesNotExistException();
        if (!users.containsKey(userSchema.getUsername())) throw new UniqueUsernameViolationException();
        users.remove(oldUsername);
        users.put(userSchema.getUsername(), userSchema);
    }

    public void update(UserSchema userSchema) throws UserDoesNotExistException {
        // In case he doesn't change his username
        if (!users.containsKey(userSchema.getUsername())) throw new UserDoesNotExistException();
        users.replace(userSchema.getUsername(), userSchema);
    }
    public void delete(String username) throws UserDoesNotExistException {
        if (!users.containsKey(username)) throw new UserDoesNotExistException();
        users.remove(username);
    }
}
