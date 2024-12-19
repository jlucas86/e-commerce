package com.example;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController()
public class Main {

    


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greeting")
    public GreetResponse greet() {
        return new GreetResponse("sup pussy", List.of("java", "python", "C++"), new Person("james", 28, 30000));

    }

    /**
     * Person
     */
    public record Person(String name, int age, double savings) {
    }

    /**
     * GreetResponse
     */
    record GreetResponse(String greet, List<String> favProgramingLanguages, Person person) {
    }

}
