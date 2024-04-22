package com.example.Batch.model;

public class Coffee {
   private int coffeeId;
    private String brand;
    private String origin;
    private String characteristics;
	public int getCoffeeId() {
		return coffeeId;
	}
	public void setCoffeeId(int coffeeId) {
		this.coffeeId = coffeeId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
	public Coffee(int coffeeId, String brand, String origin, String characteristics) {
		this.coffeeId = coffeeId;
		this.brand = brand;
		this.origin = origin;
		this.characteristics = characteristics;
	}
	public Coffee() {
	}
	@Override
	public String toString() {
		return "Coffee [coffeeId=" + coffeeId + ", brand=" + brand + ", origin=" + origin + ", characteristics="
				+ characteristics + "]";
	}

   
}
