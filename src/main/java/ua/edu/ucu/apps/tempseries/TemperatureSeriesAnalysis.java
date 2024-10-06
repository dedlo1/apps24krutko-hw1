package ua.edu.ucu.apps.tempseries;

import java.util.InputMismatchException;
import java.util.Arrays;

public class TemperatureSeriesAnalysis {
    private static final int INITIAL_CAPACITY = 10;
    private static final int MIN_TEMPERATURE = -273;
    private double[] temperatureSeries;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[INITIAL_CAPACITY];
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        validateTemperatures(temperatureSeries);
        this.temperatureSeries = Arrays.copyOf(
            temperatureSeries, temperatureSeries.length * 2
            );
        this.size = temperatureSeries.length;
    }

    public double average() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += temperatureSeries[i];
        }
        return sum / size;
    }

    public double deviation() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double mean = average();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += (
                (temperatureSeries[i] - mean)
                * (temperatureSeries[i] - mean)
                );
        }
        return Math.sqrt(sum / size);
    }

    // Helper method to validate temperature values
    private void validateTemperatures(double[] temps) {
        for (double temp : temps) {
            if (temp < MIN_TEMPERATURE) {
                throw new InputMismatchException();
            }
        }
    }


    public double min() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double min = temperatureSeries[0];
        for (int i = 1; i < size; i++) {
            if (temperatureSeries[i] < min) {
                min = temperatureSeries[i];
            }
        }
        return min;
    }

    public double max() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double max = temperatureSeries[0];
        for (int i = 1; i < size; i++) {
            if (temperatureSeries[i] > max) {
                max = temperatureSeries[i];
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double closest = temperatureSeries[0];
        for (int i = 1; i < size; i++) {
            double currentTemp = temperatureSeries[i];
            if (Math.abs(currentTemp) < Math.abs(closest)
            || (Math.abs(currentTemp) == Math.abs(closest)
            && currentTemp > closest)) {
                closest = currentTemp;
            }
        }
        return closest;
    }

    public double findTempClosestToValue(double tempValue) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double closest = temperatureSeries[0];
        for (int i = 1; i < size; i++) {
            double currentTemp = temperatureSeries[i];
            if (Math.abs(
                currentTemp - tempValue
                ) < Math.abs(
                    closest - tempValue
                    ) 
                || (Math.abs(
                    currentTemp - tempValue
                    ) == Math.abs(
                        closest - tempValue
                        ) && currentTemp > closest)) {
                closest = currentTemp;
            }
        }
        return closest;
    }
    
    public double[] findTempsLessThen(double tempValue) {
        if (size == 0) {
            return new double[0];
        }
        double[] result = new double[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] < tempValue) {
                result[count++] = temperatureSeries[i];
            }
        }
        return Arrays.copyOf(result, count);
    }
    
    public double[] findTempsGreaterThen(double tempValue) {
        if (size == 0) {
            return new double[0];
        }
        double[] result = new double[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] > tempValue) {
                result[count++] = temperatureSeries[i];
            }
        }
        return Arrays.copyOf(result, count);
    }

    public double[] findTempsInRange(double tempLow, double tempHigh) {
        if (size == 0) {
            return new double[0];
        }
        double[] result = new double[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (
                temperatureSeries[i] >= tempLow
                &&
                temperatureSeries[i] <= tempHigh
                ) {
                result[count++] = temperatureSeries[i];
            }
        }
        return Arrays.copyOf(result, count);
    }
    
    public void reset() {
        this.temperatureSeries = new double[INITIAL_CAPACITY];
        this.size = 0;
    }
    
    public void sortTemps() {
        Arrays.sort(temperatureSeries, 0, size);
    }

    public TempSummaryStatistics summaryStatistics() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double avg = average();
        double dev = deviation();
        double min = min();
        double max = max();
        return new TempSummaryStatistics(avg, dev, min, max);
    }

    public int addTemps(double... newTemps) {
        validateTemperatures(newTemps);
        ensureCapacity(size + newTemps.length);
        System.arraycopy(newTemps, 0, temperatureSeries, size, newTemps.length);
        size += newTemps.length;
        return size;
    }
    
    // Helper method to ensure capacity of the temperature series
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > temperatureSeries.length) {
            int newCapacity = temperatureSeries.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            temperatureSeries = Arrays.copyOf(temperatureSeries, newCapacity);
        }
    }
    
    
}
