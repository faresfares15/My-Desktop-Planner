package Databases;

import Models.User.UserSchema;

import java.util.Objects;
import java.util.TreeMap;

public class UserFileDataBase implements UserDataBase {
    private final TreeMap<String, UserSchema> users = new TreeMap<>();

    //CRUD operations implementation using an MVC architecture
    public UserSchema create(UserSchema userSchema) throws UniqueUsernameViolationException {
        if (users.containsKey(userSchema.getUsername())) throw new UniqueUsernameViolationException();
        return users.put(userSchema.getUsername(), userSchema);
    }

    public UserSchema find(String username) throws UserDoesNotExistException {
        if (!users.containsKey(username)) throw new UserDoesNotExistException();
        return users.get(username);
    }

    public UserSchema update(String oldUsername, UserSchema userSchema) throws UniqueUsernameViolationException,
            UserDoesNotExistException {

        if (!users.containsKey(oldUsername)) throw new UserDoesNotExistException();

        if (Objects.equals(userSchema.getUsername(), oldUsername)) {
            return users.replace(oldUsername, userSchema);
        } else { // In case he changes his username
            if (users.containsKey(userSchema.getUsername())) throw new UniqueUsernameViolationException();
            users.remove(oldUsername);
            return users.put(userSchema.getUsername(), userSchema);
        }

    }

    public UserSchema update(UserSchema userSchema) throws UserDoesNotExistException {
        // In case he doesn't change his username
        if (!users.containsKey(userSchema.getUsername())) throw new UserDoesNotExistException();
        return users.replace(userSchema.getUsername(), userSchema);
    }

    public UserSchema delete(String username) throws UserDoesNotExistException {
        if (!users.containsKey(username)) throw new UserDoesNotExistException();
        return users.remove(username);
    }
}
