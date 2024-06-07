package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class CrossMathTimer extends Timer{
    public CrossMathTimer(JLabel timerLabel) {
        super(1000, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                timerLabel.setText("Time : "+count+"s");
        }});
    }
    private int count = 0;
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    
}
