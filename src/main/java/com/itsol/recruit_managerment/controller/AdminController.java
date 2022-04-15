package com.itsol.recruit_managerment.controller;

import com.itsol.recruit_managerment.config.AccountActivationConfig;
import com.itsol.recruit_managerment.constant.ConstantDateTime;

import com.itsol.recruit_managerment.dto.AuthenDTO;
import com.itsol.recruit_managerment.dto.Metadata;
import com.itsol.recruit_managerment.dto.CustomResponseDto;

import com.itsol.recruit_managerment.dto.UserSignupDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.OTP;
import com.itsol.recruit_managerment.model.Role;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.RoleRepo;
import com.itsol.recruit_managerment.security.AuthorFilter;
import com.itsol.recruit_managerment.service.AdminService;
import com.itsol.recruit_managerment.service.UserService;



import com.itsol.recruit_managerment.serviceimpl.UserServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")


@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserServiceimpl userServiceimpl;
    @Autowired
    UserService userService;
    @Autowired
    AccountActivationConfig accountActivationConfig;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    AdminService adminService;



    @PostMapping("/signupje")
    public ResponseEntity<String> singupje(@RequestBody UserSignupDTO userSignupDTO) {
        Role role = roleRepo.findByName("ROLE_JE");
        User user = userServiceimpl.createUser(userSignupDTO);
        if (ObjectUtils.isEmpty(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);



        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        SimpleDateFormat sdf = new SimpleDateFormat(ConstantDateTime.YYYYMMDD_FOMART);

        try {
            user.setBirthDay(sdf.parse(userSignupDTO.getBirthDay()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        user.setActive(false);
        user.setIsDelete(0);
        userService.saveUser(user);
        OTP otp = userService.generateOTP(user);
        String linkActive = accountActivationConfig.getActivateUrl() + user.getId();

        emailService.sendSimpleMessage(user.getEmail(), "Link active account", "<a href=\" " + linkActive + "\">Click vào đây để kích hoạt tài khoản</a>");
        return ResponseEntity.ok().body("check email for OTP");
    }


    @GetMapping("/active/{id}")
    public ResponseEntity<String> activeAccount(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user.isActive()) {
                return ResponseEntity.ok().body("Your account is already activated");
            }
            userService.activeAccount(user);
            return ResponseEntity.ok().body("Active successfull");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/getallje")
    public Object getAllJe() {
        return userService.getAllJE();
    }

    @GetMapping("/getje/{id}")
    public User getJeById(@PathVariable("id") Long id) {
        return adminService.findById(id).get();
    }

    @PutMapping("/updateJE/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UserSignupDTO userSignupDTO) {
        try {
            adminService.update(userSignupDTO, id);
            return ResponseEntity.ok().body(userSignupDTO);
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to update user");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            adminService.delete(id);
            return ResponseEntity.ok().body("xóa thành công");
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to update user");
        }
    }


    @PostMapping ("/all")
    @ResponseBody
    public ResponseEntity<CustomResponseDto<Page<User>>> findAll(
            @RequestParam(value = "p", defaultValue = "0") int page,
            @RequestParam(value = "s", defaultValue = "10") int size,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "fields", required = false) Set<String> fields) {


        CustomResponseDto<Page<User>> customResponseDto = new CustomResponseDto<>();


        Pageable pageable = PageRequest.of(page, size);
        Page<User> p = userService.getFullnameList(pageable, userName, fullName, phoneNumber, email);


        customResponseDto.setData(p);
        customResponseDto.setMetadata(new Metadata(p.getSize(), p.getTotalElements(), p.getTotalPages()));
        return new ResponseEntity<>(customResponseDto, HttpStatus.OK);
    }

}
