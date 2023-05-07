package Models;

import Controllers.CalendarControllers.Calendar;

public class UserInfo  implements Comparable<UserInfo>{
    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private Calendar calendar;

    public UserInfo(String lastName, String firstName, String password, String email, Calendar calendar) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.calendar = calendar;
    }

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
        this.calendar = new Calendar();
    }

    public UserInfo() {
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
        return this.username.equals(((UserInfo)obj).username);
    }

    @Override
    public int compareTo(UserInfo o) {
        return (this.lastName + this.firstName).compareTo(o.lastName + o.firstName);
    }
}
