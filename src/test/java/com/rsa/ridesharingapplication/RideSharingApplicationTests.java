package com.rsa.ridesharingapplication;

import com.rsa.ridesharingapplication.enums.Gender;
import com.rsa.ridesharingapplication.logging.ConsoleLogger;
import com.rsa.ridesharingapplication.logging.Logger;
import com.rsa.ridesharingapplication.services.RideBookingService;
import com.rsa.ridesharingapplication.services.UserService;
import com.rsa.ridesharingapplication.services.VehicleService;
import com.rsa.ridesharingapplication.strategy.LowestDurationRideSearchStrategy;
import com.rsa.ridesharingapplication.strategy.RideSearchStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

//@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RideSharingApplicationTests {
    private UserService userService;
    private VehicleService vehicleService;
    private Logger logger;

    private RideSearchStrategy rideSearchStrategy;

    private RideBookingService rideBookingService;

    @BeforeAll
    public void init() {
        this.logger = new ConsoleLogger();
        this.userService = new UserService(logger);
        this.rideSearchStrategy = new LowestDurationRideSearchStrategy();
        this.rideBookingService = new RideBookingService();
        this.vehicleService = new VehicleService(logger, userService, rideSearchStrategy, rideBookingService);
    }


    void registerUser() {
        userService.registerUser("Mukul", 9090909090L, Gender.MALE, 26);
        userService.registerUser("Jack", 8989898989L, Gender.MALE, 30);
        userService.registerUser("Rajesh", 0000000000L, Gender.MALE, 35);
        userService.registerUser("Jack", 11111111L, Gender.MALE, 25);
        // Rider
        userService.registerUser("Isha", 808080088L, Gender.FEMALE, 22);
        userService.registerUser("Mohan", 9090909111L, Gender.MALE, 18);

    }

    @Test
    void registerVehicle() {
        registerUser();
        vehicleService.addVehicle(9090909090L, "KA02JS7878", "swift", 5);
        vehicleService.addVehicle(8989898989L, "KA02J9090", "Thar", 6);
        vehicleService.addVehicle(0000000000L, "DL03JS9090", "Balino", 5);
        vehicleService.addVehicle(11111111L, "MH03JS0007", "XUV 700", 7);
    }

    @Test
    void offerRides() {
        registerVehicle();
        vehicleService.offerRide(9090909090L, "KA02JS7878", 4, "Bangalore", "Mysore", 2L, LocalDateTime.now().plusDays(1));
        vehicleService.offerRide(8989898989L, "KA02J9090", 5, "Bangalore", "Mumbai", 12L, LocalDateTime.now().plusDays(2));
        vehicleService.offerRide(11111111L, "DL03JS9090", 5, "Bangalore", "Mumbai", 10L, LocalDateTime.now().plusDays(2));
    }

    @Test
    void searchRide() {
        offerRides();
        vehicleService.searchRide(9090909111L, "Bangalore", "Mumbai", 4, LocalDateTime.now().plusDays(2));
        vehicleService.searchRide(808080088L, "Bangalore", "Mumbai", 2, LocalDateTime.now().plusDays(2));
    }



    @Test
    void registerUserFail() {
        //userService.registerUser("Jack", 9090909090L, Gender.MALE, 26);
    }


}
