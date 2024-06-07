package logic;

import java.util.LinkedList;
import java.util.List;

import logic.basic.Vector2Int;

public class CellsContainer {
    protected List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }
    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void addedCell(Cell model){
        if(cells==null)
            cells=new LinkedList<Cell>();
        if(!cells.contains(model))
            cells.add(model);
    }
    public void removeCell(Cell model){
        if(cells!=null && cells.contains(model))
            cells.remove(model);
    }
    public Cell getCell(Vector2Int index){
        for (Cell cell : cells) {
            if(cell.getCurrentIndex()==index) return cell;
        }
        return null;
    }
}
