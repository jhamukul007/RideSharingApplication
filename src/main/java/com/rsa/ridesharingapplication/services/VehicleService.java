package com.rsa.ridesharingapplication.services;

import com.rsa.ridesharingapplication.enums.RideBookingStatus;
import com.rsa.ridesharingapplication.enums.RideStatus;
import com.rsa.ridesharingapplication.enums.VehicleStatus;
import com.rsa.ridesharingapplication.exceptions.RideNotMatchException;
import com.rsa.ridesharingapplication.exceptions.UserNotFoundException;
import com.rsa.ridesharingapplication.exceptions.VehicleFoundException;
import com.rsa.ridesharingapplication.exceptions.VehicleNotFoundException;
import com.rsa.ridesharingapplication.logging.Logger;
import com.rsa.ridesharingapplication.models.RideBooking;
import com.rsa.ridesharingapplication.models.User;
import com.rsa.ridesharingapplication.models.Vehicle;
import com.rsa.ridesharingapplication.models.VehicleRide;
import com.rsa.ridesharingapplication.strategy.RideSearchStrategy;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class VehicleService {
    private final List<Vehicle> vehicleList;

    private final List<VehicleRide> vehicleRideList;

    private final Logger logger;
    private final UserService userService;

    private final RideSearchStrategy rideSearchStrategy;
    private final RideBookingService rideBookingService;

    public VehicleService(Logger logger, UserService userService, RideSearchStrategy rideSearchStrategy, RideBookingService rideBookingService) {
        this.userService = userService;
        this.rideSearchStrategy = rideSearchStrategy;
        this.rideBookingService = rideBookingService;
        this.vehicleList = new ArrayList<>();
        this.logger = logger;
        this.vehicleRideList = new ArrayList<>();
    }

    public Vehicle addVehicle(Long userPhone, String vehicleNumber, String modelName, Integer totalSeat) {
        User user = userService.findByPhone(userPhone).orElseThrow(() -> new UserNotFoundException());
        if (getVehicleByNumber(vehicleNumber).isPresent())
            throw new VehicleFoundException();
        Vehicle vehicle = Vehicle.builder()
                .modelName(modelName)
                .totalSeat(totalSeat)
                .number(vehicleNumber)
                .status(VehicleStatus.ACTIVE)
                .user(user)
                .build();
        vehicleList.add(vehicle);
        logger.log(String.format("vehicle %s has been added to user %s ", vehicleNumber, user.getName()));
        return vehicle;
    }

    public Optional<Vehicle> getVehicleByNumber(String vehicleNumber) {
        return vehicleList.stream().filter(vehicle -> Objects.deepEquals(vehicleNumber, vehicle.getNumber())).findFirst();
    }

    public void offerRide(Long userPhone, String vehicleNumber, Integer availableSeat, String source, String destination, Long duration, LocalDateTime time) {
        User user = userService.findByPhone(userPhone).orElseThrow(() -> new UserNotFoundException());
        Vehicle vehicle = getVehicleByNumber(vehicleNumber).orElseThrow(() -> new VehicleNotFoundException());
        if (availableSeat > vehicle.getTotalSeat())
            throw new IllegalArgumentException("Offered seat can't more than total car seat");
        VehicleRide vehicleRide = VehicleRide.builder()
                .vehicle(vehicle)
                .source(source)
                .destination(destination)
                .rideStatus(RideStatus.SCHEDULED)
                .scheduledTime(time)
                .duration(duration)
                .seatOffered(availableSeat)
                .seatAvailable(availableSeat)
                .build();
        logger.log("Offered ride " + vehicleRide);
        vehicleRideList.add(vehicleRide);
    }

    public RideBooking searchRide(Long phoneNumber, String source, String destination, Integer numberOfSeats, LocalDateTime date) {
        User user = userService.findByPhone(phoneNumber).orElseThrow(() -> new UserNotFoundException());
        List<VehicleRide> vehicleRides = rideSearchStrategy.matchRide(source, destination, date, vehicleRideList, numberOfSeats);
        if (CollectionUtils.isEmpty(vehicleRides))
            throw new RideNotMatchException();
        VehicleRide vehicleRide = vehicleRides.get(0);
        vehicleRide.bookSeat(numberOfSeats);
        RideBooking rideBooking = rideBookingService.bookRide(RideBooking.builder()
                .vehicleRide(vehicleRide)
                .rider(user)
                .rideBookingStatus(RideBookingStatus.SCHEDULED)
                .bookingTime(LocalDateTime.now())
                .build());
        logger.log("Found Car number and trip details  " + rideBooking);
        return rideBooking;
    }

}
