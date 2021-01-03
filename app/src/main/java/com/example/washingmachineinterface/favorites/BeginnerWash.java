package com.example.washingmachineinterface.favorites;

public class BeginnerWash extends FavoriteItem {
    String color;
    boolean dirt;
    boolean allergy;

    public BeginnerWash(String program, String color, boolean dirt, boolean allergy) {
        super(program);
        this.color = color;
        this.dirt = dirt;
        this.allergy = allergy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDirt(){
        return dirt? "Πολύ" : "Λίγο";
    }

    public boolean isDirt() {
        return dirt;
    }

    public void setDirt(boolean dirt) {
        this.dirt = dirt;
    }

    public String getAllergy(){
        return allergy? "Ναι" : "Όχι";
    }

    public boolean isAllergy() {
        return allergy;
    }

    public void setAllergy(boolean allergy) {
        this.allergy = allergy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BeginnerWash that = (BeginnerWash) o;
        return dirt == that.dirt &&
                allergy == that.allergy &&
                color.equals(that.color);
    }
}
