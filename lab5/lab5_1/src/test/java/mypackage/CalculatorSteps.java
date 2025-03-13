package mypackage;

import static java.lang.System.getLogger;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.slf4j.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.slf4j.LoggerFactory.getLogger;
import org.example.Calculator;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;

    @Given("^a calculator I just turned on$")
    public void setup() {
        calc = new Calculator();
    }

    //    @When("^I add (\\d+) and (\\d+)$")
    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    //    @When("^I substract (\\d+) to (\\d+)$")
    @When("I substract {int} to {int}")
    public void substract(int arg1, int arg2) {
        log.debug("Substracting {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    //    @Then("^the result is (\\d+)$")
    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }

    @When("I multiply {int} for {int}")
    public void iMultiplyFor(int arg0, int arg1) {
        log.debug("Multiplying {} for {}", arg0, arg1);
        calc.push(arg0);
        calc.push(arg1);
        calc.push("*");
    }
}