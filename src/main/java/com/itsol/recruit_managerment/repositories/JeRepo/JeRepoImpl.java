//package com.itsol.recruit_managerment.repositories.JeRepo;
//
//import com.itsol.recruit_managerment.model.*;
//import com.itsol.recruit_managerment.repositories.IUserRespository;
//import com.itsol.recruit_managerment.repositories.JobStatusRepo;
//import com.itsol.recruit_managerment.repositories.JobsRepo;
//import com.itsol.recruit_managerment.repositories.ProfileRepo;
//import com.itsol.recruit_managerment.vm.JobRegisterSearchVm;
//import com.itsol.recruit_managerment.vm.UserSearchVM;
//import com.itsol.recruit_managerment.vm.UserVM;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//    @Slf4j
//    public class JeRepoImpl extends JeRepoBase implements jeRepo {
//
//
//
//
//    @Autowired
//    UserSearchVM userSearchVM;
//
//    @Autowired
//    IUserRespository iUserRespository;
//    public List<User> search(UserVM userVM) {
//        try {
//            StringBuilder stringBuilder = new StringBuilder();
//            Map<String, Object> map = new HashMap<>();
//            stringBuilder.append("select * from users u\n");
//
//            if (userSearchVM.getFullName() != null && !userSearchVM.getFullName().isEmpty()) {
//                stringBuilder.append(" and u.full_name = :fullName");
//                map.put("fullName", userSearchVM.getUsername());
//            }
//            if (userSearchVM.getEmail() != null && !userSearchVM.getEmail().isEmpty()) {
//                stringBuilder.append(" and j.job_position = :jobPosition");
//                map.put("jobPosition", userSearchVM.getEmail());
//            }
//            if (userSearchVM.getPhoneNumber() != null && !userSearchVM.getPhoneNumber().isEmpty()) {
//                stringBuilder.append(" and js.status_name = :statusName");
//                map.put("statusName", userSearchVM.getPhoneNumber());
//            }
//
//            return getNamedParameterJdbcTemplate().query(stringBuilder.toString(), map, new JeMapper());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return null;
//    }
//
//    class JeMapper implements RowMapper<User> {
//        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//            User dto = new User();
//            dto.setFullName(rs.getString("fullName"));
////            dto.getUserName(rs.getString("username"));
//            dto.setEmail(rs.getString("email"));
//            dto.setPhoneNumber(rs.getString("phoneNumber"));
//            return dto;
//        }
//    }
//}
