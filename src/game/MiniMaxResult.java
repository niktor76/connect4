package game;

public class MiniMaxResult {
    int ratingOfColumn;
    int columnIndex;

    public MiniMaxResult(int ratingOfColumn, int columnIndex) {
        this.ratingOfColumn = ratingOfColumn;
        this.columnIndex = columnIndex;
    }
}
