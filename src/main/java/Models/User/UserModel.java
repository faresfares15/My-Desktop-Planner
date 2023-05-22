package Models.User;

import Databases.*;
import esi.tp_poo_final.HelloApplication;

import java.io.*;
import java.util.TreeMap;

public class UserModel {
    private UserDataBase userDataBase;

    public UserModel(UserDataBase userDataBase) {
        this.userDataBase = userDataBase;
    }
    public void save() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(HelloApplication.usersDbFileName))) {
            objectOutputStream.writeObject(userDataBase);
        }
    }
    public void load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(HelloApplication.usersDbFileName)))) {

                userDataBase = (UserDataBase) objectInputStream.readObject();
                System.out.println("loading the user model");
        }
    }
    public boolean exists(String username){
        return this.userDataBase.exists(username);
    }

    public UserSchema create(UserSchema userSchema) throws UniqueUsernameViolationException {
        return this.userDataBase.create(userSchema);
    }
    public UserSchema create(String username, String password) throws UniqueUsernameViolationException {
        return this.userDataBase.create(username, password);
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
