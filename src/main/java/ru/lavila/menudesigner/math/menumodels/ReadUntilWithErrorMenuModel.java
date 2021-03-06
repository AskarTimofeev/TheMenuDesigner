package ru.lavila.menudesigner.math.menumodels;

public class ReadUntilWithErrorMenuModel extends ReadUntilMenuModel
{
    protected final double errorProbability;

    public ReadUntilWithErrorMenuModel(double tResp, double tLoad, double tRead, double tClick, double errorProbability)
    {
        super(tResp, tLoad, tRead, tClick);
        this.errorProbability = errorProbability;
    }

    @Override
    public String getName()
    {
        return super.getName() + " with navigation mistakes probability";
    }

    @Override
    public double getTimeToSelect(int target, int total)
    {
        return (1 + 2 * errorProbability) * (tResp + tClick + tLoad * total) + errorProbability * tRead * total + (1 + errorProbability) * tRead * target;
    }

    @Override
    protected double getModelFactor(int total)
    {
        return ((1 + 2 * errorProbability) * (tResp + tLoad * total + tClick) + errorProbability * tRead * total) / ((1 + errorProbability) * tRead);
    }
}
