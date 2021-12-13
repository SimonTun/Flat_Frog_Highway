import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;
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
        gs.setTerminalHeight(terminalHeight);
        gs.setTerminalWidth(terminalWidth);

        // Car direction bestäms (flytta in i Car alt GameState senare??
        List<Car> cars = gs.getCars();
        setDirectionForCars(cars,terminal);

//      Placera ut groda, bilar och sidlinjer
        drawCharacters(cars, gs.getFrog(), terminal);
        drawRoadLines(terminal, terminalWidth, gs.getFrogY());


//        //Sparar denna loop för att krashrutin skall funka
//        Position[] roadSideDown = new Position[terminalWidth];
        Position[] roadSideUp = new Position[terminalWidth];
        for (int i = 0; i < terminalWidth; i++) {
            roadSideUp[i] = new Position(i, 10);
//            roadSideDown[i] = new Position(i, 40);
        }
//
//        //Print vägen
//        final char sideline = '-';
//        for (Position p : roadSideDown) {
//            terminal.setCursorPosition(p.getX(), p.getY());
//            terminal.putCharacter(sideline);
//        }
//        terminal.flush();
//        for (Position p : roadSideUp) {
//            terminal.setCursorPosition(p.getX(), p.getY());
//            terminal.putCharacter(sideline);
//        }
//
//        terminal.flush();

//        MEGALOOPENS BÖRJAN
        boolean continueReadingInput = true;
        while (continueReadingInput) {
            int frogOldX = gs.getFrogX();
            int frogOldY = gs.getFrogY();
            gs.getFrog().setPrevPosition(new Position(frogOldX, frogOldY));

//            KEYSTROKE-LOOPEN BÖRJAR HÄR
            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 10 == 0) {
                    moveCars(cars, terminal);
                    gs.hasCrashed(cars);
                    flatMessage(terminal, gs);

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

            hideLastPosition(gs.getFrog().getPrevPosition(), terminal);
            terminal.setCursorPosition(gs.getFrogX(), gs.getFrogY());
            terminal.putCharacter(gs.getFrogModel());
            drawRoadLines(terminal, terminalWidth, gs.getFrogY());
            terminal.flush();
            gs.hasCrashed(cars);                                    // Ställ frågan om bilen har crashat


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
//            TEXT OM GRODAN DÖTT (stackars groda....)
            if (!gs.isAlive()) {
                String line = "*** YOU GOT FLAT ***";
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

    public static void flatMessage(Terminal terminal, GameState gs) throws IOException {
        if (!gs.isAlive()) {
            String line = "*** YOU GOT FLAT ***";
            char[] charArray = line.toCharArray();
            for (int i = 0; i < line.length(); i++) {
                charArray[i] = line.charAt(i);
                terminal.setCursorPosition(50 + i, 50);
                terminal.putCharacter(charArray[i]);
                terminal.flush();
            }
        }

    }



    public static void moveCars(List<Car> cars, Terminal terminal) throws IOException {               //FUNKAR!!!!!!!!!
        for (Car car : cars) {
            int newX = 0;

            int oldCarX = car.getPosition().getX();
            int oldCarY = car.getPosition().getY();
            car.setPrevPosition(new Position(oldCarX, oldCarY));

            CarDirection cd = car.getDirection();
            if (cd == CarDirection.LEFT) {
                newX = (car.getPosition().getX()) - 1;
               car.getPosition().setX(newX);
            } else if (cd == CarDirection.RIGHT) {
                newX = (car.getPosition().getX() + 1);
                car.getPosition().setX(newX);
            }
            terminal.setCursorPosition(oldCarX, oldCarY);
            terminal.putCharacter(' ');

            car.setPosition(car.getPosition());
            terminal.setCursorPosition(newX,car.getPosition().getY());
            terminal.putCharacter(car.getModel());
            terminal.flush();
        }
    }

    public static void hideLastPosition(Position position, Terminal terminal) throws IOException {
        terminal.setCursorPosition(position.getX(), position.getY());
        terminal.putCharacter(' ');
    }

    public static void setDirectionForCars(List<Car> cars, Terminal terminal) throws IOException {
        for (Car car : cars) {
            int oldX = car.getPosition().getX();
            if (oldX == 100) {
                car.setDirection(CarDirection.LEFT);
            } else {
                car.setDirection(CarDirection.RIGHT);
            }
        }
    }

    public static void drawCharacters(List<Car> cars, Frog frog, Terminal terminal) throws IOException {
        for (Car car : cars) {
            terminal.setCursorPosition(car.getPosition().getX(),car.getPosition().getY());
            terminal.putCharacter(car.getModel());

           terminal.setCursorPosition(frog.getPosition().getX(), frog.getPosition().getY());
           terminal.putCharacter(frog.getModel());

        }
    }

    public static void drawRoadLines(Terminal terminal, int terminalWidth, int frogY) throws IOException{

        final char sideLine = '=';
        final char midLine = '-';

        //Create array
        Position[] roadSideDown = new Position[terminalWidth];
        Position[] middleLine = new Position[terminalWidth];
        Position[] roadSideUp = new Position[terminalWidth];
        for (int i = 0; i < terminalWidth; i++) {
            roadSideUp[i] = new Position(i, 10);
            middleLine[i] = new Position(i, 25);
            roadSideDown[i] = new Position(i, 40);
        }

        //Print vägen
        for (int i = 0; i <terminalWidth; i++) {
            if (frogY == roadSideUp[i].getY() || frogY == middleLine[i].getY() || frogY == roadSideDown[i].getY()) {
                continue;
            }
                terminal.setCursorPosition(roadSideUp[i].getX(), roadSideUp[i].getY());
                terminal.putCharacter(sideLine);
                terminal.setCursorPosition(middleLine[i].getX(), middleLine[i].getY());
                terminal.putCharacter(midLine);
                terminal.setCursorPosition(roadSideDown[i].getX(), roadSideDown[i].getY());
                terminal.putCharacter(sideLine);
            }


            terminal.flush();








    }
}


