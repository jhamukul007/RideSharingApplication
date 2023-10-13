package com.rsa.ridesharingapplication.models;

import com.rsa.ridesharingapplication.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Vehicle extends BaseEntity {
    private String number;
    private String modelName;
    private Integer totalSeat;
    private User user;
    private VehicleStatus status;

    public Vehicle(String number, String modelName, Integer totalSeat, User user) {
        this.number = number;
        this.modelName = modelName;
        this.totalSeat = totalSeat;
        this.user = user;
        this.status = VehicleStatus.ACTIVE;
    }
}
