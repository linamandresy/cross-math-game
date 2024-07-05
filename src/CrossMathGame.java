import display.CrossMathFrame;
import levelgenerator.LevelGenerator;
import logic.LevelModel;

public class CrossMathGame {
    public static void main(String[] args) throws Exception {
        LevelModel model = new LevelGenerator().generate();
        // String json = model.generateJson();
        // String[][] data=convertToArray(json);
        // System.out.println(model.checkAllOperationCorrect(data));
        
        CrossMathFrame frame = new CrossMathFrame(model);
        frame.setVisible(true);

        
    }

    public static String[][] convertToArray(String value){
        value = value.replaceAll(" ", "").replaceAll("\"", "");
        String[] list = value.split("\\],\\[");
        String[][] result = new String[list.length][3];
        int i = 0;
        for (String cell : list) {
            String line = cell.replaceAll("\\[", "").replaceAll("\\]", "");
            String[] splited = line.split(",");
            result[i][0] = splited[0];
            result[i][1] = splited[1];
            result[i][2] = splited[2];
            i++;
        }
        return result;
    }
}
