//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Cheese Eater
// Files:           Main.java
// Course:          CS300: Programming II Spring 2018
//
// Author:          Yukun Feng
// Email:           yfeng92@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Yi Zhu
// Partner Email:   zhu342@wisc.edu
// Lecturer's Name: Gary Dahl
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   X___ Write-up states that pair programming is allowed for this assignment.
//   X___ We have both read and understand the course Pair Programming Policy.
//   X___ We have registered our team prior to the team registration deadline.
/////////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do.  If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Random;
import java.util.Scanner;

public class Feng{

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        char[][] room = new char[10][20];

        //1.Initialize room
        for( int i=0; i<room.length; i++){
            for( int j=0; j<room[0].length; j++)
                room[i][j] = '.';
        }

        //2.Place some walls
        placeWalls(room, 20, new Random());

        //3.Place some cheese, initialize the coordinates to (-1,-1)
        int[][] cheesePositions = new int [10][2];
        for( int i=0; i<cheesePositions.length; i++)
            for( int j=0; j<cheesePositions[i].length; j++)
                cheesePositions[i][j] = -1;
        placeCheeses(cheesePositions,room,new Random());

        //4.Decide the mouse3 spawn
        int mouseX,mouseY;
        int height = room.length;
        int width = room[0].length;
        while(true){
            mouseX = new Random().nextInt(height);
            mouseY = new Random().nextInt(width);
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
        int count = 0;

        System.out.println();
        printRoom(room,cheesePositions,mouseX,mouseY);

        while( actions > 0){
            System.out.print("Enter the next step you'd like the mouse to take (WASD): ");
            char move = sc.next().charAt(0);


            int[] result = moveMouse(mouseX,mouseY,room,move);


            if( result == null){
                actions++;
            }
            else{
                mouseX = result[0];
                mouseY = result[1];

                while(tryToEatCheese(mouseX, mouseY, cheesePositions))
                    count++;
            }
            System.out.println("\nThe mouse has eaten " + count + " cheese!" );
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
        int i,j;

        while( numberOfWalls > 0 ){
            i = randGen.nextInt(height);
            j = randGen.nextInt(width);

            if( room[i][j] == '.' ){
                room[i][j] = '#';
                numberOfWalls--;
            }
        }
    }
    public static void placeCheeses (int[][] cheesePositions, char[][] room, Random randGen) {
        int height = room.length;
        int width = room[0].length;
        int x,y;
        for(int i=0; i<cheesePositions.length; i++ ){
            while(true) {
                x = randGen.nextInt(height);
                y = randGen.nextInt(width);

                if( room[x][y] == '#' ) // If there is a wall
                    continue;

                boolean isChesse = false;
                for( int row=0; row<cheesePositions.length; row++){
                    if( x==cheesePositions[row][0] && y==cheesePositions[row][1] ) {
                        isChesse = true;
                        break;
                    }
                }
                if( !isChesse )  // If there is not a piece of cheese , succeed!
                    break;
            }
            //room[x][y] = '%';
            cheesePositions[i][0] = x;
            cheesePositions[i][1] = y;
        }
    }


    public static void printRoom(char[][] room, int[][] cheesePositions, int mouseX, int mouseY) {

        for( int i=0; i<room.length; i++){
            for( int j=0; j<room[0].length; j++) {
                if(i == mouseX && j == mouseY)
                    System.out.print('@');
                else{
                    boolean isCheese = false;
                    for( int row=0; row<cheesePositions.length; row++){
                        if( i==cheesePositions[row][0] && j==cheesePositions[row][1] ){
                            isCheese = true;
                            break;
                        }
                    }

                    if( isCheese )  System.out.print('%');
                    else            System.out.print(room[i][j]);
                }
            }
            System.out.println();

        }

    }

    public static int[] moveMouse(int mouseX, int mouseY, char[][] room, char move) {
//	    Scanner m = new Scanner(System.in);
//        move = m.next();
        move = Character.toUpperCase(move);
        switch (move) {
            case 'W':
                if (mouseX-1 < 0)
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                else if (room[mouseX-1][mouseY] == '#')
                    System.out.println("WARNING: Mouse cannot move into wall.");
                else return new int[]{mouseX-1, mouseY}; break;
            case 'A':
                if (mouseY-1 < 0)
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                else if (room[mouseX][mouseY-1] =='#')
                    System.out.println("WARNING: Mouse cannot move into wall.");
                else return new int[]{mouseX, mouseY-1}; break;
            case 'S':
                if (mouseX+1 >= room.length)
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                else if (room[mouseX+1][mouseY] =='#')
                    System.out.println("WARNING: Mouse cannot move into wall.");
                else return new int[]{mouseX+1, mouseY}; break;
            case 'D':
                if (mouseY+1 >= room[0].length)
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                else if (room[mouseX][mouseY+1] =='#')
                    System.out.println("WARNING: Mouse cannot move into wall.");
                else return new int[]{mouseX, mouseY+1}; break;
            default :
                System.out.println("WARNING: Didn't recognize move command: <move>");
                return null;
        }

        return null;

    }

    public static boolean tryToEatCheese(int mouseX, int mouseY, int[][] cheesePositions) {
        for(int i=0; i < cheesePositions.length; i++) {
            if (cheesePositions[i][0] == mouseX && cheesePositions[i][1] == mouseY) {
                cheesePositions[i][0] = -1;
                cheesePositions[i][1] = -1;
                return true;
            }
        }
        return false;
    }






}

