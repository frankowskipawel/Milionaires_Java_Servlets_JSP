package entity;

import lombok.Getter;

@Getter
public enum AmountStep {

    STEP_1 (500),
    STEP_2 (1000),// Suma gwarantowana
    STEP_3 (2000),
    STEP_4 (5000),
    STEP_5 (10000),
    STEP_6 (20000),
    STEP_7 (40000),// Suma gwarantowana
    STEP_8 (75000),
    STEP_9 (125000),
    STEP_10 (250000),
    STEP_11 (500000),
    STEP_12 (1000000);

    int value;

    AmountStep(int value) {
        this.value = value;
    }
}
