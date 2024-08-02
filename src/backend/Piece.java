package backend;

import backend.utilities.Color;
import backend.utilities.Position;

public abstract class Piece implements canMove {
    protected Position pos;
    protected Color color;

    public Piece(Position pos, Color color) {
        this.pos = pos;
        this.color = color;
    }

    public int getX() {
        return this.pos.x;
    }

    public int getY() {
        return this.pos.y;
    }

    public Position getPos() { return this.pos;}
    public void setPos(Position pos) { this.pos = pos;}

    public void setX(int x) {
        this.pos.x = x;
    }

    public void setY(int y) {
        this.pos.y = y;
    }

    public Color getColor() { return color; }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.color + ")" + " at " + this.pos;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Piece other = (Piece) obj;

        if (color != other.color) {
            return false;
        }

        return (this.pos.x.equals(other.pos.x) && this.pos.y.equals(other.pos.y));
    }
}
