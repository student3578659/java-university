package university.services;

import university.entities.Course;
import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;
import university.util.GPAUtils;

public class EnrollmentService {
    private Enrollment[] enrollments = new Enrollment[10];
    private int count = 0;
    private int nextId = 1;

    public Enrollment addEnrollment(Student student, Course course, String semester) {
        growIfNeeded();
        Enrollment enrollment = new Enrollment(nextId, student, course, semester);
        enrollments[count] = enrollment;
        count++;
        nextId++;
        return enrollment;
    }

    public void setGrade(int enrollmentId, Grade grade) {
        findById(enrollmentId).setGrade(grade);
    }

    public void markPaid(int enrollmentId) {
        findById(enrollmentId).markPaid();
    }

    public Enrollment findById(int id) {
        return enrollments[findIndexById(id)];
    }

    public Enrollment[] getAll() {
        Enrollment[] result = new Enrollment[count];
        for (int i = 0; i < count; i++) {
            result[i] = enrollments[i];
        }
        return result;
    }

    public Enrollment[] getByStudent(int studentId) {
        Enrollment[] result = new Enrollment[count];
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == studentId) {
                result[resultCount] = enrollments[i];
                resultCount++;
            }
        }

        return trim(result, resultCount);
    }

    public Enrollment[] getUnpaid() {
        Enrollment[] result = new Enrollment[count];
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (!enrollments[i].isPaid()) {
                result[resultCount] = enrollments[i];
                resultCount++;
            }
        }

        return trim(result, resultCount);
    }

    public double getStudentGpa(int studentId) {
        Enrollment[] studentEnrollments = getByStudent(studentId);
        return GPAUtils.calculateGpa(studentEnrollments, studentEnrollments.length);
    }

    public double getAverageGpaByCourseAndSemester(int courseId, String semester) {
        Enrollment[] result = new Enrollment[count];
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            boolean sameCourse = enrollments[i].getCourse().getId() == courseId;
            boolean sameSemester = enrollments[i].getSemester().equalsIgnoreCase(semester);
            if (sameCourse && sameSemester) {
                result[resultCount] = enrollments[i];
                resultCount++;
            }
        }

        return GPAUtils.calculateGpa(result, resultCount);
    }

    public int getCount() {
        return count;
    }

    private int findIndexById(int id) {
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getId() == id) {
                return i;
            }
        }

        throw new IllegalArgumentException("зарахування з id " + id + " не знайдено");
    }

    private Enrollment[] trim(Enrollment[] source, int size) {
        Enrollment[] result = new Enrollment[size];
        for (int i = 0; i < size; i++) {
            result[i] = source[i];
        }
        return result;
    }

    private void growIfNeeded() {
        if (count < enrollments.length) {
            return;
        }

        Enrollment[] newEnrollments = new Enrollment[enrollments.length * 2];
        for (int i = 0; i < enrollments.length; i++) {
            newEnrollments[i] = enrollments[i];
        }
        enrollments = newEnrollments;
    }
}
