package net.hecco.biomesbf.config;

public class LimitedIntValue {
    private int value;
    public int defaultValue;
    public final int min;
    public final int max;
    public LimitedIntValue(int value, int min, int max) {
        this.value = value;
        this.defaultValue = value;
        this.min = min;
        this.max = max;
    }
    public int value() {
        return this.value;
    }
    public void setValue(int i) {
        value = Math.clamp(i, min, max);
    }
}