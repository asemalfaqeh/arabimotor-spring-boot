package com.af.arabimotors.utils;

import com.af.arabimotors.entities.VehiclesEntity;

import java.util.Comparator;

public class ComparableVehiclePrices extends VehiclesEntity implements Comparator<VehiclesEntity> {
    @Override
    public int compare(VehiclesEntity o1, VehiclesEntity o2) {
        return Integer.compare(Integer.parseInt(o2.getPrice()), Integer.parseInt(o1.getPrice()));
    }

    // The other stuff of the car class
}