import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws Exception {
        int terminalWidth = 100;
        int terminalHeight = 50;

//      Skapa ny terminal
        TerminalSize ts = new TerminalSize(terminalWidth, terminalHeight);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        GameState gs = new GameState();

        // Car direction bestäms (flytta in i Car alt GameState senare)
        int startY = gs.getCarY();
        int startX = gs.getCarX();

        if (startX == 100) {
            gs.getCar().setDirection(CarDirection.LEFT);
        } else {
            gs.getCar().setDirection(CarDirection.RIGHT);
        }

//      Placera ut groda och bil
        terminal.setCursorPosition(gs.getFrogX(), gs.getFrogY());
        terminal.putCharacter(gs.getFrogModel());
        terminal.setCursorPosition(gs.getCarX(), gs.getCarY());
        terminal.putCharacter(gs.getCarModel());

        //Create array
        Position[] roadSideDown = new Position[terminalWidth];
        Position[] roadSideUp = new Position[terminalWidth];
        for (int i = 0; i < terminalWidth; i++) {
            roadSideUp[i] = new Position(i,10);
            roadSideDown[i] = new Position(i,40);
        }

        //Print vägen
        final char sideline = '-';
        for (Position p : roadSideDown) {
            terminal.setCursorPosition(p.getX(), p.getY());
            terminal.putCharacter(sideline);
        }
        terminal.flush();
        for (Position p : roadSideUp) {
            terminal.setCursorPosition(p.getX(), p.getY());
            terminal.putCharacter(sideline);
        }

        terminal.flush();

//        MEGALOOPENS BÖRJAN
        boolean continueReadingInput = true;
        while (continueReadingInput) {
            gs.getFrog().getPosition().setPreviousX(gs.getFrogX());
            gs.getFrog().getPosition().setPreviousY(gs.getFrogY());
            gs.getCar().getPosition().setPreviousX(gs.getCarX());
            gs.getCar().getPosition().setPreviousY(gs.getCarY());


//            KEYSTROKE-LOOPEN BÖRJAR HÄR
            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 10 == 0) {
                    moveCar(gs.getCar().getPosition(), gs.getCar().getDirection(), terminal);
                    hideLastPosition(gs.getCar().getPosition(), terminal);
                }
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            switch (keyStroke.getKeyType()) {
                case ArrowUp -> gs.getFrog().moveUp();
                case ArrowDown -> gs.getFrog().moveDown();
                case ArrowRight -> gs.getFrog().moveRight();
                case ArrowLeft -> gs.getFrog().moveLeft();
                default -> {
                    System.out.println("Quitting");
                    continueReadingInput = false;
                    terminal.close();
                }
            }

            int currentX = gs.getFrogX();
            int currentY = gs.getFrogY();

            boolean isCrash = false;
            for (Position p : roadSideUp) {
                if (p.getX() == currentX && p.getY() == currentY) {
                    isCrash = true;
                    break;
                }
            }
//            if (isCrash) {
//                frog.setPosition(oldX);
//                frog.setPosition(oldY);
//            }

            hideLastPosition(gs.getFrog().getPosition(), terminal);
            terminal.setCursorPosition(gs.getFrogX(), gs.getFrogY());
            terminal.putCharacter(gs.getFrogModel());
            terminal.flush();

//            TEXT VID VINST
            if (gs.getFrog().hasReachedGoal()) {
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

    public static void moveCar(Position position, CarDirection direction, Terminal terminal) throws IOException {
        if (direction == CarDirection.LEFT) {
            position.setX((position.getX() - 1));
        }
        else if (direction == CarDirection.RIGHT){
            position.setX((position.getX() + 1));
        }
        terminal.setCursorPosition(position.getX(), position.getY());
        terminal.putCharacter('C');
        terminal.flush();
    }

    public static void hideLastPosition(Position position, Terminal terminal) throws IOException {
        terminal.setCursorPosition(position.getPreviousX(), position.getPreviousY());
        terminal.putCharacter(' ');
    }
}


