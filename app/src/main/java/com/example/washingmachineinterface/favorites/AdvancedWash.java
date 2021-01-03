package com.example.washingmachineinterface.favorites;

public class AdvancedWash extends FavoriteItem {
    boolean prewash;
    String temperature;
    String rpm;
    boolean rinse;

    public AdvancedWash(String program, boolean prewash, String temperature, String rpm, boolean rinse) {
        super(program);
        this.prewash = prewash;
        this.temperature = temperature;
        this.rpm = rpm;
        this.rinse = rinse;
    }

    public String getPrewash(){
        return prewash? "Ναι" : "Όχι";
    }

    public boolean isPrewash() {
        return prewash;
    }

    public void setPrewash(boolean prewash) {
        this.prewash = prewash;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRpm() {
        return rpm;
    }

    public void setRpm(String rpm) {
        this.rpm = rpm;
    }

    public String getRinse(){
        return rinse? "Ναι" : "Όχι";
    }

    public boolean isRinse() {
        return rinse;
    }

    public void setRinse(boolean rinse) {
        this.rinse = rinse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdvancedWash that = (AdvancedWash) o;
        return prewash == that.prewash &&
                temperature.equals(that.temperature) &&
                rpm.equals(that.rpm) &&
                rinse == that.rinse;
    }
}
