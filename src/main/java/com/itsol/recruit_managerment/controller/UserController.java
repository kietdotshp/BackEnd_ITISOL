package com.itsol.recruit_managerment.controller;


import com.itsol.recruit_managerment.constant.ConstantDateTime;
import com.itsol.recruit_managerment.dto.PasswordDTO;
import com.itsol.recruit_managerment.dto.UserSignupDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.OTP;
import com.itsol.recruit_managerment.model.Role;
import com.itsol.recruit_managerment.model.User;

import com.itsol.recruit_managerment.repositories.IUserRespository;
import com.itsol.recruit_managerment.serviceimpl.UserServiceimpl;
import com.itsol.recruit_managerment.vm.FogotPasswordVM;
import com.itsol.recruit_managerment.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserServiceimpl userService;
    @Autowired
    private UserServiceimpl userServiceimpl;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    IUserRespository iUserRespository;


    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getData();
    }

    @GetMapping("/getalluser")
    public Object getAllUSER() {
        return userService.getAllUSER();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody UserVM userVM, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            userService.validateUser(userVM);
            userService.add(userVM);
            return ResponseEntity.ok().body(userVM);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to create user");
        }

    }


    @GetMapping("/getuser/{id}")
    public User getJeById(@PathVariable("id") Long id) {
        return iUserRespository.findById(id).get();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UserVM userVM) {
        try {
            userService.validateUser(userVM);
            userService.update(userVM, id);
            return ResponseEntity.ok().body(userVM);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to update user");
        }

    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("xóa thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to update user");
        }

    }


    @GetMapping("/search/{fullName}")
    public List<User> search(@PathVariable String fullName) {
        return userService.searchName(fullName);

    }
    @GetMapping("/getuserinfo")
    public ResponseEntity getProfile(HttpServletRequest request) {
        return ResponseEntity.ok().body(userService.getUserInfo(request));

    }
    @GetMapping("/users/info/get-otp")
    public ResponseEntity<String> getOTP() {
        try {
            User user = userService.loadUserFromContext();
            OTP otp = userService.retrieveNewOTP(user);
            emailService.sendSimpleMessage(user.getUserName(), "OTP", "OTP: " + otp.getCode());
            return ResponseEntity.ok().body("check email for OTP");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/info/change-password")
    public ResponseEntity<String> changePasswordRequest(@RequestBody PasswordDTO passwordDTO) {
        User user = userService.loadUserFromContext();
        try {
            if (userService.verifyPassword(user, passwordDTO)) {
                OTP otp = userService.retrieveNewOTP(user);
                emailService.sendSimpleMessage(user.getEmail(),
                        "Change password",
                        "OTP: " + otp.getCode() + "\nNew password: " + passwordDTO.getNewPassword());
                return ResponseEntity.ok().body("Check mail for OTP to commit changing");
            } else {
                return ResponseEntity.badRequest().body("Failed changing password");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/users/info/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String otpCode, @RequestParam String password) {
        try {
            User user = userService.loadUserFromContext();
            OTP otp = userService.getOTPByUser(user);
            userService.verifyOTP(otp, otpCode);
            userService.changePassword(password, user);
            return ResponseEntity.ok().body("Password change successfull");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/fogotpass")
    public ResponseEntity<String> fogotPassword(@RequestBody FogotPasswordVM fogotPasswordVM) {
        return (ResponseEntity<String>) userService.sendFogotPasswordMail(fogotPasswordVM.getEmail());
    }
}
