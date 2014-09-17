package be.witspirit.iswamtoday.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representation of a single Swim log entry
 */
public class SwimEntry {
    private String date; // Will encode the date yyyy-mm-dd to have some control over the format
    private int distance; // in meters

    public SwimEntry(Date date, int distance) {
        this.date = formatter().format(date);
        this.distance = distance;
    }

    public static SwimEntry today(int distance) {
        return new SwimEntry(new Date(), distance);
    }

    public static SwimEntry parse(String date, int distance) {
        try {
            return new SwimEntry(formatter().parse(date), distance);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse "+date+" as a date in the format YYYY-MM-DD");
        }
    }

    public String getDate() {
        return date;
    }

    public int getDistance() {
        return distance;
    }

    private static SimpleDateFormat formatter() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
