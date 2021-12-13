import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean playAgain = true;

        int terminalWidth = 100;
        int terminalHeight = 50;
        int highscore = 0;

//      Skapa ny terminal
        TerminalSize ts = new TerminalSize(terminalWidth, terminalHeight);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        do {
            terminal.clearScreen();
            GameState gs = new GameState();

            // Car direction bestäms
            List<Car> cars = gs.getCars();
            setDirectionForCars(cars);

//      Placera ut groda, bilar och sidlinjer
            drawCharacters(cars, gs.getFrog(), terminal);
            drawRoadLines(terminal, terminalWidth, gs.getFrogY());
            terminal.flush();

//      MEGALOOPENS BÖRJAN
        boolean continueReadingInput = true;
        int counter = 0;
        int index = 0;
        int currentHighscore = 0;

            while (continueReadingInput) {
                printStaticHighscore(terminal, highscore);
                int frogOldX = gs.getFrogX();
                int frogOldY = gs.getFrogY();
                gs.getFrog().setPrevPosition(new Position(frogOldX, frogOldY));

                index++;
                currentHighscore++;
                if (index % 30 == 0) {                     //Timer för hur ofta bilarna ska röra på sig (hastighet)
                    moveCars(cars, terminal);
                }
                if (counter % 300 == 0) {                 //Timer för hur ofta en ny bil ska skapas/spawna.  30/300 känns bra!
                    for (int i = 0; i < 6; i++) {
                        gs.spawnAnotherCar(i, terminal);
                    }
                }
                terminal.flush();
                Thread.sleep(5);
                KeyStroke keyStroke = terminal.pollInput();
                counter++;

//          KEYSTROKE-LOOPEN BÖRJAR HÄR
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()) {
                        case ArrowUp -> gs.getFrog().moveUp();
                        case ArrowDown -> gs.getFrog().moveDown();
                        case ArrowRight -> gs.getFrog().moveRight();
                        case ArrowLeft -> gs.getFrog().moveLeft();
                        default -> {
                            System.out.println("Quitting");
                            continueReadingInput = false;
                            playAgain = false;
                            terminal.close();
                        }
                    }
                }

                hideLastPosition(gs.getFrog().getPrevPosition(), terminal);
                terminal.setForegroundColor(TextColor.ANSI.GREEN);
                terminal.setCursorPosition(gs.getFrogX(), gs.getFrogY());
                terminal.putCharacter(gs.getFrogModel());
                terminal.setForegroundColor(TextColor.ANSI.WHITE);
                drawRoadLines(terminal, terminalWidth, gs.getFrogY());
                gs.hasCrashed(cars);
                terminal.flush();

//            TEXT VID VINST
                if (gs.getFrog().hasReachedGoal()) {
                    printWinMessage(terminal);

                    if (currentHighscore < highscore) {
                        printHighscore(terminal, currentHighscore);
                        highscore = currentHighscore;
                        currentHighscore = 0;

                    } else if (highscore == 0){
                        printYourScore(terminal, currentHighscore);
                        highscore = currentHighscore;

                    } else if (currentHighscore > highscore){
                        printYourScore(terminal, currentHighscore);
                    }


                    playAgain = playAgain(terminal);




                    break;
                }


//            TEXT OM GRODAN DÖTT (stackars groda....)
                if (gs.isAlive()) {
                    printflatMessage(terminal, gs);
                    playAgain = playAgain(terminal);
                    break;
                }
            }
        } while (playAgain);
    }

    public static void printWinMessage(Terminal terminal) throws IOException {
        String line = "*** YOU MADE IT! ***";
        char[] charArray = line.toCharArray();
        for (int i = 0; i < line.length(); i++) {
            charArray[i] = line.charAt(i);
            terminal.setCursorPosition(39 + i, 25);
            terminal.setForegroundColor(TextColor.ANSI.CYAN);
            terminal.putCharacter(charArray[i]);
            terminal.flush();
        }
    }

    public static void printHighscore(Terminal terminal, long highscore) throws IOException {
        String line = "*** New time record: "+highscore + " ***";
        char[] charArray = line.toCharArray();
        for (int i = 0; i < line.length(); i++) {
            charArray[i] = line.charAt(i);
            terminal.setCursorPosition(39 + i, 20);
            terminal.setForegroundColor(TextColor.ANSI.CYAN);
            terminal.putCharacter(charArray[i]);
            terminal.flush();
        }
    }

    public static void printYourScore(Terminal terminal, long highscore) throws IOException {
        String line = "*** Your time was: "+highscore + " ***";
        char[] charArray = line.toCharArray();
        for (int i = 0; i < line.length(); i++) {
            charArray[i] = line.charAt(i);
            terminal.setCursorPosition(39 + i, 20);
            terminal.setForegroundColor(TextColor.ANSI.CYAN);
            terminal.putCharacter(charArray[i]);
            terminal.flush();
        }
    }

    public static void printStaticHighscore(Terminal terminal, int highscore) throws IOException {
        if (highscore != 0) {
            String line = "Fastest run: " + highscore;
            char[] charArray = line.toCharArray();
            for (int i = 0; i < line.length(); i++) {
                charArray[i] = line.charAt(i);
                terminal.setCursorPosition(80 + i, 1);
                terminal.setForegroundColor(TextColor.ANSI.CYAN);
                terminal.putCharacter(charArray[i]);
                terminal.setForegroundColor(TextColor.ANSI.WHITE);
                terminal.flush();
            }
        }
    }

    public static void printflatMessage(Terminal terminal, GameState gs) throws IOException {
        if (gs.isAlive()) {
            String line = "*** YOU GOT FLAT ***";
            char[] charArray = line.toCharArray();
            for (int i = 0; i < line.length(); i++) {
                charArray[i] = line.charAt(i);
                terminal.setCursorPosition(39 + i, 25);
                terminal.setForegroundColor(TextColor.ANSI.RED);
                terminal.putCharacter(charArray[i]);
                terminal.flush();
            }
        }
    }

    public static void moveCars(List<Car> cars, Terminal terminal) throws IOException {
        for (Car car : cars) {
            int newX = 0;

            int oldCarX = car.getPosition().getX();
            int oldCarY = car.getPosition().getY();
            car.setPrevPosition(new Position(oldCarX, oldCarY));

            CarDirection cd = car.getDirection();
            if (cd == CarDirection.LEFT) {
                if (car.getModel() == 'C') {
                    newX = (car.getPosition().getX()) - 1;
                    car.getPosition().setX(newX);
                } else if (car.getModel() == 'A') {
                    newX = (car.getPosition().getX()) - 2;
                    car.getPosition().setX(newX);
                } else {
                    newX = (car.getPosition().getX()) - 3;
                    car.getPosition().setX(newX);
                }
            } else if (cd == CarDirection.RIGHT) {
                if (car.getModel() == 'C') {
                    newX = (car.getPosition().getX() + 1);
                    car.getPosition().setX(newX);
                } else if (car.getModel() == 'A') {
                    newX = (car.getPosition().getX() + 2);
                    car.getPosition().setX(newX);
                } else {
                    newX = (car.getPosition().getX() + 3);
                    car.getPosition().setX(newX);
                }
            }
            terminal.setCursorPosition(oldCarX, oldCarY);
            terminal.putCharacter(' ');

            car.setPosition(car.getPosition());
            terminal.setCursorPosition(newX, car.getPosition().getY());
            terminal.putCharacter(car.getModel());
            terminal.flush();
        }
    }

    public static void hideLastPosition(Position position, Terminal terminal) throws IOException {
        terminal.setCursorPosition(position.getX(), position.getY());
        terminal.putCharacter(' ');
    }

    public static void setDirectionForCars(List<Car> cars) {
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
            terminal.setCursorPosition(car.getPosition().getX(), car.getPosition().getY());
            terminal.putCharacter(car.getModel());

            terminal.setCursorPosition(frog.getPosition().getX(), frog.getPosition().getY());
            terminal.putCharacter(frog.getModel());

        }
    }

    public static void drawRoadLines(Terminal terminal, int terminalWidth, int frogY) throws IOException {

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
        for (int i = 0; i < terminalWidth; i++) {
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

    public static boolean playAgain(Terminal terminal) throws IOException {
        boolean playAgain = false;
        String line = "Play again?  y/n";
        char[] charArray = line.toCharArray();
        for (int i = 0; i < line.length(); i++) {
            charArray[i] = line.charAt(i);
            terminal.setCursorPosition(41 + i, 26);
            terminal.putCharacter(charArray[i]);
            terminal.flush();
        }
        KeyStroke keyStroke = terminal.pollInput();
        while (!(Objects.equals(keyStroke, KeyStroke.fromString("y"))) || !(Objects.equals(keyStroke, KeyStroke.fromString("n")))) {
            keyStroke = terminal.pollInput();
            if ((Objects.equals(keyStroke, KeyStroke.fromString("y")))) {
                playAgain = true;
                break;
            } else if ((Objects.equals(keyStroke, KeyStroke.fromString("n")))) {
                System.out.println("Exiting...");
                terminal.close();
                break;
            }
        }
        return playAgain;
    }
}



