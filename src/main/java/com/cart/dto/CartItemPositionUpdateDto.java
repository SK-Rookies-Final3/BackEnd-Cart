package com.cart.dto;

public class CartItemPositionUpdateDto {
    private Long itemId;        // 아이템 ID
    private int xCoordinate;    // x좌표
    private int yCoordinate;    // y좌표

    // Getter와 Setter 메소드 추가
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
