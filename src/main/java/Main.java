import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;

import static java.awt.SystemColor.text;

public class Main {
    public static void main(String[] args) throws Exception {

        TerminalSize ts = new TerminalSize(100,  50 );
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);




//        terminal.setCursorPosition(1, 8);
//        terminal.putCharacter('A');
//        terminal.flush();

        Frog frog = new Frog(50,50 ,'F');
//        int x = 10;
//        int y = 10;
//        final char player = 'X';
        final char block = '\u2588';

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.putCharacter(frog.getModel());

        //Create array
        Position[] obstacles = new Position[10];
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Position(i + 5, 11);
        }

        //Print obstacles

        for (Position p : obstacles) {
            terminal.setCursorPosition(p.x, p.y);
            terminal.putCharacter(block);
        }
        terminal.flush();

        boolean continueReadingInput = true;

        while (continueReadingInput) {
            int oldX = frog.getX();
            int oldY = frog.getY();

            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

//            Character c = keyStroke.getCharacter(); // used for exit the loop when enter 'q'

            switch (keyStroke.getKeyType()) {
                case ArrowUp -> frog.setY(frog.getY()-1);
                case ArrowDown -> frog.setY(frog.getY()+1);
                case ArrowRight -> frog.setX(frog.getY()+1);
                case ArrowLeft -> frog.setX(frog.getY()-1);
                default -> {
                    System.out.println("Quitting");
                    continueReadingInput = false;
                    terminal.close();
                }
            }

//            if (c == Character.valueOf('q')) {
//                continueReadingInput = false;
//                System.out.println("Quit");
//                terminal.close();
//            }
            boolean isCrash = false;
            for (Position p : obstacles) {
                if (p.x == frog.getX() && p.y == frog.getY()) {
                    isCrash = true;

                }
            }
            if (isCrash) {
                frog.setX(oldX);
                frog.setY(oldY);
            }

            terminal.setCursorPosition(oldX, oldY);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(frog.getX(), frog.getY());
            terminal.putCharacter(frog.getModel());
            terminal.flush();






        }



        /*  output = input (fungerar)


        boolean continueReadingInput = true;

        while (continueReadingInput) {

            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter(); // used Character, not char because it might be null

            if (type != null) System.out.println(type);
            if (c != null) System.out.println(c);
            if (c == Character.valueOf('q')) {
                continueReadingInput = false;
                System.out.println("Quit");
                terminal.close();
            }
            terminal.flush();

        }
         */


//        for (int column = 4; column < 10; column++) {
//            terminal.setCursorPosition(column, 4);
//            terminal.putCharacter('X');
//            terminal.flush();
//        }
//
//        for (int row = 4; row < 10; row++) {
//            terminal.setCursorPosition(5, row);
//            terminal.putCharacter('X');
//            terminal.flush();
//        }


        //Fungerar:
//        String line = "This is a String printed out in Lanterna by iterating over the characters";
//        char[] charArray = line.toCharArray();
//        for (int i = 0; i < line.length(); i++) {
//            charArray[i] = line.charAt(i);
//            terminal.setCursorPosition(i,6);
//            terminal.putCharacter(charArray[i]);
//            terminal.flush();
//        }

        // Task 6
//        for (int i = 0; i<charArray.length; i++) {
//            terminal.setCursorPosition(i,6);
//            terminal.putCharacter(charArray[i]);
//            terminal.flush();
//        }


    }

}

