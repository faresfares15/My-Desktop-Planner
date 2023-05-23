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
    private int mostCongratsReceivedInARow;
    private int totalCongratsReceived;
    private int congratsInARowCounter;
    private int numberOfGoodBadges;
    private int numberOfVeryGoodBadges;
    private int numberOfExcellentBadges;
    private Settings settings;
    public int getMostCongratsReceivedInARow() {
        return mostCongratsReceivedInARow;
    }

    public void setMostCongratsReceivedInARow(int mostCongratsReceivedInARow) {
        this.mostCongratsReceivedInARow = mostCongratsReceivedInARow;
    }
    public UserSchema(String username, String password) {
        this.username = username;
        this.password = password;
        this.settings = new Settings();
        this.mostProductiveDate = LocalDate.now();
        this.mostTasksCompletedInADay = 0;
        this.congratsInARowCounter = 0;
        this.mostCongratsReceivedInARow = 0;
        this.totalCongratsReceived = 0;
        this.numberOfGoodBadges = 0;
        this.numberOfVeryGoodBadges = 0;
        this.numberOfExcellentBadges = 0;
    }

    public UserSchema() {
        this.settings = new Settings();
        this.mostProductiveDate = LocalDate.now();
        this.mostTasksCompletedInADay = 0;
        this.congratsInARowCounter = 0;
        this.mostCongratsReceivedInARow = 0;
        this.totalCongratsReceived = 0;
        this.numberOfGoodBadges = 0;
        this.numberOfVeryGoodBadges = 0;
        this.numberOfExcellentBadges = 0;
    }

    public int getNumberOfGoodBadges() {
        return numberOfGoodBadges;
    }

    public void setNumberOfGoodBadges(int numberOfGoodBadges) {
        this.numberOfGoodBadges = numberOfGoodBadges;
    }

    public int getNumberOfVeryGoodBadges() {
        return numberOfVeryGoodBadges;
    }

    public void setNumberOfVeryGoodBadges(int numberOfVeryGoodBadges) {
        this.numberOfVeryGoodBadges = numberOfVeryGoodBadges;
    }

    public int getNumberOfExcellentBadges() {
        return numberOfExcellentBadges;
    }

    public void setNumberOfExcellentBadges(int numberOfExcellentBadges) {
        this.numberOfExcellentBadges = numberOfExcellentBadges;
    }

    public int getCongratsInARowCounter() {
        return congratsInARowCounter;
    }

    public void setCongratsInARowCounter(int congratsInARowCounter) {
        this.congratsInARowCounter = congratsInARowCounter;
    }

    public int getTotalCongratsReceived() {
        return totalCongratsReceived;
    }

    public void setTotalCongratsReceived(int totalCongratsReceived) {
        this.totalCongratsReceived = totalCongratsReceived;
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
