package backend;

import backend.utilities.Tuple;

public class Move {
    public Tuple<Integer,Integer> start;
    public Tuple<Integer,Integer> end;

    public Move(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end) {
        this.start = start;
        this.end = end;
    }

    public Tuple<Integer,Integer> getStart() { return start; }
    public Tuple<Integer,Integer> getEnd() { return end; }
}
