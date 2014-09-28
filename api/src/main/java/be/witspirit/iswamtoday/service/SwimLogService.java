package be.witspirit.iswamtoday.service;

import be.witspirit.iswamtoday.google.AuthScopes;
import be.witspirit.iswamtoday.google.Constants;
import be.witspirit.iswamtoday.model.AccumulatedDistance;
import be.witspirit.iswamtoday.model.SwimEntry;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
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
        // Configuring the root is currently limited and hence, the default seems to be the most sensible to use
)
public class SwimLogService {

    private List<SwimEntry> entries = new ArrayList<>();

    @ApiMethod(path = "logs")
    public SwimEntry log(User user, SwimEntry logEntry) throws OAuthRequestException {
        requireUser(user);
        entries.add(logEntry);
        return logEntry;
    }

    @ApiMethod(path = "logs/today/{distance}")
    public SwimEntry logToday(User user, @Named("distance") int distance) throws OAuthRequestException {
        requireUser(user);
        SwimEntry entry = SwimEntry.today(distance);
        entries.add(entry);
        return entry;
    }

    @ApiMethod(path = "logs")
    public List<SwimEntry> getLogs(User user) throws OAuthRequestException {
        requireUser(user);
        return entries;
    }

    @ApiMethod(path = "total")
    public AccumulatedDistance getTotalDistance(User user) throws OAuthRequestException {
        requireUser(user);
        long total = 0;
        for (SwimEntry entry : entries) {
            total += entry.getDistance();
        }
        return new AccumulatedDistance(total);
    }

    @ApiMethod(path = "test/user")
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
