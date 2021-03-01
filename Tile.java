package minesweeper;

class Tile {
    private final int x;
    private final int y;
    private boolean isClicked = false;
    private boolean isFlagged = false;
    private int numberValue = 0;
    private String displayed = ".";
    private Type tileType = Type.EMPTY;

    enum Type {
        MINE,
        NUMBER,
        EMPTY
    }
    public Tile(int a, int b) {
        x = a;
        y = b;
    }
    public boolean getIsClicked() {
        return isClicked;
    }
    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
    public boolean getIsFlagged() {
        return isFlagged;
    }
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
    public Type getTileType() {
        return tileType;
    }
    public int getNumberValue() {
        return numberValue;
    }
    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }
    public void setMINE() {
        tileType = Type.MINE;
    }
    public void setNUMBER() {
        tileType = Type.NUMBER;
    }
    public void setDisplayed() {
        if (isFlagged) {
            displayed = "*";
            return;
        }
        if (isClicked) {
            switch (tileType) {
                case MINE:
                    displayed = "X";
                    break;
                case NUMBER:
                    displayed = String.valueOf(numberValue);
                    break;
                case EMPTY:
                    displayed = "/";
            }
        } else {
            displayed = ".";
        }
    }
    public String getDisplayed() {
        return displayed;
    }
}
