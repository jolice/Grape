package me.riguron.grape;

public class Impl1 implements IFace {

    private int v;

    public Impl1(int v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "Impl1 - " + v;
    }
}
