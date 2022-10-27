package pl.restmeans.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public final class GradesAndMeans {
    private Map<Integer, List<Grade>> grades;
    private List<MeanOfSemester> meansOfSemesters;
    private List<MeanOfYear> meansOfYears;

    public GradesAndMeans(Map<Integer, List<Grade>> grades) {
        this.grades = grades;
        this.meansOfSemesters = null;
        this.meansOfYears = null;
    }
}
