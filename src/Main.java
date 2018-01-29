import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        char[][] room = new char[6][7];

        //1.Initialize room
        for( int i=0; i<room.length; i++){
            for( int j=0; j<room[0].length; j++)
                room[i][j] = '.';
        }
        int[][] cheesePositions = new int [3][2];

        //2.Place some walls
        placeWalls(room ,3, new Random());

        //3.Place some cheese
        placeCheeses(cheesePositions,room,new Random());

        //4.Decide the mouse spawn
        int mouseX,mouseY;
        int height = room.length;
        int width = room[0].length;
        while(true){
            mouseX = (new Random()).nextInt(height);
            mouseY = (new Random()).nextInt(width);
            if( room[mouseX][mouseY]!='#' && room[mouseX][mouseY]!='%' )
                break;
        }


        //printRoom(room,cheesePositions,mouseX,mouseY);


        //5.Interact with user
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Cheese Eater simulation.");
        System.out.println("=======================================");
        System.out.print("Enter the number of steps for this simulation to run: ");
        int actions = sc.nextInt();
        printRoom(room,cheesePositions,mouseX,mouseY);

        while( actions > 0){
            System.out.print("Enter the next step you'd like the mouse to take (WASD): ");
            char ch = sc.next().charAt(0);


            int[] result = moveMouse(mouseX,mouseY,room,ch);


            if( result[0] == -1 && result[1] == -1){
                continue;
            }
            else{
                mouseX = result[0];
                mouseY = result[1];
            }
            printRoom(room,cheesePositions,mouseX,mouseY);

            actions--;
        }

        System.out.println("==================================================");
        System.out.println("Thank you for running the Cheese Eater simulation.");


    }

    public static void show(char[][] room){
        for( int i=0; i<room.length; i++){
            for( int j=0; j<room[0].length; j++)
                System.out.print(room[i][j]);
            System.out.println();
        }
        System.out.println();
    }

    public static void placeWalls(char[][] room, int numberOfWalls, Random randGen) {
        int height = room.length;
        int width = room[0].length;
        int x,y;

        while( numberOfWalls > 0 ){
            x = randGen.nextInt(height);
            y = randGen.nextInt(width);

            if( room[x][y] == '.' ){
                room[x][y] = '#';
                numberOfWalls--;
            }

        }


    }

    public static void placeCheeses(int[][] cheesePositions, char[][] room, Random randGen) {
        int height = room.length;
        int width = room[0].length;

        int x, y;
        for (int i = 0; i < cheesePositions.length; i++) {
            while(true){
                x = randGen.nextInt(height);
                y = randGen.nextInt(width);

                if( room[x][y]!='#' && room[x][y]!='%' )
                    break;
            }


            room[x][y] = '%';
            cheesePositions[i][0] = x;
            cheesePositions[i][1] = y;

        }
    }


    public static void printRoom(char[][] room, int[][] cheesePositions, int mouseX, int mouseY) {
        System.out.println();

        for( int i=0; i<room.length; i++){
            for( int j=0; j<room[0].length; j++) {
                if (i == mouseX && j == mouseY)
                    System.out.print('@');
                else
                    System.out.print(room[i][j]);
            }
            System.out.println();
        }
    }


    public static int[] moveMouse(int mouseX, int mouseY, char[][] room, char move) {
        move = Character.toUpperCase(move);

        switch(move){
            case 'W':
                if( moveMouseTo(mouseX-1,mouseY,room) )
                    return new int[]{mouseX-1,mouseY};
                break;
            case 'A':
                if( moveMouseTo(mouseX,mouseY-1,room) )
                    return new int[]{mouseX,mouseY-1};
                break;
            case 'S':
                if( moveMouseTo(mouseX+1,mouseY,room) )
                    return new int[]{mouseX+1,mouseY};
                break;
            case 'D':
                if( moveMouseTo(mouseX,mouseY+1,room) )
                    return new int[]{mouseX,mouseY+1};
                break;
            default:
                System.out.println("WARNING: Didn’t recognize move command: <" +move+ ">");
        }

        return new int[]{-1,-1};
    }

    public static boolean moveMouseTo(int curX, int curY, char[][] room){
        int height = room.length;
        int width = room[0].length;

        //System.out.println("curX="+curX+",curY="+curY);

        if( curX<0 || curX>=height || curY<0 || curY>=width){
            System.out.println("WARNING: Mouse cannot move outside the room.");
            return false;
        }else if( room[curX][curY] == '#' ){
            System.out.println("WARNING: Mouse cannot move into wall.");
            return false;
        }

        return true;
    }


    //public static boolean tryToEatCheese(int mouseX, int mouseY, int[][] cheesePositions) {}
}
