package app;

import java.util.Scanner;

import app.exceptions.impl.InvalidValueException;

public class RPNShell {
    public static void main(String args[]) {
        int bitsize = 0;
        boolean signed = false;
        boolean checkValid = false;
        Scanner in = new Scanner(System.in);
        String nextLn;
        RPNCalc calc;

        System.out.println(welcomeMessage());

        while (!checkValid) {
            System.out.print("Enter bitsize: ");
            nextLn = in.nextLine();
            if (nextLn.compareToIgnoreCase("exit") == 0)
                return;
            try {
                bitsize = Integer.parseInt(nextLn);
                checkValid = true;
            }
            catch (Exception e) {
                System.out.println("Invalid bitsize - not a number.\nPlease try again.");
            }
        }
        checkValid = false;
        while (!checkValid) {
            System.out.print("Signed (+-) or unsigned (++)? ");
            nextLn = in.nextLine();
            if (nextLn.compareToIgnoreCase("exit") == 0)
                return;
            checkValid = true;
            if (nextLn.compareTo("+-") == 0)
                signed = true;
            else if (nextLn.compareTo("++") == 0)
                signed = false;
            else
                checkValid = false;
        }

        System.out.println("\nInitialized new RPN calculator");
        System.out.println("Bitsize: " + bitsize + (signed ? ", signed" : ", unsigned"));
        calc = new RPNCalc(bitsize, signed);
        
        while(true) {
            System.out.print("RPNCalculator> ");
            nextLn = in.nextLine();
            if (nextLn.compareToIgnoreCase("exit") == 0)
                break;
            else if (nextLn.compareToIgnoreCase("pop") == 0)
                System.out.println(calc.pop());
            else if (nextLn.compareToIgnoreCase("flags") == 0)
                System.out.println(calc.flags());
            else if (nextLn.compareToIgnoreCase("size") == 0)
                System.out.println(calc.size());
            else if (nextLn.compareToIgnoreCase("sign") == 0)
                System.out.println(calc.signed());
            else {
                try {
                    calc.evaluate(nextLn);
                }
                catch (InvalidValueException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static String welcomeMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("Welcome to the textual shell for Zack Matey's RPN Calculator.\n");
        sb.append("This project was created for Professor Houser's COS 470, Spring 2021 at USM\n");
        sb.append("This shell functions as a basic testing environment and demo,\n");
        sb.append("as this code is meant to be used in the backend of a calculator program.\n");
        sb.append("All rights reserved, Zack Matey 2021\n");
        return sb.toString();
    }
    
}
