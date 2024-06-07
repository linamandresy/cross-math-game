package display;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.LevelModel;
import util.CrossMathTimer;

public class CrossMathFrame extends JFrame {
    protected CrossMathBoard board;
    protected CrossMathPocketPanel pocketPanel;
    protected LevelModel model;
    protected CrossMathCell cell=null;
    protected List<CrossMathBoardCellToFill> cellsToFill= new LinkedList<CrossMathBoardCellToFill>();
    protected CrossMathTimer timer;
    protected JLabel timerLabel;
    public CrossMathFrame(LevelModel model) {
        initDisplay();
        setModel(model);
        loadDataToPanel();
    }

    private void loadDataToPanel() {
        board.setModel(model);
        pocketPanel.setModel(model);
        pocketPanel.ready();
        board.ready();
    }

    public void initDisplay() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1200);
        pocketPanel = new CrossMathPocketPanel(this);
        board = new CrossMathBoard(this);
        this.getContentPane().setLayout(getLayout());
        this.setLayout(null);
        this.add(board);
        this.add(pocketPanel);
        timerLabel = new JLabel("Time : 0s");
        timerLabel.setBounds(5, 5, 200, 50);
        this.board.add(timerLabel);
        timer = new CrossMathTimer(timerLabel);
        timer.start();
    }

    public CrossMathBoard getBoard() {
        return board;
    }

    public void setBoard(CrossMathBoard board) {
        this.board = board;
    }

    public CrossMathPocketPanel getPocketPanel() {
        return pocketPanel;
    }

    public void setPocketPanel(CrossMathPocketPanel pocketPanel) {
        this.pocketPanel = pocketPanel;
    }

    public LevelModel getModel() {
        return model;
    }

    public void setModel(LevelModel model) {
        this.model = model;
    }

    public CrossMathCell getCell() {
        return cell;
    }

    public void setCell(CrossMathCell cell) {
        this.cell = cell;
    }

    public List<CrossMathBoardCellToFill> getCellsToFill() {
        return cellsToFill;
    }
    public boolean checkVita(){
        for (CrossMathBoardCellToFill crossMathBoardCellToFill : cellsToFill) {
            if(!crossMathBoardCellToFill.checkCorrect())
                return false;
            
        }
        return true;
    }

    public CrossMathTimer getTimer() {
        return timer;
    }

        
}
