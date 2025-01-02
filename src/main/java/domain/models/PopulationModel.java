package domain.models;

import data.Model;
import data.annotations.Bind;


public class PopulationModel implements Model{
    @Bind private int[] YEARS;      //years [from, to]

    @Bind private double[] grPOP;   //population growth
    @Bind private double[] grIMM;   //immigration growth

    @Bind private double[] POP;     //current population
    @Bind private double[] IMM;     //number of immigrants
    @Bind private double[] TOTAL_POP;    //total population after recalculation

    public PopulationModel() { }

    @Override
    public void run() {
        TOTAL_POP = new double[YEARS.length];
        TOTAL_POP[0] = POP[0] + IMM[0];
        for (int t = 1; t < YEARS.length; t++) {
            POP[t] = grPOP[t] * POP[t - 1];
            IMM[t] = grIMM[t] * IMM[t - 1];
            TOTAL_POP[t] = POP[t] + IMM[t];
        }
    }
}
