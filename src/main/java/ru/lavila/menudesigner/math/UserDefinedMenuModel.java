package ru.lavila.menudesigner.math;

public class UserDefinedMenuModel implements MenuModel
{
    private final String name;
    private final double[][] values;
    private final double[] optimalProportion;

    public UserDefinedMenuModel(String name, double[][] values, double[] optimalProportion)
    {
        this.name = name;
        this.values = values;
        this.optimalProportion = optimalProportion;
    }

    public String getName()
    {
        return name;
    }

    public double getTimeToSelect(int target, int total)
    {
        if (total > values.length || target > values[total].length) return Double.MAX_VALUE;
        return values[total - 1][target - 1];
    }

    public double[] getOptimalProportion()
    {
        return optimalProportion;
    }
}