package map;

import characters.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;

import static map.MazeTile.*;

public class Maze {
    private ArrayList<ArrayList<MazeTile>> matrix;
    private int pastilleCounter , specialPastilleCounter;
    private int lineSize, columnSize;

    private int MapRandom() {
        int min = 1;
        int max = 3;
        int file = min + (int)(Math.random() * ((max - min) + 1));
        return file;
    }

    public Maze(int numberOfFile) {
        boolean created = false;

        while (!created) {
            String filename = "./src/main/java/resources/Map0" + numberOfFile + ".txt";
            matrix = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                columnSize = 0;

                while ((line = reader.readLine()) != null) {
                    if (line.length() > lineSize) {
                        lineSize = line.length();
                    }

                    ArrayList<MazeTile> mapLine = new ArrayList<>();

                    for (char c : line.toCharArray()) {
                        mapLine.add(discoverTile(c));
                    }

                    columnSize++;
                    matrix.add(mapLine);
                }
                created = true;
                reader.close();
            } catch (Exception e) {
                numberOfFile = MapRandom();
                System.out.println(e.getMessage());
            }
        }

    }

    private MazeTile discoverTile(char c) {
        switch (c) {
            case ('='):
                return MazeTile.WALL;
            case ('.'):
                this.pastilleCounter++;
                return PASTILLE;
            case ('o'):
                this.specialPastilleCounter++;
                return SPECIAL_PASTILLE;
            default:
                return MazeTile.EMPTY;
        }
    }

    public int getPastilleCounter() {
        return pastilleCounter;
    }

    public int getSpecialPastilleCounter() {
        return specialPastilleCounter;
    }

    public boolean isValidLocation(Location l){
        if (l.getX() < 0 || l.getY() < 0 || l.getX() > this.getColumnSize() || l.getY() > this.getLineSize()) {
            return false;
        }
        if (matrix.get((int) l.getX()).get((int) l.getY()) == MazeTile.WALL) {
            return false;
        }
        return true;
    }

    public int getLineSize() {
        return lineSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < this.lineSize ; j++) {
                try{
                    stringBuilder.append((matrix.get(i).get(j).charRep + " "));
                } catch (Exception e) {
                    stringBuilder.append(("  "));
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public MazeTile getMazeTile (Location l) {
        return matrix.get((int) l.getX()).get((int) l.getY());
    }

    public void eatPastille (Location l) {
        if (matrix.get((int) l.getX()).get((int) l.getY()) == PASTILLE || matrix.get((int) l.getX()).get((int) l.getY()) == SPECIAL_PASTILLE) matrix.get((int) l.getX()).set((int) l.getY(), EMPTY);
    }
}
