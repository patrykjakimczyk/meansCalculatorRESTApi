package pl.restmeans.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public final class MeanOfYear {

    private int year;
    private BigDecimal mean;

}
