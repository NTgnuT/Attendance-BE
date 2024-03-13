package com.rikkei.managementuser.model.dto.request;

import com.rikkei.managementuser.model.entity.Courses;
import com.rikkei.managementuser.validator.CourseExist;
import com.rikkei.managementuser.validator.ModuleCourseNameUnique;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleCourseRequest {

    @Length(max = 100, min = 6)
    @ModuleCourseNameUnique(message = "Tên module này tồn tại")
    private String moduleName;

    @Min(1)
    @NonNull
    private Integer time;

    @CourseExist(message = "Không tồn tại khóa học có mã ID này")
    private Long courseId;

}
