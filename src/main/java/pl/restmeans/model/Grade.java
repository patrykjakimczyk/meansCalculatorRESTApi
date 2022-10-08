package pl.restmeans.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class Grade {

    private String nameOfSubject;
    private double gradeVal;
    private int weight;

}
