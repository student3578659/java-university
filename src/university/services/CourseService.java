package university.services;

import university.entities.Course;
import university.entities.Teacher;

public class CourseService {
    private Course[] courses = new Course[10];
    private int count = 0;
    private int nextId = 1;

    public Course addCourse(String title, int credits, Teacher teacher) {
        growIfNeeded();
        Course course = new Course(nextId, title, credits, teacher);
        courses[count] = course;
        count++;
        nextId++;
        return course;
    }

    public void updateCourse(int id, String title, int credits, Teacher teacher) {
        Course course = findById(id);
        course.setTitle(title);
        course.setCredits(credits);
        course.setTeacher(teacher);
    }

    public void deleteCourse(int id) {
        int index = findIndexById(id);

        for (int i = index; i < count - 1; i++) {
            courses[i] = courses[i + 1];
        }

        courses[count - 1] = null;
        count--;
    }

    public Course findById(int id) {
        return courses[findIndexById(id)];
    }

    public Course[] getAll() {
        Course[] result = new Course[count];
        for (int i = 0; i < count; i++) {
            result[i] = courses[i];
        }
        return result;
    }

    public Course[] filterByTeacher(int teacherId) {
        Course[] result = new Course[count];
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (courses[i].getTeacher().getId() == teacherId) {
                result[resultCount] = courses[i];
                resultCount++;
            }
        }

        return trim(result, resultCount);
    }

    public Course[] filterByCredits(int credits) {
        Course[] result = new Course[count];
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (courses[i].getCredits() == credits) {
                result[resultCount] = courses[i];
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
            if (courses[i].getId() == id) {
                return i;
            }
        }

        throw new IllegalArgumentException("курсу з id " + id + " не знайдено");
    }

    private Course[] trim(Course[] source, int size) {
        Course[] result = new Course[size];
        for (int i = 0; i < size; i++) {
            result[i] = source[i];
        }
        return result;
    }

    private void growIfNeeded() {
        if (count < courses.length) {
            return;
        }

        Course[] newCourses = new Course[courses.length * 2];
        for (int i = 0; i < courses.length; i++) {
            newCourses[i] = courses[i];
        }
        courses = newCourses;
    }
}
