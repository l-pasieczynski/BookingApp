package com.example.BookingApp.accommodation.entity;

public enum AccommodationAddInfo {
    PARKING("Parking"),
    POOL("Pool"),
    RESTAURANT("Restaurant"),
    BAR("Bar"),
    PLAYROOM("Playroom"),
    GRILL("Grill");

    private String AccommodationAddInfo;

    AccommodationAddInfo(String accommodationAddInfo) {
        AccommodationAddInfo = accommodationAddInfo;
    }

    public String accommodationAddInfo() {
        return AccommodationAddInfo;
    }
}
