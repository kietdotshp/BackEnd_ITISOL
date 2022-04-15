package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.dto.InformationUserDTO;
import com.itsol.recruit_managerment.model.AcademicLevel;
import com.itsol.recruit_managerment.model.Profiles;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.AcademicLevelRepo;
import com.itsol.recruit_managerment.repositories.ProfileRepo;
import com.itsol.recruit_managerment.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserInfomationImpl implements UserInformationService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private AcademicLevelRepo academicLevelRepo;
    @Override
    public List<User> findAllInformation() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User saveInformation(User userEntity) {

        return userRepository.save(userEntity);
    }

    @Override
    public User findByIDInformation(Long id) {

        try {
            Optional<User> optional = userRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public Profiles updateIfor(InformationUserDTO informationUserDTO) {
////        User user = userRepository.findByEmail(informationUserDTO.getEmail());
////        Profiles profiles = profileRepo.findByUsers(user);
////
////        AcademicLevel academicLevel = academicLevelRepo.findByacAdemicName(informationUserDTO.getAvatar());
////        profiles.setAcademicLevel(academicLevel);
////
////        return null;
// //   }
}
