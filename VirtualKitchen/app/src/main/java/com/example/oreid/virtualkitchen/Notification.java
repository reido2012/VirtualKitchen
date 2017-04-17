package com.example.oreid.virtualkitchen;

/**
 * Notification class
 *
 * Contains information about a food item which a notification has to be displayed for.
 *
 * Created by hollie on 15/04/2017.
 */

public class Notification {


    /**
     * Create a new Notification
     * @param f food which the notification is about
     * @param notificationMessage message to display to the user.
     * @param cat NotificationCategory the notification belongs to.
     */
    public Notification(FoodItem f, NotificationCategory cat) {
        this.food = f;
        this.category = cat;
    }

    public String listMessage() {
        int days = food.getDaysLeft();
        String name = food.getName();
        String msg = "";

        if (days < 0) {
            msg = name + " is out of date by " + (0 - days) + " days!";
        } else if (days == 0) {
            msg = name + " expres today.";
        } else if (days == 1) {
            msg = name + " expires tomorrow.";
        } else {
            msg = name + " expires in " + days + " days.";
        }

        return msg;
    }


    public FoodItem food;
    public String notificationMessage;
    public NotificationCategory category;

    public enum NotificationCategory {
        EXPIRED("Out of date!"),
        TODAY("Expiring today"),
        TOMORROW("Expiring tomorrow"),
        SOON("Expiring soon");

        private final String category;
        NotificationCategory(String cat) { category = cat; }
        public String toString() {
            return category;
        }

    }
}
