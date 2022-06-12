package nonogram;

import algorithms.BackTracker;
import java.util.LinkedList;
import java.util.Scanner;

public class Nonogram {

    public static void main(String[] args) {
        int sqSide;
        LinkedList<Variable> columns = new LinkedList<>();
        LinkedList<Variable> rows = new LinkedList<>();
        String input = "";
        Scanner scanner = new Scanner(System.in);
        sqSide = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < sqSide; i++) {
            columns.add(new Variable(scanner.nextLine(), sqSide,i));
        }
        for (int i = 0; i < sqSide; i++) {
            rows.add(new Variable(scanner.nextLine(), sqSide,i));
        }
        Map map=new Map(columns,rows,sqSide);
        map.makeArcConsistent();
        /*if you want to check the algorithm solving the problem using FC,
        you have to make the line "map.makeArcConsistent" a comment line and
        also make the line "map.forwardCheck" in algorithms.Backtracker.backTrack,
        an instruction.*/
        if(!BackTracker.backTrack(map)){
            System.out.println("No Answer!");
        }
    }

}
