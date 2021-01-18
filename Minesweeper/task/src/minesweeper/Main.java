package minesweeper;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean won = false;
        //get user input for mine number
        Scanner sc = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int mine = sc.nextInt();
        Game minesweeper = new Game(mine, 9);

        minesweeper.createField();
        minesweeper.placeMines();
        minesweeper.outputField();

        Scanner scanner = new Scanner(System.in);

        while (!minesweeper.wonGame() && !minesweeper.lostGame()) {
            System.out.println("Set/unset mine marks or claim a cell as free:");
            int b = scanner.nextInt() - 1;
            int a = scanner.nextInt() - 1;
            String mineOrFree = scanner.next();
            if(mineOrFree.equals("free")) {
                minesweeper.free(a, b);
            } else if(mineOrFree.equals("mine")) {
                minesweeper.updateMark(a, b);
            }
            minesweeper.outputField();
            minesweeper.checkLoss(a, b);
            minesweeper.checkWin();
            /*System.out.println(minesweeper.debug(8, 8));
            System.out.println(minesweeper.debug(7, 8));
            System.out.println(minesweeper.debug(8, 7));*/
        }
    }
}