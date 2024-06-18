package school.grade.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "student_id")
    private String studentId;

    @NotNull
    @Column(name = "class_id")
    private Long classId;

    @Size(max = 100)
    @NotNull
    @Column(name = "assessment", nullable = false, length = 100)
    private String assessment;

    @NotNull
    @Column(name = "grade", nullable = false)
    private Double grade;

    public Double getGrade() {
        return grade;
    }

    public void setGrade( Double grade) {
        this.grade = grade;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}