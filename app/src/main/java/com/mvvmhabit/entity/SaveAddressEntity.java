package com.mvvmhabit.entity;

public class SaveAddressEntity {
    private AddressEntity address;
    private String error;

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
