package backend.utilities;

public class Position extends Tuple<Integer, Integer> {
    public Position(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        char mappedX = (char) (this.x + 65);
        int mappedY =  8 -  this.y;
        return String.format("%c%d [%d, %d]", mappedX, mappedY, this.x, this.y);
    }
}
