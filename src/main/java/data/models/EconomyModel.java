package data.models;

import data.Model;
import data.annotations.Bind;

public class EconomyModel implements Model{
    @Bind private int LL;           // number of years

    @Bind private int[] YEARS;      //years [from, to]

    @Bind private double[] grPRC;    //the growth rate of private consumption
    @Bind private double[] grPUC;    //the growth rate of public consumption
    @Bind private double[] grINV;   //investment growth
    @Bind private double[] grEXP;   //export growth
    @Bind private double[] grIMP;   //import growth

    @Bind private double[] PRC;      //private consumption
    @Bind private double[] PUC;      //public consumption
    @Bind private double[] INV;     //investment
    @Bind private double[] EXP;     //export
    @Bind private double[] IMP;     //import
    @Bind private double[] GDP;     //Gross domestic product

    public EconomyModel() { }

    @Override
    public void run() {
        GDP = new double[LL];
        GDP[0] = PRC[0] + PUC[0] + INV[0] + EXP[0] - IMP[0];
        for (int t = 1; t < LL; t++) {
            PRC[t] = grPRC[t] * PRC[t - 1];
            PUC[t] = grPUC[t] * PUC[t - 1];
            INV[t] = grINV[t] * INV[t - 1];
            EXP[t] = grEXP[t] * EXP[t - 1];
            IMP[t] = grIMP[t] * IMP[t - 1];
            GDP[t] = PRC[t] + PUC[t] + INV[t] + EXP[t] - IMP[t];
        }
    }
}