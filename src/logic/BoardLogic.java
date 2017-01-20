package logic;

import java.util.Random;

/**
 * Created by Mateusz on 19.01.2017.
 * Project InferenceEngine
 */
public class BoardLogic {

    public BoardLogic(int newWidht, int newHeight){
        width = newWidht;
        height = newHeight;

        visibleBoard = new int[height][width];
        actualBoard = new int[height][width];
        for(int i = 0; i < height;i++) {
            for(int j = 0; j < width;j++) {
                visibleBoard[i][j]=0;
                actualBoard[i][j]=-1;
            }
        }
        Random rnd = new Random();
        for(int i = 0; i < height;i++) {
            for (int j = 0; j < width; j++) {
                if (actualBoard[i][j] == -1) {
                    int newIndex = rnd.nextInt(typesCount);
                    int[] newIndexSecond = possiblePlace();
                    actualBoard[i][j] = newIndex;
                    actualBoard[newIndexSecond[1]][newIndexSecond[0]] = newIndex;
                    System.out.println("Type: " + newIndex + " " + i + " " + j + " " + newIndexSecond[1] + " " + newIndexSecond[0]);
                }
            }
        }
    }

    public int getWidht(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public int width;
    public int height;

    public int[][] visibleBoard;
    public int[][] actualBoard;

    int typesCount = 12;

    public void resetBoard() {
        visibleBoard = new int[height][width];
        actualBoard = new int[height][width];
        for(int i = 0; i < height;i++) {
            for(int j = 0; j < width;j++) {
                visibleBoard[i][j]=0;
                actualBoard[i][j]=0;
            }
        }
        Random rnd = new Random();
        for(int i = 0; i < height;i++) {
            for (int j = 0; j < width; j++) {
                if (actualBoard[i][j] == 0) {
                    int newIndex = rnd.nextInt(typesCount);
                    actualBoard[i][j] = newIndex;
                    int[] newIndexSecond = possiblePlace();
                    actualBoard[newIndexSecond[1]][newIndexSecond[0]] = newIndex;
                }
            }
        }
    }
    public boolean anyMove() {
        int freeBlock = 0;
        for(int i = 0; i < height;i++) {
            for(int j = 0; j < width;j++) {
                if(visibleBoard[i][j]==0)
                    freeBlock++;
            }
        }
        return freeBlock >= 2;
    }

    private int[] possiblePlace(){
        int[] possiblePlace = new int[2];
        Random rnd = new Random();
        while(true) {
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            if(actualBoard[y][x]==-1) {
                possiblePlace[0] = x;
                possiblePlace[1] = y;
                return possiblePlace;
            }
        }
    }

}
