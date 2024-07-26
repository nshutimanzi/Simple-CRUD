package com.example.demo.service;

import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getAllProfiles(){
        logger.info("Getting all profiles");
        List<Profile> profiles = profileRepository.findAll();
        logger.info("Number of profiles fetched: {}", profiles.size());
        return profiles;
    }

    public Optional<Profile> getProfileById(Long id){
        logger.info ("Getting profile with id: {}", id);
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isPresent()){
            logger.info("Profile found: {}", profile.get());
        } else {
            logger.warn("Profile with id: {} not found", id);
        }
        return profile;
    }

    public Profile createProfile (Profile profile){
        logger.info("Creating profile: {}", profile);
        Profile saveProfile = profileRepository.save(profile);
        logger.info("Created profile with id: {}", saveProfile.getId());
        return saveProfile;
    }

    public Profile updateProfile(Long id, Profile profileDetails) {
        logger.info("Updating profile with id: {}", id);
        Profile profile = profileRepository.findById(id).orElseThrow(() -> {
            logger.error("Profile with id: {} Not found for updating", id);
            return new RuntimeException("Profile not found");
        });
        profile.setName(profileDetails.getName());
        profile.setEmail(profileDetails.getEmail());
        profile.setPhone(profileDetails.getPhone());
        Profile updateprofile = profileRepository.save(profile);
        logger.info("Profile updated: {}", updateprofile);
        return updateprofile;
    }

    public void deleteProfile(Long id) {
        logger.info("Deleting profile with id: {}", id);
        Profile profile = profileRepository.findById(id).orElseThrow(() -> {
            logger.error("Profile with id: {} Not found for deletion", id);
            return new RuntimeException("Profile not found");
        });
        profileRepository.delete(profile);
        logger.info("Profile with id: {} has been deleted", id);
    }
}
