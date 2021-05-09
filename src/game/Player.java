package game;

public interface Player {
    void setNumber(int Number);
    int getNumber();

    void columnClicked(int columnIndex);

    void makeTurn();
}
