package vsnapuprojectsssss.vsnapuuuuuuuu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import vsnapuprojectsssss.vsnapuuuuuuuu.model.UserModel;
import vsnapuprojectsssss.vsnapuuuuuuuu.repo.Userrepo;
import vsnapuprojectsssss.vsnapuuuuuuuu.service.EmailService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/form")
public class UserController {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private EmailService emailService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitForm(@Valid @RequestBody UserModel data, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // Save the form data
        UserModel savedUser = userrepo.save(data);

        // Send notification email to owner and team
        String subject = "New Service Request Received";
        String body = "New form submitted by: " + data.getName() + "\nEmail: " + data.getEmail() +
                      "\nLocation: " + data.getLocation() + "\nMobile: " + data.getMobile() +
                      "\nCity: " + data.getCity() + "\nTime of Shoot: " + data.getTimeOfShoot();

        // Owner email
        emailService.sendNotificationEmail("sahuaman399@gmail.com", subject, body);

        // Team email (optional, uncomment and add email if needed)
        // emailService.sendNotificationEmail("team@example.com", subject, body);

        return ResponseEntity.ok("Form submitted successfully! ID: " + savedUser.getId());
    }

    @GetMapping("/all")
    public List<UserModel> getAllForms() {
        return userrepo.findAll();
    }
}



