package com.springdata.demo.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String streetaddress;
	private String city;
	private String country;

	public String getStreetaddress() {
		return streetaddress;
	}

	public void setStreetaddress(String streetaddress) {
		this.streetaddress = streetaddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [streetaddress=" + streetaddress + ", city=" + city + ", country=" + country + "]";
	}
}
