package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<Profile> getAllProfiles(){
        logger.info("Request received to get all profiles");
        List<Profile> profiles = profileService.getAllProfiles();
        logger.info("Number of profiles fetched: {}", profiles.size());
        return profiles;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id){
        logger.info("Request received to fetch profile with id: {}", id);
        Optional<Profile> optionalProfile = profileService.getProfileById(id);

        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            logger.info("Profile found: {}", profile);
            return ResponseEntity.ok(profile);
        } else {
            logger.warn("Profile with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile){
        logger.info("Request received to create profile: {}", profile);
        Profile createprofile = profileService.createProfile(profile);
        logger.info("Profile created with id: {}", createprofile.getId());
        return ResponseEntity.ok(createprofile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile profileDetails){
        logger.info("Request received to update profile with id: {}", id);
        try{
            Profile updateprofile = profileService.updateProfile(id, profileDetails);
            logger.info("Profile is updated: {}", updateprofile);
            return ResponseEntity.ok(updateprofile);
        }catch (RuntimeException e){
            logger.error("Error updating profile with id: {}", id,e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id){
        logger.info("Request received to delete profile with id: {}", id);
        try{
            profileService.deleteProfile(id);
            logger.info("Profile with id: {} is deleted", id);
            return ResponseEntity.ok("Profile with id" + id + "deleted successfully.");
        }catch (RuntimeException e){
            logger.error("Error deleting profile with id: {}", id,e);
            return ResponseEntity.notFound().build();
        }
    }

}
