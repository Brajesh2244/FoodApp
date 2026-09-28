package com.fooddelivery.config;

import com.fooddelivery.entity.Category;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.entity.User;
import com.fooddelivery.repository.CategoryRepository;
import com.fooddelivery.repository.FoodItemRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import java.math.BigDecimal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CategoryRepository categoryRepository,
                           RestaurantRepository restaurantRepository,
                           FoodItemRepository foodItemRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Admin user
        if (!userRepository.existsByEmail("admin@demo.com")) {
            User admin = new User("Admin User", "admin@demo.com",
                    passwordEncoder.encode("admin123"), "+1-555-0199", "742 Evergreen Terrace, Springfield", "ADMIN");
            userRepository.save(admin);
        }

        // 2. Owner user
        if (!userRepository.existsByEmail("owner@demo.com")) {
            User owner = new User("Mario Rossi", "owner@demo.com",
                    passwordEncoder.encode("owner123"), "+1-555-0144", "100 Culinary Blvd, New York", "RESTAURANT_OWNER");
            userRepository.save(owner);
        }

        // 3. Demo Customer
        if (!userRepository.existsByEmail("customer@demo.com")) {
            User customer = new User("Sarah Jenkins", "customer@demo.com",
                    passwordEncoder.encode("customer123"), "+1-555-0188", "456 Market St, San Francisco", "CUSTOMER");
            userRepository.save(customer);
        }

        // 4. Seed Categories
        List<String> categoryNames = Arrays.asList(
                "BURGER", "PIZZA", "SUSHI", "INDIAN", "CHINESE", "DESSERT", "DRINK", "ROLLS", "NOODLES", "COFFEE"
        );
        for (String name : categoryNames) {
            if (categoryRepository.findByNameIgnoreCase(name).isEmpty()) {
                Category cat = new Category();
                cat.setName(name);
                cat.setDescription(name + " dishes and delicacies");
                cat.setImageUrl("/images/categories/" + name.toLowerCase() + ".jpg");
                cat.setActive(true);
                categoryRepository.save(cat);
            }
        }

        // 5. Seed Restaurants & Menu
        // Restaurant 1: Burger Barn
        if (restaurantRepository.findByNameContainingIgnoreCase("Burger Barn").isEmpty()) {
            Restaurant r1 = new Restaurant(
                    "Burger Barn",
                    "Artisanal smash burgers, hand-spun shakes, and golden truffle fries.",
                    "123 Main Street, Chicago, IL",
                    "Chicago",
                    "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=800&q=80",
                    "+1-312-555-0123",
                    "contact@burgerbarn.com",
                    true);
            r1.setAverageRating(4.8);
            r1.setTotalReviews(284);
            r1.setOpeningTime("10:00 AM");
            r1.setClosingTime("11:00 PM");
            restaurantRepository.save(r1);

            Category burgerCat = getOrCreateCat("BURGER");
            Category drinkCat = getOrCreateCat("DRINK");

            createFood("Classic Cheeseburger", "Angus beef patty, aged cheddar, butter lettuce, secret house sauce on a brioche bun.", 9.99, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=600&q=80", false, r1, burgerCat);
            createFood("Bacon Avocado Smash", "Double beef patty, smoked bacon, fresh avocado, pepper jack cheese, chipotle mayo.", 12.49, "https://images.unsplash.com/photo-1553979459-d2229ba7433b?auto=format&fit=crop&w=600&q=80", false, r1, burgerCat);
            createFood("Crispy Truffle Fries", "Hand-cut Idaho potatoes tossed with white truffle oil, rosemary, and shaved parmesan.", 5.49, "https://images.unsplash.com/photo-1576107232684-1279f3908594?auto=format&fit=crop&w=600&q=80", true, r1, burgerCat);
            createFood("Vanilla Bean Milkshake", "Creamy Madagascar vanilla bean ice cream blended with farm-fresh milk.", 4.99, "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=600&q=80", true, r1, drinkCat);
        }

        // Restaurant 2: Pizza Haven
        if (restaurantRepository.findByNameContainingIgnoreCase("Pizza Haven").isEmpty()) {
            Restaurant r2 = new Restaurant(
                    "Pizza Haven",
                    "Authentic Neapolitan wood-fired pizzas with imported San Marzano tomatoes.",
                    "458 Little Italy Blvd, New York, NY",
                    "New York",
                    "https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=800&q=80",
                    "+1-212-555-0456",
                    "ciao@pizzahaven.com",
                    true);
            r2.setAverageRating(4.9);
            r2.setTotalReviews(342);
            r2.setOpeningTime("11:00 AM");
            r2.setClosingTime("11:30 PM");
            restaurantRepository.save(r2);

            Category pizzaCat = getOrCreateCat("PIZZA");
            Category drinkCat = getOrCreateCat("DRINK");

            createFood("Margherita DOP", "Fresh buffalo mozzarella, San Marzano tomato sauce, fresh basil, extra virgin olive oil.", 14.99, "https://images.unsplash.com/photo-1604382355076-af4b0eb60143?auto=format&fit=crop&w=600&q=80", true, r2, pizzaCat);
            createFood("Diavola Pepperoni", "Spicy Calabrian salami, mozzarella, organic honey drizzle, red chili flakes.", 16.49, "https://images.unsplash.com/photo-1628840042765-356cda07504e?auto=format&fit=crop&w=600&q=80", false, r2, pizzaCat);
            createFood("Quattro Formaggi", "Gorgonzola, fontina, mozzarella, and aged parmigiano-reggiano with roasted garlic.", 17.99, "https://images.unsplash.com/photo-1573821663912-569905455b1c?auto=format&fit=crop&w=600&q=80", true, r2, pizzaCat);
            createFood("Italian Lemon Soda", "Sparkling San Pellegrino limonata with fresh mint leaves.", 3.49, "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=600&q=80", true, r2, drinkCat);
        }

        // Restaurant 3: Sushi Spot
        if (restaurantRepository.findByNameContainingIgnoreCase("Sushi Spot").isEmpty()) {
            Restaurant r3 = new Restaurant(
                    "Sushi Spot",
                    "Premium sashimi, signature specialty rolls, and traditional Japanese bento boxes.",
                    "789 Ocean Drive, Los Angeles, CA",
                    "Los Angeles",
                    "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&w=800&q=80",
                    "+1-310-555-0789",
                    "info@sushispot.la",
                    true);
            r3.setAverageRating(4.7);
            r3.setTotalReviews(198);
            r3.setOpeningTime("12:00 PM");
            r3.setClosingTime("10:00 PM");
            restaurantRepository.save(r3);

            Category sushiCat = getOrCreateCat("SUSHI");
            Category rollsCat = getOrCreateCat("ROLLS");

            createFood("Dragon Roll", "Tempura shrimp, eel, avocado, topped with tobiko and unagi glaze.", 15.99, "https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?auto=format&fit=crop&w=600&q=80", false, r3, rollsCat);
            createFood("Spicy Tuna Roll", "Sashimi-grade tuna, spicy sriracha mayo, crisp cucumber, toasted sesame seeds.", 11.49, "https://images.unsplash.com/photo-1563245372-f21724e3856d?auto=format&fit=crop&w=600&q=80", false, r3, rollsCat);
            createFood("Salmon Nigiri Platter", "6 pieces of premium Atlantic salmon over seasoned sushi rice.", 13.99, "https://images.unsplash.com/photo-1534422298391-e4f8c172dddb?auto=format&fit=crop&w=600&q=80", false, r3, sushiCat);
            createFood("Edamame with Sea Salt", "Steamed young soybeans tossed in Himalayan pink salt.", 4.99, "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=600&q=80", true, r3, sushiCat);
        }

        // Restaurant 4: Spice Symphony
        if (restaurantRepository.findByNameContainingIgnoreCase("Spice Symphony").isEmpty()) {
            Restaurant r4 = new Restaurant(
                    "Spice Symphony",
                    "Rich North Indian curries, fragrant biryanis, and fresh butter naan.",
                    "320 Curry Hill Row, Austin, TX",
                    "Austin",
                    "https://images.unsplash.com/photo-1585937421612-70a008356fbe?auto=format&fit=crop&w=800&q=80",
                    "+1-512-555-0320",
                    "hello@spicesymphony.com",
                    true);
            r4.setAverageRating(4.9);
            r4.setTotalReviews(412);
            r4.setOpeningTime("11:30 AM");
            r4.setClosingTime("10:30 PM");
            restaurantRepository.save(r4);

            Category indianCat = getOrCreateCat("INDIAN");
            Category drinkCat = getOrCreateCat("DRINK");

            createFood("Butter Chicken Deluxe", "Tender tandoori chicken cooked in a velvety tomato and cashew butter gravy.", 14.99, "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=600&q=80", false, r4, indianCat);
            createFood("Paneer Tikka Masala", "Charcoal grilled paneer cubes in a spiced aromatic onion-tomato masala.", 13.49, "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?auto=format&fit=crop&w=600&q=80", true, r4, indianCat);
            createFood("Hyderabadi Dum Biryani", "Layered fragrant basmati rice with marinated chicken, saffron, and fried onions.", 15.99, "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?auto=format&fit=crop&w=600&q=80", false, r4, indianCat);
            createFood("Mango Lassi", "Traditional chilled yogurt smoothie with sweet Alphonso mango pulp.", 4.49, "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=600&q=80", true, r4, drinkCat);
        }

        // Restaurant 5: Dragon Wok
        if (restaurantRepository.findByNameContainingIgnoreCase("Dragon Wok").isEmpty()) {
            Restaurant r5 = new Restaurant(
                    "Dragon Wok",
                    "Sizzling wok stir-fries, handmade soup dumplings, and crispy spring rolls.",
                    "604 Chinatown Gate, Seattle, WA",
                    "Seattle",
                    "https://images.unsplash.com/photo-1563245372-f21724e3856d?auto=format&fit=crop&w=800&q=80",
                    "+1-206-555-0604",
                    "orders@dragonwok.com",
                    true);
            r5.setAverageRating(4.6);
            r5.setTotalReviews(175);
            r5.setOpeningTime("11:00 AM");
            r5.setClosingTime("10:00 PM");
            restaurantRepository.save(r5);

            Category chineseCat = getOrCreateCat("CHINESE");
            Category noodleCat = getOrCreateCat("NOODLES");

            createFood("Kung Pao Chicken", "Wok-seared diced chicken with peanuts, bell peppers, and Sichuan chili peppers.", 13.99, "https://images.unsplash.com/photo-1525755662778-989d0524087e?auto=format&fit=crop&w=600&q=80", false, r5, chineseCat);
            createFood("Veg Hakka Noodles", "Stir-fried wheat noodles with crunchy julienned vegetables and light soy sauce.", 10.99, "https://images.unsplash.com/photo-1585032226651-759b368d7246?auto=format&fit=crop&w=600&q=80", true, r5, noodleCat);
            createFood("Steamed Pork Dumplings", "6 handmade dim sum dumplings served with ginger soy dipping sauce.", 8.99, "https://images.unsplash.com/photo-1541696432-82c6da8ce7bf?auto=format&fit=crop&w=600&q=80", false, r5, chineseCat);
        }

        // Restaurant 6: Sweet Tooth Cafe
        if (restaurantRepository.findByNameContainingIgnoreCase("Sweet Tooth Cafe").isEmpty()) {
            Restaurant r6 = new Restaurant(
                    "Sweet Tooth Cafe",
                    "Artisan French pastries, layered decadence cakes, and freshly brewed coffees.",
                    "88 Baker Street, Boston, MA",
                    "Boston",
                    "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80",
                    "+1-617-555-0088",
                    "treats@sweettoothcafe.com",
                    true);
            r6.setAverageRating(4.8);
            r6.setTotalReviews(220);
            r6.setOpeningTime("08:00 AM");
            r6.setClosingTime("09:00 PM");
            restaurantRepository.save(r6);

            Category dessertCat = getOrCreateCat("DESSERT");
            Category coffeeCat = getOrCreateCat("COFFEE");

            createFood("Belgian Chocolate Fudge Cake", "Triple layered moist chocolate cake with warm ganache center.", 6.99, "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=600&q=80", true, r6, dessertCat);
            createFood("New York Cheesecake", "Creamy classic baked cheesecake with fresh strawberry coulis.", 6.49, "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?auto=format&fit=crop&w=600&q=80", true, r6, dessertCat);
            createFood("Iced Caramel Macchiato", "Espresso layered with whole milk, vanilla syrup, and buttery caramel drizzle.", 4.99, "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?auto=format&fit=crop&w=600&q=80", true, r6, coffeeCat);
        }
    }

    private Category getOrCreateCat(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Category cat = new Category();
                    cat.setName(name);
                    cat.setDescription(name + " category");
                    cat.setActive(true);
                    return categoryRepository.save(cat);
                });
    }

    private void createFood(String name, String description, double price, String imageUrl, boolean veg, Restaurant restaurant, Category category) {
        FoodItem item = new FoodItem();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(BigDecimal.valueOf(price));
        item.setImageUrl(imageUrl);
        item.setVegetarian(veg);
        item.setAvailable(true);
        item.setRestaurant(restaurant);
        item.setCategory(category);
        foodItemRepository.save(item);
    }
}

