package com.example.BookingApp.accommodation.model;

public enum RoomStandard {
    STANDARD("Standard"),
    FAMILY("Family"),
    PREMIUM("Premium"),
    SUPERIOR("Superior");

    private String roomType;

    RoomStandard(String roomType) {
        this.roomType = roomType;
    }

    public String roomType() {
        return roomType;
    }
}
