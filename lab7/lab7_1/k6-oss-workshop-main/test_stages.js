import http from "k6/http";
import { sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

// Export test configuration with specific load stages
export const options = {
    stages: [
        // Ramp up from 0 to 20 VUs over 5 seconds
        { duration: '5s', target: 20 },
        // Maintain 20 VUs for 10 seconds
        { duration: '10s', target: 20 },
        // Ramp down from 20 to 0 VUs over 5 seconds
        { duration: '5s', target: 0 },
    ],
};

// Main test function that will be executed by each virtual user
export default function () {
    // Define pizza recommendation restrictions
    let restrictions = {
        maxCaloriesPerSlice: 500,
        mustBeVegetarian: false,
        excludedIngredients: ["pepperoni"],
        excludedTools: ["knife"],
        maxNumberOfToppings: 6,
        minNumberOfToppings: 2,
    };

    // Send POST request to pizza recommendation endpoint
    let res = http.post(`${BASE_URL}/api/pizza`, JSON.stringify(restrictions), {
        headers: {
            "Content-Type": "application/json",
            "X-User-ID": 23423,
            "Authorization": "token abcdef0123456789"
        },
    });

    // Optional: Log pizza recommendation (useful for debugging)
    console.log(`${res.json().pizza.name} (${res.json().pizza.ingredients.length} ingredients)`);

    // Add a small sleep to prevent overwhelming the system
    sleep(1);
}