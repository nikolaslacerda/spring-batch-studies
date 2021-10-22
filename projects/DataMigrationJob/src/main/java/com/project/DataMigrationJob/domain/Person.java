package com.project.DataMigrationJob.domain;

import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@Builder
public class Person {

    @Id
    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private Date birthday;

    public boolean isValid() {
        return !Strings.isBlank(name) && !Strings.isBlank(email) && birthday != null;
    }
}
