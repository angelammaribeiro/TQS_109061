import http from "k6/http";
import { sleep, check } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export const options = {
    stages: [
        // ramp up from 0 to 20 VUs over the next 30 seconds
        { duration: '30s', target: 120 },
        // run 20 VUs over the next 30 seconds
        { duration: '30s', target: 20 },
        // ramp down from 20 to 0 VUs over the next 30 seconds
        { duration: '30s', target: 0 },
    ],
    thresholds: {
        // SLO 1: 95% of requests must complete within 1.1 seconds
        http_req_duration: ['p(95)<1100'],

        // SLO 2: Less than 1% of requests should fail
        http_req_failed: ['rate<0.01'],

        // SLO 3: Checks must pass for at least 98% of requests
        "checks": ["rate>0.98"]
    },
};

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

    // Perform checks on the response
    let checkResult = check(res, {
        // Check 1: HTTP status is 200
        'status is 200': (r) => r.status === 200,

        // Check 2: Response body size is less than 1KB (1024 bytes)
        'body size < 1KB': (r) => r.body.length < 1024,

        // Check 3: Response contains a pizza recommendation
        'response has pizza': (r) => {
            try {
                let body = r.json();
                return body.pizza && body.pizza.name && body.pizza.ingredients;
            } catch {
                return false;
            }
        },

        // Check 4: Pizza has between 2 and 6 toppings (as per initial restrictions)
        'pizza meets topping requirements': (r) => {
            try {
                let ingredients = r.json().pizza.ingredients;
                return ingredients.length >= 2 && ingredients.length <= 6;
            } catch {
                return false;
            }
        }
    });

    // Optional: Log pizza recommendation if checks pass
    if (checkResult) {
        try {
            let pizzaInfo = res.json().pizza;
            console.log(`${pizzaInfo.name} (${pizzaInfo.ingredients.length} ingredients)`);
        } catch (error) {
            console.error(`Error parsing response: ${error}`);
        }
    }

    // Add a small sleep to prevent overwhelming the system
    sleep(1);
}