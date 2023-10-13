package com.rsa.ridesharingapplication.models;

import com.rsa.ridesharingapplication.enums.RideBookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class RideBooking extends BaseEntity{
    private VehicleRide vehicleRide;
    private User rider;
    private RideBookingStatus rideBookingStatus;
    private LocalDateTime bookingTime;
}
