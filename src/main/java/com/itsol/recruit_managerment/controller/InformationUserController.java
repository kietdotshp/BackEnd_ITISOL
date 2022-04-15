package com.itsol.recruit_managerment.controller;

import com.itsol.recruit_managerment.GennericResponse.GenericResponse;
import com.itsol.recruit_managerment.dto.InformationUserDTO;
import com.itsol.recruit_managerment.model.AcademicLevel;
import com.itsol.recruit_managerment.model.DesiredWork;
import com.itsol.recruit_managerment.model.Profiles;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.request.InformationUserRequest;
import com.itsol.recruit_managerment.service.AcademicLevelService;
import com.itsol.recruit_managerment.service.DesireWorkService;
import com.itsol.recruit_managerment.service.ProfileService;
import com.itsol.recruit_managerment.service.UserInformationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/pesonalInfomation")
public class InformationUserController {
    private static Logger logger = LogManager.getLogger(InformationUserController.class);
    @Autowired
    private DesireWorkService desiredWorkService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AcademicLevelService academic_levelService;

    @CrossOrigin("*")
    @GetMapping("/getInformationUserById/{id}")
    public ResponseEntity<?> getInformationUserById(@PathVariable("id") Long userId) {
        try {
            Profiles profileEntity = profileService.findByUserID(userId);
            GenericResponse<Object> response = new GenericResponse<>(new Date(), HttpStatus.OK,
                    "Thành công", profileEntity);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Có lỗi xảy ra, vui lòng kiểm tra lại");
        }
    }

    @CrossOrigin("*")
    @PutMapping("/updateInformationUserById/{id}")
    public ResponseEntity<?> getInformationUserById(@PathVariable("id") Long userId, @Valid @RequestBody InformationUserRequest informationUserRequest) {
        try {


            User userFind = userInformationService.findByIDInformation(userId);
            if (userFind == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy thông tin user với id=" + userId);

            InformationUserRequest result = new InformationUserRequest();

            Profiles profileEntity = profileService.findByUserID(userId);
            if (profileEntity == null)
                profileEntity = new Profiles();


            result.setId(profileEntity.getId());
            if (informationUserRequest.getDelete() != null)
                profileEntity.setDelete(informationUserRequest.getDelete());
            if (informationUserRequest.getDesiredSalary() != null)
                profileEntity.setDesiredSalary(informationUserRequest.getDesiredSalary());
            if (informationUserRequest.getSkill() != null)
                profileEntity.setSkill(informationUserRequest.getSkill().trim());
            if (informationUserRequest.getDesiredWorkingAddress() != null)
                profileEntity.setDesiredWorkingAddress(informationUserRequest.getDesiredWorkingAddress().trim());


            if (informationUserRequest.getUsers() != null) {
                if (informationUserRequest.getUsers().getAvatar() != null)
                    userFind.setAvatar(informationUserRequest.getUsers().getAvatar().trim());
//                if (informationUserRequest.getUsers().getIsDelete() != null)
                userFind.setIsDelete(informationUserRequest.getUsers().getIsDelete());
                if (informationUserRequest.getUsers().getBirthDay() != null)
                    userFind.setBirthDay(informationUserRequest.getUsers().getBirthDay());
                if (informationUserRequest.getUsers().getFullName() != null)
                    userFind.setFullName(informationUserRequest.getUsers().getFullName().trim());
                if (informationUserRequest.getUsers().getPhoneNumber() != null)
                    userFind.setPhoneNumber(informationUserRequest.getUsers().getPhoneNumber());
                if (informationUserRequest.getUsers().getUserName() != null)
                    userFind.setUserName(informationUserRequest.getUsers().getUserName().trim());
                if (informationUserRequest.getUsers().getEmail() != null)
                    userFind.setEmail(informationUserRequest.getUsers().getEmail().trim());
                if (informationUserRequest.getUsers().getGender() != null)
                    userFind.setGender(informationUserRequest.getUsers().getGender().trim());
                if (informationUserRequest.getUsers().getHomeTown() != null)
                    userFind.setHomeTown(informationUserRequest.getUsers().getHomeTown().trim());
            }


            DesiredWork desiredwork = new DesiredWork();
            if (profileEntity.getDesiredwork() != null) {
                desiredwork = desiredWorkService.findById(profileEntity.getDesiredwork().getId());
            }
            desiredwork.setDesiredworkname(informationUserRequest.getDesiredworkname());
            if (desiredwork == null)
                desiredwork = new DesiredWork();
            if (informationUserRequest.getDesiredwork() != null) {
                if (informationUserRequest.getDesiredwork().getDesiredworkname() != null)
                    desiredwork.setDesiredworkname(informationUserRequest.getDesiredwork().getDesiredworkname().trim());
                if (informationUserRequest.getDesiredwork().getDescription() != null)
                    desiredwork.setDescription(informationUserRequest.getDesiredwork().getDescription().trim());
            }
            userFind.setFullName(informationUserRequest.getFullName());
            userFind.setEmail(informationUserRequest.getEmail());
            userFind.setHomeTown(informationUserRequest.getHomeTown());
            userFind.setGender(informationUserRequest.getGender());
            userFind.setBirthDay(informationUserRequest.getBirthDay());
            userFind.setPhoneNumber(informationUserRequest.getPhoneNumber());

            AcademicLevel academicLevel = new AcademicLevel();
            if (profileEntity.getAcademicLevel() != null) {
                academicLevel = academic_levelService.findById(profileEntity.getAcademicLevel().getId());
            }
            academicLevel.setAcademicName(informationUserRequest.getAcademicName());

            if (academicLevel == null)
                academicLevel = new AcademicLevel();

            if (informationUserRequest.getAcademicLevel() != null) {
                if (informationUserRequest.getAcademicLevel().getAcademicName() != null)
                    academicLevel.setAcademicName(informationUserRequest.getAcademicLevel().getAcademicName().trim());
                if (informationUserRequest.getAcademicLevel().getDescription() != null)
                    academicLevel.setDescription(informationUserRequest.getAcademicLevel().getDescription().trim());
            }

            desiredwork = desiredWorkService.save(desiredwork);
            academicLevel = academic_levelService.save(academicLevel);
            userFind = userInformationService.saveInformation(userFind);
            profileEntity.setUsers(userFind);
            profileEntity.setAcademicLevel(academicLevel);
            profileEntity.setDesiredwork(desiredwork);
            profileEntity = profileService.save(profileEntity);
            GenericResponse<Object> response = new GenericResponse<>(new Date(), HttpStatus.OK,
                    "Thành công", profileEntity);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Có lỗi xảy ra, vui lòng kiểm tra lại");
        }
    }
}