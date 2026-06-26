package university.entities;

public class Course {
    private final int id;
    private String title;
    private int credits;
    private Teacher teacher;

    public Course(int id, String title, int credits, Teacher teacher) {
        if (id < 1) {
            throw new IllegalArgumentException("id курсу має бути більше 0");
        }
        this.id = id;
        setTitle(title);
        setCredits(credits);
        setTeacher(teacher);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().length() < 2) {
            throw new IllegalArgumentException("назва курсу має містити мінімум 2 символи");
        }
        this.title = title.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits < 1) {
            throw new IllegalArgumentException("кількість кредитів має бути більше 0");
        }
        this.credits = credits;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("курс має мати викладача");
        }
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Course course = (Course) object;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Course{id=" + id + ", назва='" + title + "', кредити=" + credits
                + ", викладач='" + teacher.getFullName() + "'}";
    }
}
