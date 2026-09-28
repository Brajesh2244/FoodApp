package com.fooddelivery.service;

import com.fooddelivery.dto.RestaurantRequest;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(RestaurantRequest request) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setCity(request.getCity());
        restaurant.setImageUrl(request.getImageUrl());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());
        restaurant.setActive(true);

        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getActiveRestaurants() {
        return restaurantRepository.findByActiveTrue();
    }

    public Restaurant getRestaurantById(Long id) {

        return restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found with id: " + id));
    }

    public Restaurant updateRestaurant(Long id, RestaurantRequest request) {

        Restaurant restaurant = getRestaurantById(id);

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setCity(request.getCity());
        restaurant.setImageUrl(request.getImageUrl());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());

        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {

        Restaurant restaurant = getRestaurantById(id);

        restaurantRepository.delete(restaurant);
    }

    public List<Restaurant> searchByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Restaurant> getRestaurantsByCity(String city) {
        return restaurantRepository.findByCityIgnoreCase(city);
    }
}