package com.rsa.ridesharingapplication.models;

import com.rsa.ridesharingapplication.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleRide extends BaseEntity {
    private String source;
    private String destination;
    private Vehicle vehicle;
    private Integer seatOffered;
    private Integer seatAvailable;
    private RideStatus rideStatus;
    private LocalDateTime scheduledTime;
    private Long duration;

    public void bookSeat(int numberOfSeat) {
        this.seatAvailable -= numberOfSeat;
    }
}
