package entity;

import lombok.Getter;

@Getter
public enum StepAmount {

    STEP_1 (500), // I ETAP
    STEP_2 (1000),
    STEP_3 (2000),
    STEP_4 (5000),
    STEP_5 (10000), //II ETAP
    STEP_6 (20000),
    STEP_7 (40000),
    STEP_8 (75000),
    STEP_9 (125000), // III Etap
    STEP_10 (250000),
    STEP_11 (500000),
    STEP_12 (1000000);

    int value;

    StepAmount(int value) {
        this.value = value;
    }




}
