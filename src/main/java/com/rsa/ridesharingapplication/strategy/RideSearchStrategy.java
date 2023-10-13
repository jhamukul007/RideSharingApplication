package com.rsa.ridesharingapplication.strategy;

import com.rsa.ridesharingapplication.models.Vehicle;
import com.rsa.ridesharingapplication.models.VehicleRide;

import java.time.LocalDateTime;
import java.util.List;

public interface RideSearchStrategy {
    List<VehicleRide> matchRide(String source, String destination, LocalDateTime localDateTime, List<VehicleRide> rides, Integer seatRequired);
}
