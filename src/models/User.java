package models;

@SuppressWarnings("hiding")
public interface User<Bank> {
    public Bank getClientInfo();
    public Bank getUserId();
}
