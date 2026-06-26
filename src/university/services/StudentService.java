package university.services;

import university.entities.Student;
import university.enums.StudentStatus;

public class StudentService {
    private Student[] students = new Student[10];
    private int count = 0;
    private int nextId = 1;

    public Student addStudent(String fullName, String email, int year, StudentStatus status) {
        growIfNeeded();
        Student student = new Student(nextId, fullName, email, year, status);
        students[count] = student;
        count++;
        nextId++;
        return student;
    }

    public void updateStudent(int id, String fullName, String email, int year) {
        Student student = findById(id);
        student.setFullName(fullName);
        student.setEmail(email);
        student.setYear(year);
    }

    public void deleteStudent(int id) {
        int index = findIndexById(id);

        for (int i = index; i < count - 1; i++) {
            students[i] = students[i + 1];
        }

        students[count - 1] = null;
        count--;
    }

    public void changeStatus(int id, StudentStatus status) {
        findById(id).setStatus(status);
    }

    public Student findById(int id) {
        int index = findIndexById(id);
        return students[index];
    }

    public Student[] getAll() {
        Student[] result = new Student[count];
        for (int i = 0; i < count; i++) {
            result[i] = students[i];
        }
        return result;
    }

    public Student[] filterByStatus(StudentStatus status) {
        Student[] result = new Student[count];
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (students[i].getStatus() == status) {
                result[resultCount] = students[i];
                resultCount++;
            }
        }

        return trim(result, resultCount);
    }

    public Student[] filterByYear(int year) {
        Student[] result = new Student[count];
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (students[i].getYear() == year) {
                result[resultCount] = students[i];
                resultCount++;
            }
        }

        return trim(result, resultCount);
    }

    public Student[] sortByName() {
        Student[] result = getAll();

        for (int i = 0; i < result.length - 1; i++) {
            for (int j = 0; j < result.length - i - 1; j++) {
                if (result[j].getFullName().compareToIgnoreCase(result[j + 1].getFullName()) > 0) {
                    Student temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }

        return result;
    }

    public Student[] searchByNameOrEmail(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("рядок пошуку не може бути порожнім");
        }

        Student[] result = new Student[count];
        int resultCount = 0;
        String lowerText = text.toLowerCase();

        for (int i = 0; i < count; i++) {
            String name = students[i].getFullName().toLowerCase();
            String email = students[i].getEmail().toLowerCase();
            if (name.contains(lowerText) || email.contains(lowerText)) {
                result[resultCount] = students[i];
                resultCount++;
            }
        }

        return trim(result, resultCount);
    }

    public int getCount() {
        return count;
    }

    private int findIndexById(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) {
                return i;
            }
        }

        throw new IllegalArgumentException("студента з id " + id + " не знайдено");
    }

    private Student[] trim(Student[] source, int size) {
        Student[] result = new Student[size];
        for (int i = 0; i < size; i++) {
            result[i] = source[i];
        }
        return result;
    }

    private void growIfNeeded() {
        if (count < students.length) {
            return;
        }

        Student[] newStudents = new Student[students.length * 2];
        for (int i = 0; i < students.length; i++) {
            newStudents[i] = students[i];
        }
        students = newStudents;
    }
}
