package com.example.Batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.Batch.model.Coffee;

public class CoffeeItemProcessor implements ItemProcessor<Coffee, Coffee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeItemProcessor.class);

    @Override
    public Coffee process(final Coffee coffee) throws Exception {
    	int coffeeId=coffee.getCoffeeId();
        String brand = coffee.getBrand().toUpperCase();
        String origin = coffee.getOrigin().toUpperCase();
        String chracteristics = coffee.getCharacteristics().toUpperCase();

        Coffee transformedCoffee = new Coffee(coffeeId,brand, origin, chracteristics);
        LOGGER.info("Converting ( {} ) into ( {} )", coffee, transformedCoffee);

        return transformedCoffee;
    }
}
