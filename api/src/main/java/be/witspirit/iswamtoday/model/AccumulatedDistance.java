package be.witspirit.iswamtoday.model;

/**
 * Representation of the Total distance recorded
 */
public class AccumulatedDistance {
    private long totalDistance;

    public AccumulatedDistance(long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public long getTotalDistance() {
        return totalDistance;
    }
}
