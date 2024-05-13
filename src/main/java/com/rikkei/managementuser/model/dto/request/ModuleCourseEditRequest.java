package com.rikkei.managementuser.model.dto.request;

import com.rikkei.managementuser.validator.CourseExist;
import com.rikkei.managementuser.validator.ModuleCourseNameUnique;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ModuleCourseEditRequest {

    @Length(max = 100, min = 6)
    private String moduleName;

    @Min(1)
    @NonNull
    private Integer lesson;

    @CourseExist(message = "Không tồn tại khóa học có mã ID này")
    private Long courseId;
}
