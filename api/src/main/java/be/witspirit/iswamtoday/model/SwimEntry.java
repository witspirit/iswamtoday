package be.witspirit.iswamtoday.model;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonCreator;
import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representation of a single Swim log entry
 */
public class SwimEntry {
    private String date; // Will encode the date yyyy-mm-dd to have some control over the format
    private int distance; // in meters

    @JsonCreator
    public SwimEntry(@JsonProperty("date") String date, @JsonProperty("distance") int distance) {
        this.date = normalizeDate(date);
        this.distance = distance;
    }

    public static SwimEntry today(int distance) {
        return new SwimEntry(formatter().format(new Date()), distance);
    }

    private static String normalizeDate(String dateString) {
        try {
            Date parsedDate = formatter().parse(dateString);
            return formatter().format(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse "+dateString+" as a date in the format YYYY-MM-DD");
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
