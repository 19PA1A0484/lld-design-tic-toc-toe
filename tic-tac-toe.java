import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

class Player{
    private String Name;
    private int id;
    private char ChoosingLetter;
    public String getName(){
        return Name;
    }
    public int getid(){
        return id;
    }
    public char getChoosingLetter(){
        return ChoosingLetter;
    }
    public void setName(String Name){
        this.Name=Name;
    }
    public void setid(int id){
        this.id=id;
    }
    public void setChoosingLetter(char ChoosingLetter){
        this.ChoosingLetter=ChoosingLetter;
    }
}
class GameBoard{
    char[][] board;
    int boardsize;
    Queue<Player> nextTurn;
    Scanner input;
    public GameBoard(int boardsize,Player[] players){
           this.boardsize=boardsize;
           this.board=new char[(2*boardsize)-1][(2*boardsize)-1];
           intializeBoard(board);
           nextTurn=new LinkedList<>();
           nextTurn.offer(players[0]);
           nextTurn.offer(players[1]);
           input = new Scanner(System.in);
    }
    private void intializeBoard(char[][] board){
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length;j++){
                if (i%2==0 && j%2!=0) board[i][j]='|';
                if (i%2!=0 && j%2==0) board[i][j]='+';
                if (i%2!=0 && j%2!=0) board[i][j]='-';
            }
        }
    }
    private void printBoard(){
        for (char[] row : board){
            for (char col : row){
                System.out.print(col);
            }
            System.out.println();
        }
    }
    public void playGame(){
        int count=0;
        while(true){
            count++;
            if (count ==((boardsize*boardsize)+1)){
                System.out.println("match draw");
                break;
            }
            Player p = nextTurn.poll();
            int position = getUserInput(p);
            int row=2*((position%boardsize==0 ? (position/boardsize)-1:position/boardsize));
            int col=2*((position%boardsize==0 ? boardsize:(position%boardsize))-1);
            board[row][col]=p.getChoosingLetter();
            printBoard();
            System.out.println("Board after a move");
            if (count>boardsize && checkEndGame(p,row,col)) break;
            nextTurn.offer(p);
        }
    }
    public int getUserInput(Player p){
        printBoard();
        System.out.println(p.getName()+" "+ "Choose numbers from 1 - "+ (boardsize*boardsize));
        int val=input.nextInt();
        while (!validateInput(val)){
            printBoard();
            System.out.println("not a valid input"+p.getName() +"please choose other number");
            val=input.nextInt();
        }
        return val;
    }
    private boolean validateInput(int val){
        if (val<1 || val>(boardsize*boardsize)) return false;

        int row=2*((val%boardsize==0 ? (val/boardsize)-1:val/boardsize));
        int col=2*((val%boardsize==0 ? boardsize:(val%boardsize))-1);

        if((int)board[row][col]!=0) return false;

        return true;
    }
    private boolean checkEndGame(Player p,int row,int col){
        String winString="";
        for (int i=0;i<boardsize;i++){
            winString+=String.valueOf(p.getChoosingLetter());
        }
        String rowString="";
        String colString="";
        String diagnalString="";
        String reverseDiagnalString="";
        for (int i=0;i<board.length;i=i+2){
            rowString+=board[row][i];
            colString+=board[i][col];
            if(row==col){
                diagnalString+=board[i][i];
            }
            if((row+col)==board.length-1){
                reverseDiagnalString+=board[board.length-1-i][i];
            }
        }
        if(winString.equals(rowString) || winString.equals(colString) || winString.equals(diagnalString) || winString.equals(reverseDiagnalString)){
            System.out.println(p.getName() + " " + "wins the game.");
            return true;
        }
        return false;

    }
}
public class StartGame{
    public static void main(String[] args){
        Player p1 = new Player();
        p1.setName("subbu");
        p1.setid(20);
        p1.setChoosingLetter('X');

        Player p2 = new Player();
        p2.setName("lol");
        p2.setid(10);
        p2.setChoosingLetter('O');
    
        Player[] players=new Player[]{p1,p2};
        GameBoard gb = new GameBoard(3, players);
        gb.playGame();

    }
}

