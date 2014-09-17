package be.witspirit.iswamtoday.service;

import be.witspirit.iswamtoday.google.AuthScopes;
import be.witspirit.iswamtoday.google.Constants;
import be.witspirit.iswamtoday.model.AccumulatedDistance;
import be.witspirit.iswamtoday.model.SwimEntry;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * API Definition of the Log operations
 */
@Api(name = "swimLog", version = "v1",
        scopes = {AuthScopes.EMAIL, AuthScopes.PROFILE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE},
        description = "Perform Swim Log operations",
        namespace = @ApiNamespace(ownerDomain = "be.witspirit.iswamtoday", ownerName = "witspirit")
)
public class SwimLogService {

    private List<SwimEntry> entries = new ArrayList<>();

    public SwimEntry log(User user, @Named("swimDate") String date, @Named("distance") int distance) throws OAuthRequestException {
        requireUser(user);
        SwimEntry entry = SwimEntry.parse(date, distance);
        entries.add(entry);
        return entry;
    }

    public SwimEntry logToday(User user, @Named("distance") int distance) throws OAuthRequestException {
        requireUser(user);
        SwimEntry entry = SwimEntry.today(distance);
        entries.add(entry);
        return entry;
    }

    public List<SwimEntry> getLogs(User user) throws OAuthRequestException {
        requireUser(user);
        return entries;
    }

    public AccumulatedDistance getTotalDistance(User user) throws OAuthRequestException {
        requireUser(user);
        long total = 0;
        for (SwimEntry entry : entries) {
            total += entry.getDistance();
        }
        return new AccumulatedDistance(total);
    }

    public User getUserInfo(User user) throws OAuthRequestException {
        requireUser(user);
        return user;
    }

    private void requireUser(User user) throws OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("A valid user is required");
        }
    }
}
