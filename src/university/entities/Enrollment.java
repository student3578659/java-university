package university.entities;

import university.enums.Grade;
import university.interfaces.Payable;

public class Enrollment implements Payable {
    private final int id;
    private final Student student;
    private final Course course;
    private String semester;
    private Grade grade;
    private boolean paid;

    public Enrollment(int id, Student student, Course course, String semester) {
        if (id < 1) {
            throw new IllegalArgumentException("id зарахування має бути більше 0");
        }
        if (student == null) {
            throw new IllegalArgumentException("студент не знайдений");
        }
        if (course == null) {
            throw new IllegalArgumentException("курс не знайдений");
        }
        this.id = id;
        this.student = student;
        this.course = course;
        setSemester(semester);
        this.grade = Grade.NA;
        this.paid = false;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        if (semester == null || semester.trim().isEmpty()) {
            throw new IllegalArgumentException("семестр не може бути порожнім");
        }
        this.semester = semester.trim();
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("оцінка не може бути порожньою");
        }
        this.grade = grade;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void markPaid() {
        paid = true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Enrollment enrollment = (Enrollment) object;
        return id == enrollment.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Enrollment{id=" + id + ", студент='" + student.getFullName()
                + "', курс='" + course.getTitle() + "', семестр='" + semester
                + "', оцінка=" + grade + ", оплачено=" + paid + "}";
    }
}
