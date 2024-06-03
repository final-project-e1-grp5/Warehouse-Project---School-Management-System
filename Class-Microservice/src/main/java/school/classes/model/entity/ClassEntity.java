package school.classes.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;





@Entity
@Table(name = "classes")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Size(max = 50)
    @NotNull
    @Column(name = "class_name")
    private String className;

    @Size(max = 50)
    @Column(name = "student_id")
    private String studentId;

    @Size(max = 50)
    @Column(name = "teacher_id")
    private String teacherId;


    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId( String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId( String teacherId) {
        this.teacherId = teacherId;
    }
}
