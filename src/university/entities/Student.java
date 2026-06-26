package university.entities;

import university.enums.StudentStatus;

public class Student extends Person {
    private int year;
    private StudentStatus status;

    public Student(int id, String fullName, String email, int year, StudentStatus status) {
        super(id, fullName, email);
        setYear(year);
        setStatus(status);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("рік навчання має бути більше 0");
        }
        this.year = year;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("статус студента не може бути порожнім");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() + ", рік=" + year + ", статус=" + status + "}";
    }
}
