package com.rc.cloud.app.operate.domain.model.productsku.valobj;


public class CartonSize {

    private int length;

    private int width;

    private int height;

    public CartonSize(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
