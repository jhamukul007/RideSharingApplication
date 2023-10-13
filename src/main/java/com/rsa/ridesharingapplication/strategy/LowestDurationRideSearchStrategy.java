package com.rsa.ridesharingapplication.strategy;

import com.rsa.ridesharingapplication.enums.RideStatus;
import com.rsa.ridesharingapplication.models.VehicleRide;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LowestDurationRideSearchStrategy implements RideSearchStrategy {

    @Override
    public List<VehicleRide> matchRide(String source, String destination,
                                       LocalDateTime localDateTime, List<VehicleRide> rides, Integer seatRequired) {

        List<VehicleRide> availableRides = rides.stream().filter(ride ->
                RideStatus.SCHEDULED.equals(ride.getRideStatus()) &&
                        sourceMatch(ride, source) && destinationMatch(ride, destination) && dateMatch(ride, localDateTime)
                        && ride.getSeatAvailable() >= seatRequired).collect(Collectors.toList());
        Collections.sort(availableRides, Comparator.comparing(VehicleRide::getDuration));
        return availableRides;
    }

    private boolean sourceMatch(VehicleRide ride, String source) {
        return Objects.deepEquals(ride.getSource(), source);
    }

    private boolean destinationMatch(VehicleRide ride, String destination) {
        return Objects.deepEquals(ride.getDestination(), destination);
    }

    private boolean dateMatch(VehicleRide ride, LocalDateTime time) {
        Duration duration = Duration.between(ride.getScheduledTime(), time);
        if (duration.toHours() < 3)
            return true;
        return false;
    }


}
