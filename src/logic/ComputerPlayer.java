package logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mateusz on 20.01.2017.
 * Project VoxelMemory
 */
public class ComputerPlayer {
    public ComputerPlayer(){

    }
    public int[] getFirstMove(BoardLogic board){
        int[] move = new int[2];
        for(int i = 0; i < knownElementArrayList.size();i++) {
            for(int j = 0; j < knownElementArrayList.size();j++) {
                if(j==i)
                    continue;
                if(knownElementArrayList.get(i).block==knownElementArrayList.get(j).block) {
                    move[0] = knownElementArrayList.get(i).x;
                    move[1] = knownElementArrayList.get(i).y;
                    selectedBlock = knownElementArrayList.get(i).block;
                    forbiddenX = move[0];
                    forbiddenY = move[1];
                    knownElementArrayList.remove(i);
                    return move;
                }
            }
        }

        Random rnd = new Random();
        while(true) {
            int x = rnd.nextInt(board.width);
            int y = rnd.nextInt(board.height);
            if(board.visibleBoard[y][x]==0) {
                move[0] = x;
                move[1] = y;
                forbiddenX = move[0];
                forbiddenY = move[1];
                selectedBlock = board.actualBoard[y][x];
                knownElementArrayList.add(new KnownElement(x,y,board.actualBoard[y][x]));
                return move;
            }
        }
    }
    public int[] getSecondMove(BoardLogic board){
        int[] move = new int[2];
        for(int i = 0; i < knownElementArrayList.size();i++) {
            if(knownElementArrayList.get(i).block==selectedBlock && knownElementArrayList.get(i).x!=forbiddenX && knownElementArrayList.get(i).y!=forbiddenY) {
                move[0] = knownElementArrayList.get(i).x;
                move[1] = knownElementArrayList.get(i).y;
                return move;
            }
        }
        Random rnd = new Random();
        while(true) {
            int x = rnd.nextInt(board.width);
            int y = rnd.nextInt(board.height);
            if(board.visibleBoard[y][x]==0 && y!=forbiddenY && x!=forbiddenX) {
                move[0] = x;
                move[1] = y;
                knownElementArrayList.add(new KnownElement(x,y,board.actualBoard[y][x]));
                return move;
            }
        }
    }
    public void addKnownElement(int x,int y, int block) {
        if(!knownElementArrayList.contains(new KnownElement(x,y,block)))
            knownElementArrayList.add(new KnownElement(x,y,block));
    }
    public void removeKnownElement(int x,int y, int block) {
        try {
            knownElementArrayList.remove(new KnownElement(x, y, block));
        }
        catch (Exception ignored){

        }
    }

    private ArrayList<KnownElement> knownElementArrayList = new ArrayList<>();
    int forbiddenX;
    int forbiddenY;
    int selectedBlock;
}
