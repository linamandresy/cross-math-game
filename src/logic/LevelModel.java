package logic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import logic.basic.Vector2Int;

public class LevelModel {
    private Vector2Int size;
    private List<Vector2Int> gridIndexes,rewardIndexes;
    private  List<Cell> boardCells= new LinkedList<Cell>(),pocketCells= new LinkedList<Cell>();
    private List<List<Cell>> _lines;

    public Vector2Int getSize() {
        return size;
    }

    public void setSize(Vector2Int size) {
        this.size = size;
    }

    public List<Vector2Int> getGridIndexes() {
        return gridIndexes;
    }

    public void setGridIndexes(List<Vector2Int> gridIndexes) {
        this.gridIndexes = gridIndexes;
    }

    public List<Vector2Int> getRewardIndexes() {
        return rewardIndexes;
    }

    public void setRewardIndexes(List<Vector2Int> rewardIndexes) {
        this.rewardIndexes = rewardIndexes;
    }

    public List<Cell> getBoardCells() {
        return boardCells;
    }

    public void setBoardCells(List<Cell> boardCells) {
        this.boardCells = boardCells;
    }

    public List<Cell> getPocketCells() {
        return pocketCells;
    }

    public void setPocketCells(List<Cell> pocketCells) {
        this.pocketCells = pocketCells;
    }

    public List<List<Cell>> get_lines() {
        return _lines;
    }

    public void set_lines(List<List<Cell>> _lines) {
        this._lines = _lines;
    }

    public LevelModel(Cell[][] m,Vector2Int size,List<List<Cell>> lines){
        set_lines(lines);
        setBoardCells(new LinkedList<Cell>());
        setGridIndexes(new LinkedList<Vector2Int>());
        setRewardIndexes(new LinkedList<Vector2Int>());
        
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                Cell cell = m[i][j];
                if(cell!=null){
                    gridIndexes.add(new Vector2Int(i, j));
                    if(cell.isLocked()){
                        pocketCells.add(cell);
                    }else{
                        boardCells.add(cell);
                    }
                }
            }
        }
    }
    private void removeCell(Cell cell){
        List<Cell> temp = boardCells;
        boardCells = new LinkedList<Cell>();
        for (Cell cell2 : temp) {
            if(cell2!=cell){
                boardCells.add(cell2);
            }
        }
    }
    public void cellToPocket(int percentageToPocket,int percentageRewards){
        int i = 0;
        int steps = 1000;
        int countNumbers = 0;
        for (Cell cell : boardCells) {
            cell.setLocked(true);
            if(cell.isNumber())
                countNumbers++;            
        }
        int countToPocket = Math.max(countNumbers*percentageToPocket/100, _lines.size());
        while(steps>0 && pocketCells.size()<countToPocket && i< _lines.size()){
            List<Cell> line = _lines.get(i);
            List<Cell> boardCellsLoc = new LinkedList<Cell>();

            for (Cell cell : line) {
                if(cell.isNumber() && !pocketCells.contains(cell)){
                    boardCellsLoc.add(cell);
                }
            }
            if(boardCellsLoc.size()>1){
                Random random = new Random();
                Cell cell = boardCellsLoc.get(random.nextInt(boardCellsLoc.size()));
                cell.setLocked(false);
                //System.out.println("steps = "+steps +"cellule nuero "+i+" "+ cell.getValue());
                
                pocketCells.add(cell);
                removeCell(cell);

                if(random.nextInt(100)<percentageRewards){
                    rewardIndexes.add(cell.getCurrentIndex());
                }
            }
            steps--;
            i++;
            if(i==_lines.size()-1){
                Collections.shuffle(_lines);
            }
            // _lines.clear();
        }
    }
    public boolean isOperationCorrect(String operation){
        operation = operation.replaceAll("x", "*");
        String[] parts = operation.split("=");
        if(parts.length!=2) return false;
        String expression = parts[0].trim().replaceAll(" ", "");
        String expressionResult=parts[1].trim().replaceAll(" ", "");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try{
            Object resultRight = engine.eval(expression);
            Object resultLeft = engine.eval(expressionResult);
            if(resultRight instanceof Number && resultLeft instanceof Number){
                return Math.abs(((Number)resultRight).doubleValue()-((Number)resultLeft).doubleValue())<1e-9;
            } 
            return false;
        }catch(ScriptException e){
            return false;
        }
    }

    public boolean checkAllOperationCorrect(String[][] data){
        String[] operations = getAllOperations(data);
        for (String operation : operations) {
            if(!isOperationCorrect(operation)) {
                System.out.println(operation);
                return false;
            }
        }
        return true;
    }

    public String[] getAllOperations(String[][] data){
        List<List<Cell>> operationsIndexes = this.get_lines();
        int n =operationsIndexes.size();
        String[] result = new String[n];
        for (int i = 0; i < n ; i++) {
            List<Cell> ligne = operationsIndexes.get(i);
            int tailleLigne = ligne.size();
            String operation = "";
            for (int j = 0; j < tailleLigne; j++) {
                Cell cell = ligne.get(j);
                operation= operation.concat(getValueByIndex(cell.getCorrectIndex().getX(), cell.getCorrectIndex().getY(), data));
            }
            result[i] = operation;
        }

        return result;
    }

    public String generateJson(){
        String result = "[";
        List<Cell> cells = this.getBoardCells();
        for (Cell cell : cells) {
            String info = "["+cell.getCorrectIndex().getX()+","+cell.getCorrectIndex().getY()+",\""+cell.getValueString()+"\"],";
            result=result.concat(info);
        }
        cells = this.getPocketCells();
        for (Cell cell : cells) {
            String info = "["+cell.getCorrectIndex().getX()+","+cell.getCorrectIndex().getY()+",\""+cell.getValueString()+"\"],";
            result=result.concat(info);
        }
        result = result.substring(0,result.length()-1).concat("]");
        return result;     
    }
    public String getValueByIndex(int x , int y ,String[][] data){
        for (int i = 0; i < data.length; i++) {
            if(Integer.valueOf(data[i][0]).intValue()==x && Integer.valueOf(data[i][1]).intValue()==y){
                return data[i][2];
            }
        }
        return "";
    }
}
