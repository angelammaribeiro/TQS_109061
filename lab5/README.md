# Gherkin

## Overview 
Gherkin uses a set of special keywords to give structure and meaning to executable specifications

```gherkin
Feature: Guess the word

  # The first example has two steps
  Scenario: Maker starts a game
    When the Maker starts a game
    Then the Maker waits for a Breaker to join

  # The second example has three steps
  Scenario: Breaker joins a game
    Given the Maker has started a game with the word "silky"
    When the Breaker joins the Maker's game
    Then the Breaker must guess a word with 5 characters
```

The first primary keyword in a Gherkin document must always be Feature, followed by a : and a short text that describes the feature.

## Keywords 

- Feature
- Example
- Given 
- Rule 

## Free form Description

After a keyword, the discription can be freeform

# Cucumber 

## Overview 

A Step Definition is a method with an expression that links it to one or more Gherkin steps. When Cucumber executes a Gherkin step in a scenario, it will look for a matching step definition to execute.

```gherkin

Scenario: Some cukes
  Given I have 48 cukes in my belly

```

The I have 48 cukes in my belly part of the step (the text following the Given keyword) will match the following step definition:

```java 

package com.example;
import io.cucumber.java.en.Given;

public class StepDefinitions {
    @Given("I have {int} cukes in my belly")
    public void i_have_n_cukes_in_my_belly(int cukes) {
        System.out.format("Cukes: %n\n", cukes);
    }
}

```
