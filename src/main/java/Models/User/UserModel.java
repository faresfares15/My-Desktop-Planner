package Models.User;

import Databases.UniqueUsernameViolationException;
import Databases.UserDataBase;
import Databases.UserDoesNotExistException;

import java.util.TreeMap;

public class UserModel {
    private UserDataBase userDataBase;

    public UserModel(UserDataBase userDataBase) {
        this.userDataBase = userDataBase;
    }

    public UserSchema create(UserSchema userSchema) throws UniqueUsernameViolationException {
        return this.userDataBase.create(userSchema);
    }
    public UserSchema create(String username, String password) throws UniqueUsernameViolationException {
        return this.userDataBase.create(new UserSchema(username, password));
    }
    public UserSchema find(String username) throws UserDoesNotExistException{
        return this.userDataBase.find(username);
    }
    public UserSchema update(String oldUsername,UserSchema userSchema) throws UniqueUsernameViolationException,
            UserDoesNotExistException{
        return this.userDataBase.update(oldUsername, userSchema);
    }
    public UserSchema update(UserSchema userSchema) throws UserDoesNotExistException{
       return this.userDataBase.update(userSchema);
    }
    public UserSchema delete(String username) throws UserDoesNotExistException{
        return this.userDataBase.delete(username);
    }


}
