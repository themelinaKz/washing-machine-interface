package com.example.washingmachineinterface.favorites;

public class FavoriteItem {
    public enum Program{
        Cotton,
        Synthetic,
        Delicate,
        Wool
    }
    public enum Color{
        Light,
        Dark,
        Colorful
    }

    String program;

    public FavoriteItem(String program){
        this.program = program;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteItem that = (FavoriteItem) o;
        return getProgram().equals(that.getProgram());
    }
}