package Models.User;
import Models.Calendar.Settings;

import java.io.Serializable;
import java.time.LocalDate;

public class UserSchema implements Comparable<UserSchema>, Serializable {
    private String username;
//    private String lastName;
//    private String firstName;
    private String password;
    private LocalDate mostProductiveDate;
    private int mostTasksCompletedInADay;
    private int congratsReceivedInARow;
    private boolean wasCongratulatedToday;
    private Settings settings;
//    private String email;

//    public UserSchema(String lastName, String firstName, String password, String email) {
//        this.lastName = lastName;
//        this.firstName = firstName;
//        this.password = password;
//        this.email = email;
//    }

    public int getCongratsReceivedInARow() {
        return congratsReceivedInARow;
    }

    public void setCongratsReceivedInARow(int congratsReceivedInARow) {
        this.congratsReceivedInARow = congratsReceivedInARow;
    }

    public boolean isWasCongratulatedToday() {
        return wasCongratulatedToday;
    }

    public void setWasCongratulatedToday(boolean wasCongratulatedToday) {
        this.wasCongratulatedToday = wasCongratulatedToday;
    }

    public UserSchema(String username, String password) {
        this.username = username;
        this.password = password;
        this.settings = new Settings();
        this.mostProductiveDate = null; //TODO: in the view gotta put a placeHolder when it's null meaning it's a new user
        this.mostTasksCompletedInADay = 0;
        this.wasCongratulatedToday = false;
        this.congratsReceivedInARow = 0;
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

    public LocalDate getMostProductiveDate() {
        return mostProductiveDate;
    }

    public void setMostProductiveDate(LocalDate mostProductiveDate) {
        this.mostProductiveDate = mostProductiveDate;
    }

    public int getMostTasksCompletedInADay() {
        return mostTasksCompletedInADay;
    }

    public void setMostTasksCompletedInADay(int mostTasksCompletedInADay) {
        this.mostTasksCompletedInADay = mostTasksCompletedInADay;
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
