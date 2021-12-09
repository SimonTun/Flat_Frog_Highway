import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        int terminalWidth = 100;
        int terminalHeight = 50;

        TerminalSize ts = new TerminalSize(terminalWidth, terminalHeight);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        Frog frog = new Frog(new Position(50, 50), 'F');
        final char block = '-';

        terminal.setCursorPosition(frog.getPosition().getX(), frog.getPosition().getY());
        terminal.putCharacter(frog.getModel());

        //Create array
        Position[] roadSideDown = new Position[terminalWidth];
        Position[] roadSideUp = new Position[terminalWidth];
        for (int i = 0; i < terminalWidth; i++) {
            roadSideUp[i] = new Position(10, i);
            roadSideDown[i] = new Position(40, i);
        }

        //Print vägen
        for (Position p : roadSideDown) {
            terminal.setCursorPosition(p.getX(), p.getY());
            terminal.putCharacter(block);
        }
        terminal.flush();
        for (Position p : roadSideUp) {
            terminal.setCursorPosition(p.getX(), p.getY());
            terminal.putCharacter(block);
        }

        Car car1 = new Car(new Position((terminalHeight / 2),terminalWidth),'C');
        terminal.setCursorPosition(car1.getPosition().getX(),car1.getPosition().getY());
        terminal.putCharacter(car1.getModel());
        terminal.flush();

        boolean continueReadingInput = true;
        while (continueReadingInput) {
            int oldX = frog.getPosition().getX();
            int oldY = frog.getPosition().getY();

//            KEYSTROKE-LOOPEN BÖRJAR HÄR
            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 10 == 0) {
                    moveCarLeft(car1, car1.getPosition(), terminal);
                }
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            switch (keyStroke.getKeyType()) {
                case ArrowUp -> frog.moveUp();
                case ArrowDown -> frog.moveDown();
                case ArrowRight -> frog.moveRight();
                case ArrowLeft -> frog.moveLeft();
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

            int currentX = frog.getPosition().getX();
            int currentY = frog.getPosition().getY();

            boolean isCrash = false;
            for (Position p : roadSideUp) {
                if (p.getX() == currentX && p.getY() == currentY) {
                    isCrash = true;

                }
            }
//            if (isCrash) {
//                frog.setPosition(oldX);
//                frog.setPosition(oldY);
//            }

            terminal.setCursorPosition(oldX, oldY);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(frog.getPosition().getX(), frog.getPosition().getY());
            terminal.putCharacter(frog.getModel());
            terminal.flush();

//            TEXT VID VINST
            if (frog.hasReachedGoal()) {
                String line = "*** YOU MADE IT! ***";
                char[] charArray = line.toCharArray();
                for (int i = 0; i < line.length(); i++) {
                    charArray[i] = line.charAt(i);
                    terminal.setCursorPosition(50 + i, 50);
                    terminal.putCharacter(charArray[i]);
                    terminal.flush();

                }
            }
        }
    }

    public static void moveCarLeft(Car car, Position position, Terminal terminal) throws IOException {
        Position oldPosition = new Position(position.getY(), position.getX());
        car.setPosition(new Position(oldPosition.getY(), (oldPosition.getX() - 1)));

//        terminal.setCursorPosition(oldPosition.getX(), oldPosition.getY());
//        terminal.putCharacter(' ');

        terminal.setCursorPosition(position.getX(), position.getY());
        terminal.putCharacter('C');

        terminal.flush();
    }

}


