package logic;

import display.CrossMathBoardCell;
import display.CrossMathBoardCellToFill;
import display.CrossMathCell;
import display.CrossMathFrame;
import logic.basic.Vector2Int;
import logic.util.Constante;

public class Cell {
    private Vector2Int correctIndex, currentIndex;
    private int value;
    private boolean locked,correct;

    public Cell(int value, Vector2Int correctIndex) {
        this.value = value;
        this.correctIndex = correctIndex;
        this.currentIndex = correctIndex;
    }

    public boolean isEqual() {
        return value == Constante.SignEql;
    }

    public boolean isSign() {
        return value == Constante.SignAdd || value == Constante.SignDiv || value == Constante.SignMul
                || value == Constante.SignSub;
    }

    public boolean isNumber() {
        return !isEqual() && !isSign();
    }

    public Vector2Int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(Vector2Int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public Vector2Int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Vector2Int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIsCorrect(boolean isCorrect ){
        this.correct = isCorrect;
    }

    public boolean isCorrect() {
        return correct;
    }

    public CrossMathBoardCellToFill getCrossMathBoardCellToFill(CrossMathFrame frame){
        CrossMathBoardCellToFill toFill = new CrossMathBoardCellToFill(this,frame);
        frame.getCellsToFill().add(toFill);
        return toFill;
    }
    public CrossMathCell getCrossMathCell(CrossMathFrame frame){
        return new CrossMathCell(this,frame);
    }
    public CrossMathBoardCell getCrossMathBoardCell(CrossMathFrame frame){
        return new CrossMathBoardCell(this,frame);
    }

    public String getValueString() {
        String text ;
        switch (value) {
            case Constante.SignAdd:
                text = "+";
                break;
            case Constante.SignSub:
                text = "-";
                break;
            case Constante.SignDiv:
                text= "/";
                break;
            case Constante.SignMul:
                text="x";
                break;
            case Constante.SignEql:
                text="=";
                break;
            default:
                text = String.valueOf(value);
                break;
        }
        return text;

    }
}
