package org.example.dockerfile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller.
 *
 * @author Yauheni Halaidzin
 * @since 18.07.2024
 */
@RestController
public class Controller {

    private String slogan;

    @PostMapping(path = "slogan", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createSlogan(@RequestBody String slogan) {
        this.slogan = slogan;

        return ResponseEntity.ok("Slogan was created");
    }

    @GetMapping(path = "get", produces = "application/json")
    public ResponseEntity<String> getSlogan() {
        return ResponseEntity.ok(slogan);
    }

}
