package Controllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Databases.UsersDB;
import Models.UserSchema;

public class UserController {
    //This is the only class that handles exceptions
    private UsersDB usersDB;
    private Authentificator userAuthentificator;
    private CalendarManager calendarManager;
    private UserSchema currentUserSchema;
    public UserController(){
        this.usersDB = new UsersDB();
        this.userAuthentificator = new SimpleAuthentificator();
        this.calendarManager = new Calendar();
        this.currentUserSchema = null;
    }

    public UserController(UsersDB usersDB, Authentificator userAuthentificator, CalendarManager calendarManager, UserSchema currentUserSchema) {
        this.usersDB = usersDB;
        this.userAuthentificator = userAuthentificator;
        this.calendarManager = calendarManager;
        this.currentUserSchema = currentUserSchema;
    }

    //TODO: implement these methods
    //TODO create user shouldn't have the the UserInfo as an argument, but rather its data, and it itself creates the object
    public void createUser(UserSchema userSchema) {
        //TODO: check if the userAuthenticator != null | Maybe we can do it in the GUI
        try {
            this.currentUserSchema = userAuthentificator.signUp(userSchema.getUsername(), userSchema.getPassword(), usersDB);
        } catch (PasswordNotProvidedException e) {
            System.out.println(e.getMessage());
        } catch (UserNameNotProvidedException e) {
            System.out.println(e.getMessage());
        } catch (UniqueUsernameViolationException e) {
            System.out.println(e.getMessage());
        }
    }
    public void login(String username, String password) {
        try {
            this.currentUserSchema = userAuthentificator.login(username, password, usersDB);
        } catch (UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (WrongPasswordException e) {
            System.out.println(e.getMessage());
        } catch (PasswordNotProvidedException e) {
            System.out.println(e.getMessage());
        } catch (UserNameNotProvidedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(UserSchema userSchema) {
        //Where going to change only the parameters changed by the user and create a new UserInfo object through the GUI
        // and pass it to this method
        try {
            //TODO: check if parameters are null or empty and throw an exception
             usersDB.updateUser(userSchema);
        } catch (UniqueUsernameViolationException e) {
            e.getMessage();
        }
    }
    public void deleteUser(String userName) {
        try {
             usersDB.deleteUser(userName);
        } catch (UserDoesNotExistException e) {
            e.getMessage();
        }
    }
    public Calendar getCurrentUserCalendar(){
        return currentUserSchema.getCalendar();
    }
}
