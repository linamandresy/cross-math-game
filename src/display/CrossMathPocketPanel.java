package display;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import logic.Cell;
import logic.LevelModel;

public class CrossMathPocketPanel extends JPanel{
    protected CrossMathFrame frame ;
    protected LevelModel model;
    public CrossMathPocketPanel(CrossMathFrame frame){
        setFrame(frame);
        initDisplay();
    }
    
    public void ready(){
        putPocketCell();
    }
    private void putPocketCell() {
        for (Cell cell : model.getPocketCells()) {
            this.add(cell.getCrossMathCell(frame));
            frame.board.add(cell.getCrossMathBoardCellToFill(frame));
        }
    }
    private void initDisplay(){
        this.setFrame(frame);
        this.setVisible(true);
        this.setBackground(new Color(255,255,255));
        this.setBounds(0,frame.getHeight()-300,this.frame.getWidth(),300);
        this.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
    }
    public CrossMathFrame getFrame() {
        return frame;
    }
    public void setFrame(CrossMathFrame frame) {
        this.frame = frame;
    }
    public void setModel(LevelModel model) {
        this.model = model;
    }
    
}
