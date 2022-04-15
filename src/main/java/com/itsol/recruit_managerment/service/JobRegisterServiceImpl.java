package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.config.JobRegisterConfig;
import com.itsol.recruit_managerment.constant.JobRegisterStatusConstant;
import com.itsol.recruit_managerment.dto.JobRegisterDTO;
import com.itsol.recruit_managerment.dto.ResponseDTO;
import com.itsol.recruit_managerment.model.*;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.JobRegisterStatus;
import com.itsol.recruit_managerment.model.JobsRegister;
import com.itsol.recruit_managerment.repositories.JobRegisterStatusRepo;
import com.itsol.recruit_managerment.repositories.ProfileRepo;
import com.itsol.recruit_managerment.repositories.UserRepo;
import com.itsol.recruit_managerment.repositories.jobRegisterRp.JobRegisterRepo;
import com.itsol.recruit_managerment.repositories.jobRegisterRp.JobsRegisterRepositoryJpa;
import com.itsol.recruit_managerment.repositories.jobrepo.JobRepoJpa;
import com.itsol.recruit_managerment.utils.DataUtil;
import com.itsol.recruit_managerment.vm.JobRegisterSearchVm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static com.itsol.recruit_managerment.constant.EmailConstant.CONTENT;

@Slf4j
@Service
public class JobRegisterServiceImpl implements JobRegisterService {

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    JobsRegisterRepositoryJpa jobsRegisterRepositoryJpa;

    @Autowired
    JobRegisterRepo jobRegisterRepo;

    @Autowired
    JobRegisterStatusRepo jobRegisterStatusRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    JobRepoJpa jobRepoJpa;

    @Autowired
    JobRegisterConfig jobRegisterConfig;

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    JobRegisterStatusRepo getJobRegisterStatusRepo;

    public ResponseDTO<JobsRegister> getAllJobsRegister(Integer pageNumber, Integer pageSite) {
        Pageable pageable = PageRequest.of(pageNumber, pageSite, Sort.by(Sort.Direction.ASC, "id"));

        Page<JobsRegister> jobPage = jobsRegisterRepositoryJpa.findAll(pageable);
        long totalRecord = jobPage.getTotalElements();
        List<JobsRegister> jobsRegisterList = jobPage.getContent();
        return new ResponseDTO(totalRecord, jobsRegisterList);
    }

    @Override
    public ResponseDTO<JobsRegister> search(JobRegisterSearchVm jobRegisterSearchVm, Integer pageNumber, Integer pageSite) {
        Pageable pageable = PageRequest.of(pageNumber, pageSite, Sort.by(Sort.Direction.ASC, "id"));

        Page<JobsRegister> jobPage = jobsRegisterRepositoryJpa.findAll(pageable);
        long totalRecord = jobPage.getTotalElements();
        List<JobsRegister> jobsRegister = jobRegisterRepo.search(jobRegisterSearchVm);
        return new ResponseDTO(totalRecord, jobsRegister);
    }

    @Override
    public JobsRegister getJobsRegister(int id) {
        return jobsRegisterRepositoryJpa.findById(id);
    }

    @Override
    public JobsRegister updateJobsRegister(JobRegisterDTO jobRegisterDTO) {
        try {
            JobsRegister jobsRegister = jobsRegisterRepositoryJpa.getById(jobRegisterDTO.getId());
            JobRegisterStatus jobRegisterStatus = jobRegisterStatusRepo.getById(jobRegisterDTO.getJobRegisterStatusId());
            jobsRegister.setReason(jobRegisterDTO.getReason());
            jobsRegister.setJobRegisterStatus(jobRegisterStatus);
            jobsRegisterRepositoryJpa.save(jobsRegister);
            return jobsRegister;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public byte[] downloadCv(int applicantId) throws IOException {
//        JobsRegister jobsRegister = jobsRegisterRepositoryJpa.findById(applicantId);
//        if (ObjectUtils.isEmpty(jobsRegister)) {
//            throw new NullPointerException("Could not found applicant");
//        }
//        return Files.readAllBytes(Paths.get(jobsRegister.getCvFile()));
//    }

    @Override
    public Resource downloadCv(int applicantId) throws IOException {
        JobsRegister jobsRegister = jobsRegisterRepositoryJpa.findById(applicantId);
        if (ObjectUtils.isEmpty(jobsRegister)) {
            throw new NullPointerException("Could not found applicant");
        }
        String cvFilePath = jobsRegister.getCvFile();
        Path file = Paths.get(cvFilePath);
        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("Could not read the file!");
        }
        return resource;
    }

    @Override
    public String getCvFileName(String cvFilePath) {
        if (!DataUtil.isNotNullAndEmptyString(cvFilePath)) {
            throw new NullPointerException("CV file path is null");
        }
        String[] cvFilePaths = cvFilePath.split("/");
        return cvFilePaths[cvFilePaths.length - 1];
    }

    @Override
    public boolean apply(String userId, String jobId, MultipartFile cvFile, String shortDescription) throws IOException {
        Optional<User> userOptional = userRepo.findById(Long.valueOf(userId));
        if (!userOptional.isPresent()) {
            throw new NullPointerException("Could not found user having id " + userId);
        }
        User user = userOptional.get();
//        Optional<Profiles> profileOptional = profileRepo.findByUsers(user);
        Optional<Profiles> profileOptional = Optional.ofNullable(profileRepo.findByUsers(user));
        if (!profileOptional.isPresent()) {
            throw new NullPointerException("Could not found user having id " + userId);
        }
        Profiles profiles = profileOptional.get();

        Optional<Jobs> jobOptional = jobRepoJpa.findById(Long.valueOf(jobId));
        if (!jobOptional.isPresent()) {
            throw new NullPointerException("Could not found job having id " + jobId);
        }
        Jobs job = jobOptional.get();

        Optional<JobRegisterStatus> jobRegisterStatusOptional = getJobRegisterStatusRepo.findById(JobRegisterStatusConstant.WAITING_FOR_APPROVE_STATUS);
        if (!jobRegisterStatusOptional.isPresent()) {
            throw new NullPointerException("Could not found WAITING FOR APPROVE STATUS");
        }
        JobRegisterStatus jobRegisterStatus = jobRegisterStatusOptional.get();

        String cvFilePath = handleUploadFile(cvFile);
        if (ObjectUtils.isEmpty(cvFilePath)) {
            throw new RuntimeException("Could not save CV file!");
        }
        String mimeType = Files.probeContentType(Paths.get(cvFilePath));
        JobsRegister jobsRegister = JobsRegister.builder()
                .applicationTime(new Date())
                .cvFile(cvFilePath)
                .jobRegisterStatus(jobRegisterStatus)
                .jobs(job)
                .profiles(profiles)
                .user(user)
                .cvMimetype(mimeType)
                .reason(null)
                .isDelete(false)
                .shortDescription(shortDescription)
                .methodInterview(null)
                .dateInterview(null)
                .build();
        JobsRegister savedJobRegister = jobsRegisterRepositoryJpa.save(jobsRegister);
        return !ObjectUtils.isEmpty(savedJobRegister);
    }

    private String handleUploadFile(MultipartFile cvFile) {
        try {
            Files.copy(cvFile.getInputStream(), Paths.get(jobRegisterConfig.getCvFolder()).resolve(cvFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return jobRegisterConfig.getCvFolder() + "/" + cvFile.getOriginalFilename();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

//    public JobsRegister convert(JobRegisterDTO jobRegisterDTO){
//        try{
//            JobsRegister jobsRegister = jobsRegisterRepositoryJpa.getById(jobRegisterDTO.getId());
//            System.out.println(jobsRegister);
//            if (jobRegisterDTO.getJobRegisterStatusId() != '' && !jobRegisterDTO.getApplicantName().getFullName().isEmpty()){
//                jobsRegister.getUser().setFullName(jobRegisterDTO.getApplicantName().getFullName());
//            }
////            if (jobRegisterDTO.getPositionName().getJobPosition()!= null && !jobRegisterDTO.getPositionName().getJobPosition().isEmpty()){
////                jobsRegister.getJobs().setJobPosition(jobRegisterDTO.getPositionName().getJobPosition());
////            }
////            if (jobRegisterDTO.getJobRegisterStatus().getStatusName() != null && !jobRegisterDTO.getJobRegisterStatus().getStatusName().isEmpty()){
////                jobsRegister.getJobStatus().setStatusName(jobRegisterDTO.getJobRegisterStatus().getStatusName());
////            }
////            if (jobRegisterDTO.getReason() != null && !jobRegisterDTO.getReason().isEmpty()){
////                jobsRegister.getJobStatus().setStatusName(jobRegisterDTO.getJobRegisterStatus().getStatusName());
////            }
//            return jobsRegister;
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return null;
//    }

    public Boolean sendMail(JobRegisterDTO jobRegisterDTO) {
        try {
            JobsRegister jobsRegister = jobsRegisterRepositoryJpa.getById(jobRegisterDTO.getId());
            JobRegisterStatus jobRegisterStatus = jobRegisterStatusRepo.getById(jobRegisterDTO.getJobRegisterStatusId());
            jobsRegister.setMethodInterview(jobRegisterDTO.getMethodInterview());
            jobsRegister.setJobRegisterStatus(jobRegisterStatus);
            jobsRegister.setDateInterview(jobRegisterDTO.getDateInterview());
            jobsRegisterRepositoryJpa.save(jobsRegister);
            if (jobsRegister.getJobRegisterStatus().getId() == 3) {
                sendEmail(jobsRegister);
            }
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    public void sendEmail(JobsRegister jobsRegister) {
        String Content = CONTENT;
        Content = Content.replace("X", jobsRegister.getUser().getFullName());
        Content = Content.replace("PTPV", jobsRegister.getMethodInterview());
        Content = Content.replace("ABC", jobsRegister.getJobs().getJobName());
        Content = Content.replace("DD", jobsRegister.getDateInterview().toString());
        emailService.sendSimpleMessage(jobsRegister.getUser().getEmail(),
                "Thư mời phỏng vấn",
                Content);
    }

}
