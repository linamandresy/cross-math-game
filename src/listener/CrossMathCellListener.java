package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;

import display.CrossMathBoardCellToFill;
import display.CrossMathCell;
import display.CrossMathFrame;


public class CrossMathCellListener implements MouseListener{
    private CrossMathCell cmCell ;
    private CrossMathFrame frame;
    
    public CrossMathCellListener(CrossMathCell cmCell, CrossMathFrame frame){
        setCmCell(cmCell);
        setFrame(frame);
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(frame.getCell()==null && !cmCell.getCell().isLocked() && !frame.getBoard().isAncestorOf(cmCell)){
            frame.setCell(this.cmCell);
            this.cmCell.requestFocus(true);
            this.frame.repaint();
        }else{
            if(!this.cmCell.getCell().isLocked() && this.cmCell!=frame.getCell()){
                CrossMathBoardCellToFill toFill = (CrossMathBoardCellToFill)cmCell;
                if(toFill.getGotCell()!=null){
                    frame.getPocketPanel().add(new CrossMathCell(toFill.getGotCell().getCell(), frame));
                }
                toFill.setGotCell(frame.getCell());
                frame.getPocketPanel().remove(frame.getCell());
                frame.repaint();
            }
            frame.requestFocus();
            frame.setCell(null);

        }
        if(frame.checkVita()){
            frame.getTimer().stop();
            JDialog dialog = new JDialog(frame,"Bravo : Temps : "+frame.getTimer().getCount()+"s",true);
            dialog.setSize(200,50);
            dialog.setVisible(true);
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public CrossMathCell getCmCell() {
        return cmCell;
    }


    public void setCmCell(CrossMathCell cmCell) {
        this.cmCell = cmCell;
    }


    public CrossMathFrame getFrame() {
        return frame;
    }


    public void setFrame(CrossMathFrame frame) {
        this.frame = frame;
    }

}
