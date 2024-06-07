package display;

import java.awt.Dimension;

import javax.swing.JButton;

import listener.CrossMathCellListener;
import logic.Cell;

public class CrossMathCell extends JButton{
    protected CrossMathFrame frame;
    protected Cell cell;
    public CrossMathFrame getFrame() {
        return frame;
    }
    public void setFrame(CrossMathFrame frame) {
        this.frame = frame;
    }
    public CrossMathCell(Cell cell,CrossMathFrame frame){
        setCell(cell);
        this.setText(cell.getValueString());
        this.setPreferredSize(new Dimension(ConstanteDisplay.largeur, ConstanteDisplay.hauteur));
        //this.setSize(80, 80);
        setFrame(frame);
        this.addMouseListener(new CrossMathCellListener(this,frame));
    }
    public Cell getCell() {
        return cell;
    }
    public void setCell(Cell cell) {
        this.cell = cell;
    }
     
}
