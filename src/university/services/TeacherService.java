package university.services;

import university.entities.Teacher;
import university.enums.TeacherPosition;

public class TeacherService {
    private Teacher[] teachers = new Teacher[10];
    private int count = 0;
    private int nextId = 1;

    public Teacher addTeacher(String fullName, String email, TeacherPosition position) {
        growIfNeeded();
        Teacher teacher = new Teacher(nextId, fullName, email, position);
        teachers[count] = teacher;
        count++;
        nextId++;
        return teacher;
    }

    public void updateTeacher(int id, String fullName, String email, TeacherPosition position) {
        Teacher teacher = findById(id);
        teacher.setFullName(fullName);
        teacher.setEmail(email);
        teacher.setPosition(position);
    }

    public void deleteTeacher(int id) {
        int index = findIndexById(id);

        for (int i = index; i < count - 1; i++) {
            teachers[i] = teachers[i + 1];
        }

        teachers[count - 1] = null;
        count--;
    }

    public Teacher findById(int id) {
        return teachers[findIndexById(id)];
    }

    public Teacher[] getAll() {
        Teacher[] result = new Teacher[count];
        for (int i = 0; i < count; i++) {
            result[i] = teachers[i];
        }
        return result;
    }

    public int getCount() {
        return count;
    }

    private int findIndexById(int id) {
        for (int i = 0; i < count; i++) {
            if (teachers[i].getId() == id) {
                return i;
            }
        }

        throw new IllegalArgumentException("викладача з id " + id + " не знайдено");
    }

    private void growIfNeeded() {
        if (count < teachers.length) {
            return;
        }

        Teacher[] newTeachers = new Teacher[teachers.length * 2];
        for (int i = 0; i < teachers.length; i++) {
            newTeachers[i] = teachers[i];
        }
        teachers = newTeachers;
    }
}
