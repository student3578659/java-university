package university;

import java.util.Scanner;
import university.entities.Course;
import university.entities.Enrollment;
import university.entities.Student;
import university.entities.Teacher;
import university.enums.Grade;
import university.enums.StudentStatus;
import university.enums.TeacherPosition;
import university.services.CourseService;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.services.TeacherService;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final StudentService STUDENT_SERVICE = new StudentService();
    private static final TeacherService TEACHER_SERVICE = new TeacherService();
    private static final CourseService COURSE_SERVICE = new CourseService();
    private static final EnrollmentService ENROLLMENT_SERVICE = new EnrollmentService();

    public static void main(String[] args) {
        addDemoData();
        boolean running = true;

        while (running) {
            printMainMenu();
            String choice = SCANNER.nextLine();

            try {
                switch (choice) {
                    case "1":
                        openStudentMenu();
                        break;
                    case "2":
                        openTeacherMenu();
                        break;
                    case "3":
                        openCourseMenu();
                        break;
                    case "4":
                        openEnrollmentMenu();
                        break;
                    case "5":
                        openReportMenu();
                        break;
                    case "0":
                        running = false;
                        System.out.println("До побачення!");
                        break;
                    default:
                        System.out.println("Немає такого пункту меню.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("=== University CLI ===");
        System.out.println("1. Студенти");
        System.out.println("2. Викладачі");
        System.out.println("3. Курси");
        System.out.println("4. Зарахування");
        System.out.println("5. Звіти / Пошук");
        System.out.println("0. Вихід");
        System.out.print("Ваш вибір: ");
    }

    private static void openStudentMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println();
            System.out.println("=== Студенти ===");
            System.out.println("1. Додати студента");
            System.out.println("2. Показати всіх студентів");
            System.out.println("3. Оновити студента");
            System.out.println("4. Видалити студента");
            System.out.println("5. Змінити статус");
            System.out.println("6. Фільтр за статусом");
            System.out.println("7. Фільтр за роком навчання");
            System.out.println("8. Сортувати за ПІБ");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");

            try {
                switch (SCANNER.nextLine()) {
                    case "1":
                        addStudent();
                        break;
                    case "2":
                        printStudents(STUDENT_SERVICE.getAll());
                        break;
                    case "3":
                        updateStudent();
                        break;
                    case "4":
                        STUDENT_SERVICE.deleteStudent(readInt("id студента: "));
                        System.out.println("Студента видалено.");
                        break;
                    case "5":
                        changeStudentStatus();
                        break;
                    case "6":
                        printStudents(STUDENT_SERVICE.filterByStatus(readStudentStatus()));
                        break;
                    case "7":
                        printStudents(STUDENT_SERVICE.filterByYear(readInt("Рік навчання: ")));
                        break;
                    case "8":
                        printStudents(STUDENT_SERVICE.sortByName());
                        break;
                    case "0":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Немає такого пункту меню.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addStudent() {
        String fullName = readText("ПІБ: ");
        String email = readText("Email: ");
        int year = readInt("Рік навчання: ");
        StudentStatus status = readStudentStatus();
        Student student = STUDENT_SERVICE.addStudent(fullName, email, year, status);
        System.out.println("Додано: " + student);
    }

    private static void updateStudent() {
        int id = readInt("id студента: ");
        String fullName = readText("Новий ПІБ: ");
        String email = readText("Новий email: ");
        int year = readInt("Новий рік навчання: ");
        STUDENT_SERVICE.updateStudent(id, fullName, email, year);
        System.out.println("Студента оновлено.");
    }

    private static void changeStudentStatus() {
        int id = readInt("id студента: ");
        StudentStatus status = readStudentStatus();
        STUDENT_SERVICE.changeStatus(id, status);
        System.out.println("Статус оновлено.");
    }

    private static void openTeacherMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println();
            System.out.println("=== Викладачі ===");
            System.out.println("1. Додати викладача");
            System.out.println("2. Показати всіх викладачів");
            System.out.println("3. Оновити викладача");
            System.out.println("4. Видалити викладача");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");

            try {
                switch (SCANNER.nextLine()) {
                    case "1":
                        addTeacher();
                        break;
                    case "2":
                        printTeachers(TEACHER_SERVICE.getAll());
                        break;
                    case "3":
                        updateTeacher();
                        break;
                    case "4":
                        TEACHER_SERVICE.deleteTeacher(readInt("id викладача: "));
                        System.out.println("Викладача видалено.");
                        break;
                    case "0":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Немає такого пункту меню.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addTeacher() {
        String fullName = readText("ПІБ: ");
        String email = readText("Email: ");
        TeacherPosition position = readTeacherPosition();
        Teacher teacher = TEACHER_SERVICE.addTeacher(fullName, email, position);
        System.out.println("Додано: " + teacher);
    }

    private static void updateTeacher() {
        int id = readInt("id викладача: ");
        String fullName = readText("Новий ПІБ: ");
        String email = readText("Новий email: ");
        TeacherPosition position = readTeacherPosition();
        TEACHER_SERVICE.updateTeacher(id, fullName, email, position);
        System.out.println("Викладача оновлено.");
    }

    private static void openCourseMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println();
            System.out.println("=== Курси ===");
            System.out.println("1. Додати курс");
            System.out.println("2. Показати всі курси");
            System.out.println("3. Оновити курс");
            System.out.println("4. Видалити курс");
            System.out.println("5. Фільтр за викладачем");
            System.out.println("6. Фільтр за кредитами");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");

            try {
                switch (SCANNER.nextLine()) {
                    case "1":
                        addCourse();
                        break;
                    case "2":
                        printCourses(COURSE_SERVICE.getAll());
                        break;
                    case "3":
                        updateCourse();
                        break;
                    case "4":
                        COURSE_SERVICE.deleteCourse(readInt("id курсу: "));
                        System.out.println("Курс видалено.");
                        break;
                    case "5":
                        printCourses(COURSE_SERVICE.filterByTeacher(readInt("id викладача: ")));
                        break;
                    case "6":
                        printCourses(COURSE_SERVICE.filterByCredits(readInt("Кількість кредитів: ")));
                        break;
                    case "0":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Немає такого пункту меню.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addCourse() {
        checkTeachersExist();
        String title = readText("Назва курсу: ");
        int credits = readInt("Кількість кредитів: ");
        printTeachers(TEACHER_SERVICE.getAll());
        Teacher teacher = TEACHER_SERVICE.findById(readInt("id викладача: "));
        Course course = COURSE_SERVICE.addCourse(title, credits, teacher);
        System.out.println("Додано: " + course);
    }

    private static void updateCourse() {
        checkTeachersExist();
        int id = readInt("id курсу: ");
        String title = readText("Нова назва: ");
        int credits = readInt("Нова кількість кредитів: ");
        printTeachers(TEACHER_SERVICE.getAll());
        Teacher teacher = TEACHER_SERVICE.findById(readInt("id викладача: "));
        COURSE_SERVICE.updateCourse(id, title, credits, teacher);
        System.out.println("Курс оновлено.");
    }

    private static void openEnrollmentMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println();
            System.out.println("=== Зарахування ===");
            System.out.println("1. Створити зарахування");
            System.out.println("2. Поставити оцінку");
            System.out.println("3. Позначити оплату");
            System.out.println("4. Зарахування студента з GPA");
            System.out.println("5. Транскрипт студента");
            System.out.println("6. Показати всі зарахування");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");

            try {
                switch (SCANNER.nextLine()) {
                    case "1":
                        addEnrollment();
                        break;
                    case "2":
                        setGrade();
                        break;
                    case "3":
                        ENROLLMENT_SERVICE.markPaid(readInt("id зарахування: "));
                        System.out.println("Оплату позначено.");
                        break;
                    case "4":
                        showStudentEnrollments();
                        break;
                    case "5":
                        printTranscript();
                        break;
                    case "6":
                        printEnrollments(ENROLLMENT_SERVICE.getAll());
                        break;
                    case "0":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Немає такого пункту меню.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addEnrollment() {
        checkStudentsExist();
        checkCoursesExist();
        printStudents(STUDENT_SERVICE.getAll());
        Student student = STUDENT_SERVICE.findById(readInt("id студента: "));
        printCourses(COURSE_SERVICE.getAll());
        Course course = COURSE_SERVICE.findById(readInt("id курсу: "));
        String semester = readText("Семестр: ");
        Enrollment enrollment = ENROLLMENT_SERVICE.addEnrollment(student, course, semester);
        System.out.println("Додано: " + enrollment);
    }

    private static void setGrade() {
        printEnrollments(ENROLLMENT_SERVICE.getAll());
        int id = readInt("id зарахування: ");
        Grade grade = readGrade();
        ENROLLMENT_SERVICE.setGrade(id, grade);
        System.out.println("Оцінку збережено.");
    }

    private static void showStudentEnrollments() {
        int studentId = readInt("id студента: ");
        Enrollment[] enrollments = ENROLLMENT_SERVICE.getByStudent(studentId);
        printEnrollments(enrollments);
        System.out.printf("GPA: %.2f%n", ENROLLMENT_SERVICE.getStudentGpa(studentId));
    }

    private static void printTranscript() {
        int studentId = readInt("id студента: ");
        Student student = STUDENT_SERVICE.findById(studentId);
        Enrollment[] enrollments = ENROLLMENT_SERVICE.getByStudent(studentId);

        System.out.println();
        System.out.println("=== Транскрипт ===");
        System.out.println(student.getFullName() + ", email: " + student.getEmail());
        printEnrollments(enrollments);
        System.out.printf("GPA: %.2f%n", ENROLLMENT_SERVICE.getStudentGpa(studentId));
    }

    private static void openReportMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println();
            System.out.println("=== Звіти / Пошук ===");
            System.out.println("1. Пошук студента за ПІБ або email");
            System.out.println("2. Студенти з неоплаченими курсами");
            System.out.println("3. Середній GPA по курсу і семестру");
            System.out.println("4. Топ-N студентів за GPA");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");

            try {
                switch (SCANNER.nextLine()) {
                    case "1":
                        printStudents(STUDENT_SERVICE.searchByNameOrEmail(readText("Пошук: ")));
                        break;
                    case "2":
                        printUnpaidStudents();
                        break;
                    case "3":
                        printAverageGpaByCourseAndSemester();
                        break;
                    case "4":
                        printTopStudents();
                        break;
                    case "0":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Немає такого пункту меню.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void printUnpaidStudents() {
        Enrollment[] unpaid = ENROLLMENT_SERVICE.getUnpaid();

        if (unpaid.length == 0) {
            System.out.println("Немає неоплачених курсів.");
            return;
        }

        for (int i = 0; i < unpaid.length; i++) {
            System.out.println(unpaid[i].getStudent().getFullName() + " - " + unpaid[i].getCourse().getTitle());
        }
    }

    private static void printAverageGpaByCourseAndSemester() {
        printCourses(COURSE_SERVICE.getAll());
        int courseId = readInt("id курсу: ");
        String semester = readText("Семестр: ");
        double gpa = ENROLLMENT_SERVICE.getAverageGpaByCourseAndSemester(courseId, semester);
        System.out.printf("Середній GPA: %.2f%n", gpa);
    }

    private static void printTopStudents() {
        Student[] students = STUDENT_SERVICE.getAll();
        int n = readInt("Скільки студентів показати: ");

        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - i - 1; j++) {
                double firstGpa = ENROLLMENT_SERVICE.getStudentGpa(students[j].getId());
                double secondGpa = ENROLLMENT_SERVICE.getStudentGpa(students[j + 1].getId());
                if (firstGpa < secondGpa) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }

        int limit = n;
        if (limit > students.length) {
            limit = students.length;
        }

        for (int i = 0; i < limit; i++) {
            System.out.printf("%d. %s - GPA %.2f%n", i + 1, students[i].getFullName(),
                    ENROLLMENT_SERVICE.getStudentGpa(students[i].getId()));
        }
    }

    private static StudentStatus readStudentStatus() {
        System.out.println("Статуси: ACTIVE, ON_LEAVE, EXPELLED, GRADUATED");
        String status = readText("Статус: ");
        try {
            return StudentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("невідомий статус студента");
        }
    }

    private static TeacherPosition readTeacherPosition() {
        System.out.println("Посади: ASSISTANT, LECTURER, PROFESSOR");
        String position = readText("Посада: ");
        try {
            return TeacherPosition.valueOf(position.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("невідома посада викладача");
        }
    }

    private static Grade readGrade() {
        System.out.println("Оцінки: A, B, C, D, F, NA");
        String grade = readText("Оцінка: ");
        try {
            return Grade.valueOf(grade.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("невідома оцінка");
        }
    }

    private static int readInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("потрібно ввести число");
        }
    }

    private static String readText(String message) {
        System.out.print(message);
        String text = SCANNER.nextLine();
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("значення не може бути порожнім");
        }
        return text;
    }

    private static void printStudents(Student[] students) {
        if (students.length == 0) {
            System.out.println("Студентів немає.");
            return;
        }

        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
    }

    private static void printTeachers(Teacher[] teachers) {
        if (teachers.length == 0) {
            System.out.println("Викладачів немає.");
            return;
        }

        for (int i = 0; i < teachers.length; i++) {
            System.out.println(teachers[i]);
        }
    }

    private static void printCourses(Course[] courses) {
        if (courses.length == 0) {
            System.out.println("Курсів немає.");
            return;
        }

        for (int i = 0; i < courses.length; i++) {
            System.out.println(courses[i]);
        }
    }

    private static void printEnrollments(Enrollment[] enrollments) {
        if (enrollments.length == 0) {
            System.out.println("Зарахувань немає.");
            return;
        }

        for (int i = 0; i < enrollments.length; i++) {
            System.out.println(enrollments[i]);
        }
    }

    private static void checkStudentsExist() {
        if (STUDENT_SERVICE.getCount() == 0) {
            throw new IllegalArgumentException("спочатку додайте студента");
        }
    }

    private static void checkTeachersExist() {
        if (TEACHER_SERVICE.getCount() == 0) {
            throw new IllegalArgumentException("спочатку додайте викладача");
        }
    }

    private static void checkCoursesExist() {
        if (COURSE_SERVICE.getCount() == 0) {
            throw new IllegalArgumentException("спочатку додайте курс");
        }
    }

    private static void addDemoData() {
        Teacher teacher1 = TEACHER_SERVICE.addTeacher("Іван Петренко", "petrenko@university.edu", TeacherPosition.PROFESSOR);
        Teacher teacher2 = TEACHER_SERVICE.addTeacher("Олена Коваленко", "kovalenko@university.edu", TeacherPosition.LECTURER);

        Student student1 = STUDENT_SERVICE.addStudent("Андрій Мельник", "andrii@student.edu", 1, StudentStatus.ACTIVE);
        Student student2 = STUDENT_SERVICE.addStudent("Марія Шевченко", "maria@student.edu", 2, StudentStatus.ACTIVE);
        Student student3 = STUDENT_SERVICE.addStudent("Олег Бондар", "oleh@student.edu", 1, StudentStatus.ON_LEAVE);

        Course java = COURSE_SERVICE.addCourse("Java Programming", 5, teacher1);
        Course math = COURSE_SERVICE.addCourse("Math", 4, teacher2);

        Enrollment enrollment1 = ENROLLMENT_SERVICE.addEnrollment(student1, java, "2026 Spring");
        enrollment1.setGrade(Grade.A);
        enrollment1.markPaid();

        Enrollment enrollment2 = ENROLLMENT_SERVICE.addEnrollment(student2, java, "2026 Spring");
        enrollment2.setGrade(Grade.B);

        Enrollment enrollment3 = ENROLLMENT_SERVICE.addEnrollment(student3, math, "2026 Spring");
        enrollment3.setGrade(Grade.C);
    }
}
