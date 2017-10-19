package com.example.akra.testapp;

/**
 * Created by akra on 19.10.2017.
 */

public class ButtonPlacement
{
    private float randomZahlX;
    private float randomZahlY;
    private double decidePlusMinus = Math.random();
    private double randomValueX = Math.random();
    private double randomValueY = Math.random();


    public float getRandomZahlX()
    {
        if(decidePlusMinus < 0.35)
        {
            double ValueX = Math.round(540 - (randomValueX * 400));
            randomZahlX = ((float)ValueX);
            return randomZahlX;
        }
        else
        {
            if(decidePlusMinus <0.52)
            {
                double ValueX = Math.round(540 - (randomValueX * 400));
                randomZahlX = ((float)ValueX);
                return randomZahlX;
            }
            else
            {
                if(decidePlusMinus <0.71)
                {
                    double ValueX = Math.round(540 + (randomValueX * 350));
                    randomZahlX = ((float)ValueX);
                    return randomZahlX;
                }
                else
                {
                    double ValueX = Math.round(540 + (randomValueX * 350));
                    randomZahlX = ((float)ValueX);
                    return randomZahlX;
                }
            }
        }
    }


    public float getRandomZahlY()
    {
        if(decidePlusMinus < 0.35)
        {
            double ValueY = Math.round(960 - (randomValueY * 600));
            randomZahlY = ((float)ValueY);
            return randomZahlY;
        }
        else
        {
            if(decidePlusMinus <0.52)
            {
                double ValueY = Math.round(960 + (randomValueY * 500));
                randomZahlY = ((float)ValueY);
                return randomZahlY;
            }
            else
            {
                if(decidePlusMinus <0.71)
                {
                    double ValueY = Math.round(960 + (randomValueY * 500));
                    randomZahlY = ((float)ValueY);
                    return randomZahlY;
                }
                else
                {
                    double ValueY = Math.round(960 - (randomValueY * 600));
                    randomZahlY = ((float)ValueY);
                    return randomZahlY;
                }
            }
        }
    }
}
