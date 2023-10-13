package com.rsa.ridesharingapplication.services;

import com.rsa.ridesharingapplication.enums.RideBookingStatus;
import com.rsa.ridesharingapplication.enums.RideStatus;
import com.rsa.ridesharingapplication.exceptions.RideBookingDetailsNotFoundException;
import com.rsa.ridesharingapplication.models.RideBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RideBookingService {
    private final List<RideBooking> rideBookings;

    public RideBookingService() {
        this.rideBookings = new ArrayList<>();
    }

    public RideBooking bookRide(RideBooking rideBooking) {
        rideBookings.add(rideBooking);
        return rideBooking;
    }

    public void startRide(String rideId) {
        RideBooking rideBooking = getBookedRideDetailsById(rideId).orElseThrow(() -> new RideBookingDetailsNotFoundException());
        rideBooking.getVehicleRide().setRideStatus(RideStatus.ONGOING);
        rideBooking.setRideBookingStatus(RideBookingStatus.ONGOING);
    }

    public void markEndRide(String rideId) {
        RideBooking rideBooking = getBookedRideDetailsById(rideId).orElseThrow(() -> new RideBookingDetailsNotFoundException());
        rideBooking.getVehicleRide().setRideStatus(RideStatus.COMPLETED);
        rideBooking.setRideBookingStatus(RideBookingStatus.COMPLETED);
    }

    public Optional<RideBooking> getBookedRideDetailsById(String id) {
        return rideBookings.stream().filter(rideBooking -> Objects.deepEquals(id, rideBooking.getId())).findFirst();
    }
}
