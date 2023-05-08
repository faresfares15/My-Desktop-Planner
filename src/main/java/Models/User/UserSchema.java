package Models.User;
import Models.Calendar.CalendarSchema;

public class UserSchema implements Comparable<UserSchema>{
    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private CalendarSchema calendarSchema;

    public UserSchema(String lastName, String firstName, String password, String email, CalendarSchema calendarSchema) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.calendarSchema = calendarSchema;
    }

    public UserSchema(String username, String password) {
        this.username = username;
        this.password = password;
        this.calendarSchema = new CalendarSchema();
    }

    public UserSchema() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public CalendarSchema getCalendar() {
        return calendarSchema;
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
