package com.itsol.recruit_managerment.controller;
import com.itsol.recruit_managerment.dto.JobDTO;
import com.itsol.recruit_managerment.dto.ResponseDTO;
import com.itsol.recruit_managerment.model.Jobs;
import com.itsol.recruit_managerment.service.JobsService;
import com.itsol.recruit_managerment.service.JobsServiceimpl;
import com.itsol.recruit_managerment.utils.FileUtil;
import com.itsol.recruit_managerment.vm.JobSearchVM;
import net.sf.jmimemagic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import org.springframework.util.StringUtils;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@CrossOrigin("*")
@RequestMapping("/jobs")
public class JobsController {
    @Autowired
    JobsServiceimpl jobsServiceimpl;

    public static final String DIRECTORY = System.getProperty("user.home") + "/Desktop/uploads/";

    @GetMapping("/getJob/{id}")
    @CrossOrigin
    public Jobs getJobs(@PathVariable("id") Long id) {
        return jobsServiceimpl.getFindByIdJob(id);
    }

    @GetMapping("/getAll")
    @CrossOrigin
    public ResponseEntity<List<Jobs>> getJobs() {
        return new ResponseEntity<List<Jobs>>(jobsServiceimpl.getAllJob(), HttpStatus.OK);
    }


    @GetMapping("/getAllPage")
//    @CrossOrigin
    public ResponseEntity<ResponseDTO> getAll(@RequestParam("pageNumber") int pageNumber,
                                              @RequestParam("pageSize") int pageSize) {
        ResponseDTO responseDTO = jobsServiceimpl.getAllJobPage(pageNumber, pageSize);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/file/upload")
//    @CrossOrigin
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles) throws
            IOException, MagicMatchNotFoundException, MagicException, MagicParseException {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }

    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }


    @PostMapping("/search")
    @CrossOrigin
    public List<Jobs> search(@RequestBody JobSearchVM jobSearchVM) {
        return jobsServiceimpl.search(jobSearchVM);
    }

}

