package university.util;

import university.entities.Enrollment;

public class GPAUtils {
    public static double calculateGpa(Enrollment[] enrollments, int count) {
        double sum = 0;
        int gradesCount = 0;

        for (int i = 0; i < count; i++) {
            if (enrollments[i] != null && enrollments[i].getGrade().hasPoints()) {
                sum += enrollments[i].getGrade().getPoints();
                gradesCount++;
            }
        }

        if (gradesCount == 0) {
            return 0;
        }

        return sum / gradesCount;
    }
}
