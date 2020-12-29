package com.example.washingmachineinterface.favorites;

class FavoriteItem {
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

    Program program;

    public FavoriteItem(Program program){
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
