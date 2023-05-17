package Models.User;
import Models.Calendar.CalendarSchema;

public class UserSchema implements Comparable<UserSchema>{
    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private String email;

    public UserSchema(String lastName, String firstName, String password, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.email = email;
    }

    public UserSchema(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserSchema() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
        return (this.lastName + this.firstName).compareTo(o.lastName + o.firstName);
    }

    @Override
    public String toString() {
        return "UserSchema{" +
                "username='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
