package com.itsol.recruit_managerment.dto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
public class companyDTO {

    int Id;
    String name;
    String email;
    String hotLine;
    Date dateIncorporation;
    String taxCode;
    String taxDate;
    String taxPlace;
    String headOffice;
    int numberStaff;
    String linkWeb;
    String description;
    String avatar;
    String backdropImg;
    boolean isDelete;
}
