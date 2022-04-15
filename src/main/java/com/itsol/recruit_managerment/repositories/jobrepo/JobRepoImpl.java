package com.itsol.recruit_managerment.repositories.jobrepo;

import com.itsol.recruit_managerment.model.*;
import com.itsol.recruit_managerment.repositories.*;
import com.itsol.recruit_managerment.vm.JobSearchVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class JobRepoImpl extends JobRepoBase implements JobRepo {
    @Autowired
    IUserRespository iUserRespository;

//    @Autowired
//    JobRepo jobRepo;

    @Autowired
    AcademicLevelRepo academicLevelRepo;

    @Autowired
    JobStatusRepo jobStatusRepo;

    @Autowired
    LevelRankRepo levelRankRepo;

    @Autowired
    MethodWorkRepo methodWorkRepo;


    @Override
    public List<Jobs> search(JobSearchVM jobSearchVM) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Map<String, Object> map = new HashMap<>();
//            MapSqlParameterSource map = new MapSqlParameterSource();
            stringBuilder.append("select * \n" +
                    "from jobs j\n" +
                    "left join job_status js on js.id = j.job_status_id\n" +
                    "left join academic_level  a on j.academic_level_id=a.id \n" +
                    "left join level_rank  l on j.level_rank_id=l.id \n" +
                    "left join method_word  m on j.method_work_id=m.id \n" +
                    "where 1=1\n" +
                    "and js.id = 1\n" +
                    "and j.due_date >= sysdate\n" +
                    "and j.is_delete = 0\n");
            if (jobSearchVM.getSearchName() != null && !jobSearchVM.getSearchName().isEmpty()) {
                stringBuilder.append(" and j.job_name =:jobname ");
                map.put("jobname", jobSearchVM.getSearchName());
            }
            if (jobSearchVM.getPositionName() != null && !jobSearchVM.getPositionName().isEmpty()) {
                stringBuilder.append(" and j.job_position =:jobposition ");
                map.put("jobposition", jobSearchVM.getPositionName());
            }
            if (jobSearchVM.getApplicationTimeFrom() != null && jobSearchVM.getApplicationTimeTo() != null && !jobSearchVM.getApplicationTimeFrom().isEmpty() && !jobSearchVM.getApplicationTimeTo().isEmpty()) {
//                stringBuilder.append(" and j.create_date >=:todate and j.due_date <=:fromdate ");
                stringBuilder.append(" and j.due_date >= to_date(:fromdate, 'YYYY-MM-DD') and j.due_date <= to_date(:todate, 'YYYY-MM-DD') ");
//                " and jr.application_time between to_date(:applicationTimeFrom, 'yyyy-MM-dd') and to_date(:applicationTimeTo, 'yyyy-MM-dd')")
                map.put("todate", jobSearchVM.getApplicationTimeTo());
                map.put("fromdate", jobSearchVM.getApplicationTimeFrom());

            }
            if (jobSearchVM.getMinSalary() != 0 && jobSearchVM.getMaxSalary() != 0) {
                stringBuilder.append(" and j.max_salary<=:max and j.min_salary>=:min");
                map.put("min", jobSearchVM.getMinSalary());
                map.put("max", jobSearchVM.getMaxSalary());

            }
            return getNamedParameterJdbcTemplate().query(stringBuilder.toString(),map,new JobMapper());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    class JobMapper implements RowMapper<Jobs> {
        public Jobs mapRow(ResultSet rs, int rowNum) throws SQLException {
            Jobs dto = new Jobs();

            dto.setId(rs.getLong("id"));
            JobStatus jobStatus = new JobStatus();
            jobStatus.setId(rs.getLong("job_status_id"));
            dto.setJobStatus(jobStatusRepo.findById(jobStatus.getId()).get());

            MethodWork methodWork = new MethodWork();
            methodWork.setId(rs.getLong("method_work_id"));
            dto.setMethodWork(methodWorkRepo.findById(methodWork.getId()).get());

            AcademicLevel academicLevel = new AcademicLevel();
            academicLevel.setId(rs.getLong("academic_level_id"));
            dto.setAcademicLevel(academicLevelRepo.findById(academicLevel.getId()).get());

            LevelRank levelRank = new LevelRank();
            levelRank.setId(rs.getLong("level_rank_id"));
            dto.setLevelRank(levelRankRepo.findById(levelRank.getId()).get());

            User user = new User();
            user.setId(rs.getLong("create_id"));
            dto.setCreateId(iUserRespository.findById(user.getId()).get());
            user.setId(rs.getLong("contact_id"));
            dto.setContactId(iUserRespository.findById(user.getId()).get());

            dto.setJobName(rs.getString("job_name"));
            dto.setJobPosition(rs.getString("job_position"));
            dto.setNumberExperience(rs.getString("number_experience"));
            dto.setAddressWork(rs.getString("address_work"));
            dto.setQuantityPerson(rs.getInt("quantity_person"));
            dto.setCreateDate(rs.getDate("create_date"));
            dto.setDueDate(rs.getDate("due_date"));
            dto.setSkills(rs.getString("skills"));
            dto.setDescription(rs.getString("description"));
            dto.setInterest(rs.getString("interrest"));
            dto.setMinSalary(rs.getFloat("min_salary"));
            dto.setMaxSalary(rs.getFloat("max_salary"));
            dto.setView(rs.getInt("views"));
            dto.setDelete(rs.getBoolean("is_delete"));
            return dto;

        }
    }

}
