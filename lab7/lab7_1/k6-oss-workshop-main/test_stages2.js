import http from "k6/http";
import { sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export const options = {
    stages: [
        // ramp up from 0 to 20 VUs over the next 30 seconds
        { duration: '30s', target: 20 },
        // run 20 VUs over the next 30 seconds
        { duration: '30s', target: 20 },
        // ramp down from 20 to 0 VUs over the next 30 seconds
        { duration: '30s', target: 0 },
    ],
    thresholds: {

    },
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
    try {
        console.log(`${res.json().pizza.name} (${res.json().pizza.ingredients.length} ingredients)`);
    } catch (error) {
        console.error(`Error parsing response: ${error}`);
    }

    // Add a small sleep to prevent overwhelming the system
    sleep(1);
}