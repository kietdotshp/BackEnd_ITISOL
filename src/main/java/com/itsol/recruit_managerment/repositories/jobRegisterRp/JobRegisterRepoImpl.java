package com.itsol.recruit_managerment.repositories.jobRegisterRp;

import com.itsol.recruit_managerment.model.*;
import com.itsol.recruit_managerment.repositories.*;
import com.itsol.recruit_managerment.repositories.jobrepo.JobRepoJpa;
import com.itsol.recruit_managerment.repositories.IUserRespository;
import com.itsol.recruit_managerment.repositories.JobsRepo;
import com.itsol.recruit_managerment.repositories.JobStatusRepo;
import com.itsol.recruit_managerment.repositories.ProfileRepo;
import com.itsol.recruit_managerment.vm.JobRegisterSearchVm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class JobRegisterRepoImpl extends JobRegisterRepoBase implements JobRegisterRepo {
    @Autowired
    IUserRespository iUserRespository;

    @Autowired
    JobRepoJpa jobRepo;




    @Autowired
    JobRegisterStatusRepo jobRegisterStatusRepo;

    @Autowired
    ProfileRepo profileRepo;

    public List<JobsRegister> search(JobRegisterSearchVm jobRegisterSearchVm) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Map<String, Object> map = new HashMap<>();
            stringBuilder.append("select * from jobs_register jr, users u, job_register_status js, jobs j, profiles p\n");
            stringBuilder.append(" WHERE jr.user_id = u.id and jr.profiles_id = p.id and jr.job_register_status_id = js.id and jr.jobs_id = j.id");
            if (jobRegisterSearchVm.getApplicantName() != null && !jobRegisterSearchVm.getApplicantName().isEmpty()) {
                stringBuilder.append(" and u.full_name = :fullName");
                map.put("fullName", jobRegisterSearchVm.getApplicantName());
            }
            if (jobRegisterSearchVm.getPositionName() != null && !jobRegisterSearchVm.getPositionName().isEmpty()) {
                stringBuilder.append(" and j.job_position = :jobPosition");
                map.put("jobPosition", jobRegisterSearchVm.getPositionName());
            }
            if (jobRegisterSearchVm.getJobRegisterStatus() != null && !jobRegisterSearchVm.getJobRegisterStatus().isEmpty()) {
                stringBuilder.append(" and js.status_name = :statusName");
                map.put("statusName", jobRegisterSearchVm.getJobRegisterStatus());
            }
            if (jobRegisterSearchVm.getApplicationTimeFrom() != null && jobRegisterSearchVm.getApplicationTimeTo() != null && !jobRegisterSearchVm.getApplicationTimeFrom().isEmpty() && !jobRegisterSearchVm.getApplicationTimeTo().isEmpty()) {
                stringBuilder.append(" and jr.application_time between to_date(:applicationTimeFrom, 'yyyy-MM-dd') and to_date(:applicationTimeTo, 'yyyy-MM-dd')");
                map.put("applicationTimeFrom", jobRegisterSearchVm.getApplicationTimeFrom());
                map.put("applicationTimeTo", jobRegisterSearchVm.getApplicationTimeTo());
            }
            return getNamedParameterJdbcTemplate().query(stringBuilder.toString(), map, new JobRegisterMapper());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    class JobRegisterMapper implements RowMapper<JobsRegister> {
        public JobsRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobsRegister dto = new JobsRegister();

            dto.setId(rs.getInt("id"));
            User user = new User();
            user.setId(rs.getLong("user_id"));
            dto.setUser(iUserRespository.findById(user.getId()).get());

            Jobs job = new Jobs();
            job.setId(rs.getLong("jobs_id"));
            dto.setJobs(jobRepo.findById(job.getId()).get());

            JobRegisterStatus jobRegisterStatus = new JobRegisterStatus();
            jobRegisterStatus.setId(rs.getInt("job_register_status_id"));
            dto.setJobRegisterStatus(jobRegisterStatusRepo.findById(jobRegisterStatus.getId()).get());

            Profiles profiles = new Profiles();
            profiles.setId(rs.getLong("profiles_id"));
            dto.setProfiles(profileRepo.findById(profiles.getId()).get());

            dto.setApplicationTime(rs.getDate("application_time"));
            dto.setCvFile(rs.getString("cv_file"));
            dto.setDelete(rs.getBoolean("is_delete"));
            return dto;
        }
    }
}
