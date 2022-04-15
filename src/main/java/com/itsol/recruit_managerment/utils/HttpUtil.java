package com.itsol.recruit_managerment.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpUtil {

    public static HttpHeaders createHeaderForDownloadFile(String fileName, String mimeType) {
        HttpHeaders head = new HttpHeaders();
        head.setContentType(MediaType.parseMediaType(mimeType));
        head.add("content-disposition", "attachment; filename=" + fileName);
        head.setContentDispositionFormData(fileName, fileName);
        head.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return head;
    }

}
