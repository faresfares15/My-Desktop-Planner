package Models;

//import Controllers.Calendar;
import Databases.UniqueUsernameViolationException;
import Databases.UsersDB;
import Controllers.CalendarControllers.Calendar;

public class UserSchema implements Comparable<UserSchema>{
    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private Calendar calendar;

    public UserSchema(String lastName, String firstName, String password, String email, Calendar calendar) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.calendar = calendar;
    }

    public UserSchema(String username, String password) {
        this.username = username;
        this.password = password;
        this.calendar = new Calendar();
    }

    public UserSchema() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean equals(Object obj) {
        return this.username.equals(((UserSchema)obj).username);
    }

    @Override
    public int compareTo(UserSchema o) {
        return (this.lastName + this.firstName).compareTo(o.lastName + o.firstName);
    }

}
