package Models.User;
import Models.Calendar.CalendarSchema;
import Models.Calendar.Settings;

import java.io.Serializable;

public class UserSchema implements Comparable<UserSchema>, Serializable {
    private String username;
//    private String lastName;
//    private String firstName;
    private String password;
    private Settings settings;
//    private String email;

//    public UserSchema(String lastName, String firstName, String password, String email) {
//        this.lastName = lastName;
//        this.firstName = firstName;
//        this.password = password;
//        this.email = email;
//    }

    public UserSchema(String username, String password) {
        this.username = username;
        this.password = password;
        this.settings = new Settings();
    }

    public UserSchema() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        } else if (obj instanceof UserSchema) {
            return this.username.equals(((UserSchema) obj).getUsername());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(UserSchema o) {
        return (this.username).compareTo(o.getUsername());
    }

    @Override
    public String toString() {
        return "UserSchema{" +
                "username='" + username + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
//                ", email='" + email + '\'' +
                '}';
    }
}
