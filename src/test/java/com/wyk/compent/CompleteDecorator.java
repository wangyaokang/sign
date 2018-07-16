package com.wyk.compent;

public class CompleteDecorator extends Decorator {
    public void eat() {
        super.run();
        reEat();
    }

    public void reEat() {
    }
}
