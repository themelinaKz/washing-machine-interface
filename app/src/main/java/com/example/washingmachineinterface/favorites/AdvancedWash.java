package com.example.washingmachineinterface.favorites;

class AdvancedWash extends FavoriteItem {
    boolean prewash;
    int temperature;
    int rpm;
    boolean rinse;

    public AdvancedWash(Program program, boolean prewash, int temperature, int rpm, boolean rinse) {
        super(program);
        this.prewash = prewash;
        this.temperature = temperature;
        this.rpm = rpm;
        this.rinse = rinse;
    }

    public String getPrewash(){
        return prewash? "Yes" : "No";
    }

    public boolean isPrewash() {
        return prewash;
    }

    public void setPrewash(boolean prewash) {
        this.prewash = prewash;
    }

    public String getTemperature() {
        return String.valueOf(temperature);
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getRpm() {
        return String.valueOf(rpm);
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public String getRinse(){
        return rinse? "Yes" : "No";
    }

    public boolean isRinse() {
        return rinse;
    }

    public void setRinse(boolean rinse) {
        this.rinse = rinse;
    }
}
