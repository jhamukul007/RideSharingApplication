package com.rsa.ridesharingapplication.services;

import com.rsa.ridesharingapplication.enums.RideBookingStatus;
import com.rsa.ridesharingapplication.enums.RideStatus;
import com.rsa.ridesharingapplication.exceptions.RideBookingDetailsNotFoundException;
import com.rsa.ridesharingapplication.logging.Logger;
import com.rsa.ridesharingapplication.models.RideBooking;
import com.rsa.ridesharingapplication.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.ObjDoubleConsumer;
import java.util.stream.Collectors;

public class RideBookingService {
    private final List<RideBooking> rideBookings;
    private final Logger logger;

    public RideBookingService(Logger logger) {
        this.logger = logger;
        this.rideBookings = new ArrayList<>();
    }

    public RideBooking bookRide(RideBooking rideBooking) {
        rideBookings.add(rideBooking);
        return rideBooking;
    }

    public void startRide(String rideId) {
        RideBooking rideBooking = getBookedRideDetailsById(rideId).orElseThrow(() -> new RideBookingDetailsNotFoundException());
        if (!RideBookingStatus.SCHEDULED.equals(rideBooking.getRideBookingStatus()))
            throw new IllegalStateException("Ride not in scheduled state");
        rideBooking.getVehicleRide().setRideStatus(RideStatus.ONGOING);
        rideBooking.setRideBookingStatus(RideBookingStatus.ONGOING);
        logger.log("Starting ride of vehicle " + rideBooking.getVehicleRide().getVehicle().getNumber());
    }

    public void markEndRide(String rideId) {
        RideBooking rideBooking = getBookedRideDetailsById(rideId).orElseThrow(() -> new RideBookingDetailsNotFoundException());
        if (!RideBookingStatus.ONGOING.equals(rideBooking.getRideBookingStatus()))
            throw new IllegalStateException("Ride not in ongoing state");
        rideBooking.getVehicleRide().setRideStatus(RideStatus.COMPLETED);
        rideBooking.setRideBookingStatus(RideBookingStatus.COMPLETED);
        logger.log("Ending ride of vehicle " + rideBooking.getVehicleRide().getVehicle().getNumber());
    }

    public Optional<RideBooking> getBookedRideDetailsById(String id) {
        return rideBookings.stream().filter(rideBooking -> Objects.deepEquals(id, rideBooking.getId())).findFirst();
    }

    public List<RideBooking> getAllRideBookByRider(User rider) {
       return rideBookings.stream().filter(rideBooking -> Objects.deepEquals(rideBooking.getRider().getPhone(), rider.getPhone())).collect(Collectors.toList());
    }
}
