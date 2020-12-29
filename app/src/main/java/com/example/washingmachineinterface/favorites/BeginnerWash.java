package com.example.washingmachineinterface.favorites;

class BeginnerWash extends FavoriteItem {
    Color color;
    boolean dirt;
    boolean allergy;

    public BeginnerWash(Program program, Color color, boolean dirt, boolean allergy) {
        super(program);
        this.color = color;
        this.dirt = dirt;
        this.allergy = allergy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDirt(){
        return dirt? "Yes" : "No";
    }

    public boolean isDirt() {
        return dirt;
    }

    public void setDirt(boolean dirt) {
        this.dirt = dirt;
    }

    public String getAllergy(){
        return allergy? "Yes" : "No";
    }

    public boolean isAllergy() {
        return allergy;
    }

    public void setAllergy(boolean allergy) {
        this.allergy = allergy;
    }
}
