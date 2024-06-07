package display;

import java.awt.Color;

import javax.swing.JPanel;

import logic.Cell;
import logic.LevelModel;

public class CrossMathBoard extends JPanel{
    protected CrossMathFrame frame ;
    protected LevelModel model;
    
    public CrossMathFrame getFrame() {
        return frame;
    }

    public void setFrame(CrossMathFrame frame) {
        this.frame = frame;
    }


    public CrossMathBoard(CrossMathFrame frame){
        this.setFrame(frame);;
        initDisplay();
    }
    public void ready(){
        putBoardCell();
    }
    
    private void putBoardCell(){
        for (Cell cell : model.getBoardCells()) {
            this.add(cell.getCrossMathBoardCell(frame));
        }


    }
    public LevelModel getModel() {
        return model;
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    //     // TODO Auto-generated method stub
    //     super.paintComponent(g);

    //     Random random = new Random();
        
    //     for (List<Cell> cells : model.get_lines()) {
    //         // int red = random.nextInt(255);
    //         // int green = random.nextInt(255);
    //         // int blue = random.nextInt(255);
    //         g.setColor(new Color(255,0,0));
    //         for (Cell cell : cells) {
    //             cell.getCorrectIndex().paint(g,String.valueOf(cell.getValueString()));
    //         }
            
    //     }

    // }

    public void setModel(LevelModel model) {
        this.model = model;
    }

    private void initDisplay(){
        setVisible(true);
        setBackground(new Color(233,238,243));
        setBounds(0,0,this.frame.getWidth(),this.frame.getHeight()-this.frame.getPocketPanel().getHeight());
        setLayout(null);
    }
}
