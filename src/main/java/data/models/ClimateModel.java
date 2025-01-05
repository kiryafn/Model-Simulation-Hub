package data.models;

import data.Model;
import data.annotations.Bind;

public class ClimateModel implements Model{
    @Bind private int[] YEARS;     //years [from, to]
    @Bind private double[] grCO2;  //CO2 emission growth
    @Bind private double[] grTEMP; //temperature increment growth

    @Bind private double[] CO2;    //current CO2 emissions
    @Bind private double[] TEMP;   //temperature change
    @Bind private double[] CLIMATE_CHANGE;  //climate change index

    public ClimateModel() { }

    @Override
    public void run() {
        CLIMATE_CHANGE = new double[YEARS.length];
        CLIMATE_CHANGE[0] = CO2[0] + TEMP[0];
        for (int t = 1; t < YEARS.length; t++) {
            CO2[t] = grCO2[t] * CO2[t - 1];
            TEMP[t] = grTEMP[t] * TEMP[t - 1];
            CLIMATE_CHANGE[t] = CO2[t] + TEMP[t];
        }
    }
}