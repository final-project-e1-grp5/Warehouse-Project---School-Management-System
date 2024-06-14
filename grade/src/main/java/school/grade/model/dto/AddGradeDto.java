package school.grade.model.dto;

import jakarta.validation.constraints.*;

public class AddGradeDto {

    @Min(value =1, message = "Invalid studentId: Student id should be greater than zero")
    private int studentId;

    @NotBlank(message = "Grade should not be empty")
    @NotNull(message = "Invalid Grade: Grade is Null")
    @Size(max=3, message = "Invalid Grade: Must be 3 characters maximum")
    //@Pattern(regexp = "[A-Za-z]+", message = "Grade must a string value")
    private String grade;

    @Min(value =1, message = "Invalid classId: Class id should be greater than zero")
    private int classId;

    @Min(value =1, message = "Invalid studentId: Student id should be greater than zero")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(@Min(value =1, message = "Invalid studentId: Student id should be greater than zero") int studentId) {
        this.studentId = studentId;
    }

    public @NotBlank(message = "Grade should not be empty") @NotNull(message = "Invalid Grade: Grade is Null") @Size(max = 3, message = "Invalid Grade: Must be 3 characters maximum") String getGrade() {
        return grade;
    }

    public void setGrade(@NotBlank(message = "Grade should not be empty") @NotNull(message = "Invalid Grade: Grade is Null") @Size(max = 3, message = "Invalid Grade: Must be 3 characters maximum") String grade) {
        this.grade = grade;
    }

    @Min(value = 1, message = "Invalid classId: Class id should be greater than zero")
    public int getClassId() {
        return classId;
    }

    public void setClassId(@Min(value = 1, message = "Invalid classId: Class id should be greater than zero") int classId) {
        this.classId = classId;
    }
}
