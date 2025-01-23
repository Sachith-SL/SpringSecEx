package com.sachith.SpringSecEx.service;

import com.sachith.SpringSecEx.dto.ReqRes;
import com.sachith.SpringSecEx.model.OurUser;
import com.sachith.SpringSecEx.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {

    @Autowired
    private OurUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReqRes register(ReqRes request) {
        ReqRes response = new ReqRes();
        try {
            OurUser ourUser = new OurUser();
            ourUser.setEmail(request.getEmail());
            ourUser.setCity(request.getCity());
            ourUser.setRole(request.getRole());
            ourUser.setName(request.getName());
            ourUser.setPassword(passwordEncoder.encode(request.getPassword()));
            OurUser ourUserResult = userRepository.save(ourUser);

            if (ourUserResult.getId() > 0) {
                response.setOurUser(ourUserResult);
                response.setMessage("User Saved Successfully");
                response.setStatusCode(200);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());

        }

        return response;
    }

    public ReqRes login(ReqRes request) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
            response.setRole(user.getRole());
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully logged in");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes request) {
        ReqRes response = new ReqRes();
        try {
            String ourEmail = jwtService.extractUserName(request.getToken());
            OurUser user = userRepository.findByEmail(ourEmail).orElseThrow();
            if (jwtService.isTokenValid(request.getToken(), user)) {
                var jwt = jwtService.generateToken(user);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(request.getToken());
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully Refreshed token");
            }
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes getAllUsers() {
        ReqRes response = new ReqRes();
        try {
            List<OurUser> result = userRepository.findAll();
            if (!result.isEmpty()) {
                response.setOurUserList(result);
                response.setStatusCode(200);
                response.setMessage("Successful");
            } else {
                response.setStatusCode(404);
                response.setMessage("No users found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes getUserById(Integer id) {
        ReqRes response = new ReqRes();
        try {
            OurUser userById = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            response.setOurUser(userById);
            response.setStatusCode(200);
            response.setMessage("User with id " + id + "found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Error occurred: " + response.getMessage());
        }
        return response;
    }

    public ReqRes deleteUser(Integer id) {
        ReqRes response = new ReqRes();
        try {
            Optional<OurUser> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userRepository.deleteById(id);
                response.setStatusCode(200);
                response.setMessage("User deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Error occurred while deleting user: " + response.getMessage());
        }
        return response;
    }

    public ReqRes updateUser(Integer id, OurUser updatedUser) {
        ReqRes response = new ReqRes();
        try {
            Optional<OurUser> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                OurUser existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                if(updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()){
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUser savedUser = userRepository.save(existingUser);
                response.setOurUser(savedUser);
                response.setStatusCode(200);
                response.setMessage("User updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found for update");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Error occurred while updating user: " + response.getMessage());
        }
        return response;
    }

    public ReqRes getMyInfo(String email){
        ReqRes response = new ReqRes();
        try {
            Optional<OurUser> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                response.setOurUser(userOptional.get());
                response.setStatusCode(200);
                response.setMessage("successful");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Error occurred while getting user info: " + response.getMessage());
        }
        return response;
    }

}
