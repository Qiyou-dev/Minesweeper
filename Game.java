package minesweeper;

import java.util.Objects;
import java.util.Random;

public class Game {
    private Tile[][] field;
    private final int mineNumber;
    private final int boardSize;
    private boolean lost = false;
    private boolean won = false;


    public Game(int mineNumber, int boardSize) {
        this.mineNumber = mineNumber;
        this.boardSize = boardSize;
        field = new Tile[boardSize][boardSize];
    }

    public boolean wonGame() {
        return won;
    }
    public boolean lostGame() {
        return lost;
    }
    public void createField() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                field[i][j] = new Tile(i, j);
            }
        }
    }
    public void placeMines() {
        int copy = mineNumber;
        Random random = new Random();
        while (copy > 0) {
            int a = random.nextInt(boardSize);
            int b = random.nextInt(boardSize);
            if (field[a][b].getTileType() == Tile.Type.MINE) {
                continue;
            }
            field[a][b].setMINE();
            updateNeighbors(a, b);
            copy--;
        }
    }
    public void updateNeighbors(int a, int b) {
        incrementCell(a, b + 1);
        incrementCell(a + 1, b);
        incrementCell(a, b - 1);
        incrementCell(a - 1, b);
        incrementCell(a + 1, b + 1);
        incrementCell(a - 1, b - 1);
        incrementCell(a + 1, b - 1);
        incrementCell(a - 1, b + 1);
    }
    public void incrementCell(int a, int b) {
        if (isOutOfBounds(a, b)) return;
        if (field[a][b].getTileType() == Tile.Type.MINE) return;
        if (field[a][b].getTileType() == Tile.Type.EMPTY) {
            field[a][b].setNUMBER();
            field[a][b].setNumberValue(1);
        } else {
            field[a][b].setNumberValue(field[a][b].getNumberValue() + 1);
        }
    }
    public void outputField() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < boardSize; j++) {
                System.out.print(field[i][j].getDisplayed());
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }
    public void free(int a, int b) {
        if (field[a][b].getIsClicked()) {
            System.out.println("There is a number here!");
        } else {
            freeNeighbors(a, b);
            field[a][b].setClicked(true);
            field[a][b].setFlagged(false);
            field[a][b].setDisplayed();
        }
    }
    private void freeNeighbors(int a, int b) {
        if (isOutOfBounds(a, b)) return;
        if (field[a][b].getIsClicked()) return;
        if (field[a][b].getTileType().equals(Tile.Type.MINE)) return;
        if (field[a][b].getTileType().equals(Tile.Type.NUMBER)) {
            field[a][b].setClicked(true);
            field[a][b].setFlagged(false);
            field[a][b].setDisplayed();
            return;
        }
        field[a][b].setClicked(true);
        field[a][b].setFlagged(false);
        field[a][b].setDisplayed();
        freeNeighbors(a, b + 1);
        freeNeighbors(a + 1, b);
        freeNeighbors(a, b - 1);
        freeNeighbors(a - 1, b);
        freeNeighbors(a + 1, b + 1);
        freeNeighbors(a - 1, b - 1);
        freeNeighbors(a + 1, b - 1);
        freeNeighbors(a - 1, b + 1);

    }
    public void updateMark(int a, int b) {
        if (field[a][b].getIsClicked()) {
            System.out.println("There is a number here!");
        } else if (field[a][b].getIsFlagged()) {
            field[a][b].setFlagged(false);
            field[a][b].setDisplayed();
        } else {
            field[a][b].setFlagged(true);
            field[a][b].setDisplayed();
        }
    }
    public void checkLoss(int a, int b) {
        if (field[a][b].getIsClicked() && field[a][b].getTileType().equals(Tile.Type.MINE)) {
            lost = true;
            System.out.println("You stepped on a mine and failed!");
        }
    }
    public void checkWin() {
        boolean markedAllMines = true;
        boolean exploredSafeTiles = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (!markedAllMines && !exploredSafeTiles) {
                    return;
                }
                if (field[i][j].getTileType() != Tile.Type.MINE && field[i][j].getIsFlagged()) {
                    markedAllMines = false;
                }
                if (Objects.equals(field[i][j].getTileType(), Tile.Type.MINE) && !field[i][j].getIsFlagged()) {
                    markedAllMines = false;
                }
                if (field[i][j].getTileType() != Tile.Type.MINE && !field[i][j].getIsClicked()) {
                    exploredSafeTiles = false;
                }
            }
        }
        if (markedAllMines || exploredSafeTiles) {
            won = true;
            System.out.println("Congratulations! You found all the mines!");
        }
    }
    public boolean isOutOfBounds(int a, int b) {
        if (a < 0 || b < 0) return true;
        return (a >= boardSize || b >= boardSize);
    }
}


