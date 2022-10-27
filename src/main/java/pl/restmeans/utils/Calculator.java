package pl.restmeans.utils;

import lombok.extern.slf4j.Slf4j;
import pl.restmeans.model.Grade;
import pl.restmeans.model.MeanOfSemester;
import pl.restmeans.model.MeanOfYear;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class Calculator {

    private Calculator() {}
    private static BigDecimal sumOfGrades(List<Grade> grades) {
        return BigDecimal.valueOf(grades
                .stream()
                .map(grade -> grade.getGradeVal() * grade.getWeight())
                .mapToDouble(Double::valueOf)
                .sum());
    }

    private static int sumOfEcts(List<Grade> grades) {
        return grades
                .stream()
                .map(Grade::getWeight)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private static BigDecimal meanOfGrades(List<Grade> grades){
        BigDecimal mean;
        int ects;
        int sum = sumOfEcts(grades);

        if (sum != 30) {
            return null;
        }

        mean = sumOfGrades(grades);
        ects = sumOfEcts(grades);

        try {
            return mean.divide(BigDecimal.valueOf(ects), MathContext.DECIMAL32);
        } catch(ArithmeticException e) {
            log.error("Cannot divide by zero");
            throw new ArithmeticException("Cannot divide by zero");
        }
    }

    public static List<MeanOfSemester> calculateMeansOfSemesters(Map<Integer, List<Grade>> grades) {
        List<MeanOfSemester> means = new ArrayList<>();
        BigDecimal mean;
        for (Map.Entry<Integer, List<Grade>> semester : grades.entrySet()) {
            try {
                mean = meanOfGrades(semester.getValue());
            } catch (ArithmeticException e) {
                return Collections.emptyList();
            }

            MeanOfSemester meanOfSemester = new MeanOfSemester(semester.getKey(), mean);
            means.add(meanOfSemester);
        }

        return means;
    }

    public static List<MeanOfYear> calculateMeansOfYear(List<MeanOfSemester> meanOfSemesters) {
        int numberOfYears = meanOfSemesters.size() / 2;
        List<MeanOfYear> meanOfYears = new ArrayList<>();
        BigDecimal mean = new BigDecimal(0);
        int semester = 0;

        for (int i = 1; i <= numberOfYears; i++) {
            mean = mean.add(meanOfSemesters.get(semester).getMean())
                    .add(meanOfSemesters.get(semester+1).getMean());
            mean = mean.divide(BigDecimal.valueOf(2), MathContext.DECIMAL32);

            MeanOfYear meanOfYear = new MeanOfYear(i, mean);
            meanOfYears.add(meanOfYear);

            mean = BigDecimal.ZERO;
            semester += 2;
        }

        return meanOfYears;
    }
}
