package com.reimu747.pokemon.util;

/**
 * @ClassName MathUtil
 * @Author Reimu747
 * @Date 2017/3/10 15:28
 * @Description My MathUtil class
 * @Version 1.0
 **/
public final class MathUtil
{
    /**
     * 返回向下取整后的数值
     *
     * @param a 原数
     * @param n 有效数字位数
     * @return 向下取整后的数值
     */
    public static double splitAndFloor(double a, int n)
    {
        return (Math.floor(a * Math.pow(10, n))) / (Math.pow(10, n));
    }

    /**
     * 返回向上取整后的数值
     *
     * @param a 原数
     * @param n 有效数字位数
     * @return 向上取整后的数值
     */
    public static double splitAndCeil(double a, int n)
    {
        return (Math.ceil(a * Math.pow(10, n))) / (Math.pow(10, n));
    }

    /**
     * 返回四舍五入取整后的数值
     *
     * @param a 原数
     * @param n 有效数字位数
     * @return 四舍五入后的数值
     */
    public static double splitAndRound(double a, int n)
    {
        return (Math.round(a * Math.pow(10, n))) / (Math.pow(10, n));
    }

    /**
     * 判断参数是否是奇数
     *
     * @param a 参数
     * @return 是否是奇数
     */
    public static boolean isOdd(int a)
    {
        return a % 2 != 0;
    }

    /**
     * 得到args数组中的最大值
     *
     * @param args 数组
     * @return 最大值
     */
    public static double getMax(double[] args) throws Exception
    {
        if (args == null || args.length < 1)
        {
            throw new Exception("参数个数有误！");
        }
        else
        {
            double res = args[0];
            for (double arg : args)
            {
                if (arg > res)
                {
                    res = arg;
                }
            }
            return res;
        }
    }

    /**
     * 得到args数组中的最小值
     *
     * @param args 数组
     * @return 最小值
     */
    public static double getMin(double[] args) throws Exception
    {
        if (args == null || args.length < 1)
        {
            throw new Exception("参数个数有误！");
        }
        else
        {
            double res = args[0];
            for (double arg : args)
            {
                if (arg < res)
                {
                    res = arg;
                }
            }
            return res;
        }
    }
}
