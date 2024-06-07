import display.CrossMathFrame;
import levelgenerator.LevelGenerator;
import logic.LevelModel;

public class CrossMathGame {
    public static void main(String[] args) throws Exception {
        LevelModel model = new LevelGenerator().generate();

        CrossMathFrame frame = new CrossMathFrame(model);
        frame.setVisible(true);

    }
}
