package Controllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Databases.UsersDB;
import Models.UserInfo;

public class UserController {
    //This is the only class that handles exceptions
    private UsersDB usersDB;
    private Authentificator userAuthentificator;
    private CalendarManager calendarManager;
    private UserInfo currentUserInfo;
    public UserController(){
        this.usersDB = new UsersDB();
        this.userAuthentificator = new SimpleAuthentificator();
        this.calendarManager = new Calendar();
        this.currentUserInfo = null;
    }

    public UserController(UsersDB usersDB, Authentificator userAuthentificator, CalendarManager calendarManager, UserInfo currentUserInfo) {
        this.usersDB = usersDB;
        this.userAuthentificator = userAuthentificator;
        this.calendarManager = calendarManager;
        this.currentUserInfo = currentUserInfo;
    }

    //TODO: implement these methods
    //TODO create user shouldn't have the the UserInfo as an argument, but rather its data, and it itself creates the object
    public void createUser(UserInfo userInfo) {
        //TODO: check if the userAuthenticator != null | Maybe we can do it in the GUI
        try {
            this.currentUserInfo = userAuthentificator.signUp(userInfo.getUsername(), userInfo.getPassword(), usersDB);
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
            this.currentUserInfo = userAuthentificator.login(username, password, usersDB);
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

    public void updateUser(UserInfo userInfo) {
        //Where going to change only the parameters changed by the user and create a new UserInfo object through the GUI
        // and pass it to this method
        try {
            //TODO: check if parameters are null or empty and throw an exception
             usersDB.updateUser(userInfo);
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
        return currentUserInfo.getCalendar();
    }
}
