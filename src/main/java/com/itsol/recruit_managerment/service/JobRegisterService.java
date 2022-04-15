package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.dto.JobRegisterDTO;
import com.itsol.recruit_managerment.dto.ResponseDTO;
import com.itsol.recruit_managerment.model.JobsRegister;
import com.itsol.recruit_managerment.vm.JobRegisterSearchVm;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JobRegisterService {
    ResponseDTO<JobsRegister> getAllJobsRegister(Integer pageNumber, Integer pageSite);

    ResponseDTO<JobsRegister> search(JobRegisterSearchVm jobRegisterSearchVm, Integer pageNumber, Integer pageSite);

    JobsRegister getJobsRegister(int id);

    JobsRegister updateJobsRegister(JobRegisterDTO jobRegisterDTO);

//    byte[] downloadCv(int applicantId) throws IOException;
    Resource downloadCv(int applicantId) throws IOException;

    String getCvFileName(String cvFilePath);


    boolean apply(String userId, String jobId, MultipartFile cvFile, String shortDescription) throws IOException;

    Boolean sendMail(JobRegisterDTO jobRegisterDTO);


}
