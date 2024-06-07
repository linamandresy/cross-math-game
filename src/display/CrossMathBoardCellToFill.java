package display;

import java.awt.Color;

import logic.Cell;

public class CrossMathBoardCellToFill extends CrossMathBoardCell{
    protected CrossMathCell gotCell=null;
    
    public CrossMathBoardCellToFill(Cell cell,CrossMathFrame frame) {
        super(cell,frame);
        setText("???");
        setForeground(Color.BLUE);
    }

    public CrossMathCell getGotCell() {
        return gotCell;
    }

    public void setGotCell(CrossMathCell gotCell) {
        this.gotCell = gotCell;
        setText(gotCell.getCell().getValueString());

    }

    public boolean checkCorrect() {

        return cell.getValue() == gotCell.getCell().getValue();
    }
    

}
