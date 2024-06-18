package school.grade.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GradeDto {

    private Long id;

    private String studentId;

    private Long classId;

    @Size(max = 100)
    @NotNull
    private String assessment;

    @NotNull
    private Double grade;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
