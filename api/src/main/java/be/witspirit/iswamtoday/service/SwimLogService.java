package be.witspirit.iswamtoday.service;

import be.witspirit.iswamtoday.google.AuthScopes;
import be.witspirit.iswamtoday.google.Constants;
import be.witspirit.iswamtoday.model.AccumulatedDistance;
import be.witspirit.iswamtoday.model.SwimEntry;
import com.google.api.server.spi.config.Api;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * API Definition of the Log operations
 */
@Api(name = "swimLog", version = "v1",
        scopes = {AuthScopes.PROFILE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE})
public class SwimLogService {

    private List<SwimEntry> entries = new ArrayList<>();

    public SwimEntry log(@Named("swimDate") String date, @Named("distance") int distance) {
        SwimEntry entry = SwimEntry.parse(date, distance);
        entries.add(entry);
        return entry;
    }

    public SwimEntry logToday(@Named("distance") int distance) {
        SwimEntry entry = SwimEntry.today(distance);
        entries.add(entry);
        return entry;
    }

    public List<SwimEntry> getLogs() {
        return entries;
    }

    public AccumulatedDistance getTotalDistance() {
        long total = 0;
        for (SwimEntry entry : entries) {
            total += entry.getDistance();
        }
        return new AccumulatedDistance(total);
    }
}
