package logic;

import java.util.LinkedList;
import java.util.List;

import logic.basic.Vector2Int;
import logic.basic.couple.IntBool;
import logic.util.Constante;

public class Board extends CellsContainer {
    private List<Vector2Int> gridIndexes;
    private Vector2Int size;
    private List<Vector2Int> rewardsIndexes;

    public Board(List<Cell> cells, List<Vector2Int> gridIndexes, List<Vector2Int> rewardIndexes, Vector2Int size) {
        super();
        this.gridIndexes = gridIndexes;
        this.rewardsIndexes = rewardIndexes;
        this.size = size;
        for (Cell cell : cells) {
            this.cells.add(cell);
        }
    }

    public void checkCrossState() {
        for (Cell cell : cells)
            cell.setIsCorrect(!cell.isLocked());

        List<Cell> correctCells = new LinkedList<Cell>();
        List<Cell> uncorrectCells = new LinkedList<Cell>();

        for (Cell cell : this.cells) {
            // TODO : eto aho zao
            if (cell.isEqual()) {
                checkLine(cell, Vector2Int.getRight(), correctCells, uncorrectCells);
                checkLine(cell, Vector2Int.getUp(), correctCells, uncorrectCells);
            }
        }

    }

    private void checkLine(Cell cell, Vector2Int direction, List<Cell> correctCells, List<Cell> uncorrectCells) {
        IntBool left = calculateResult(cell.getCurrentIndex(), direction.negativeValue());
        IntBool right = calculateResult(cell.getCurrentIndex(), direction);

        int leftResult = left.intVal;
        boolean isFullLeft = left.boolVal;

        int rightResult = right.intVal;
        boolean isFullRight = right.boolVal;

        if (isFullLeft && isFullRight) {
            if (leftResult == rightResult) {
                setLineCellCorrect(cell.getCurrentIndex(), direction, correctCells);
                setLineCellCorrect(cell.getCurrentIndex(), direction.negativeValue(), correctCells);
            } else {
                setLineCellCorrect(cell.getCurrentIndex(), direction, uncorrectCells);
                setLineCellCorrect(cell.getCurrentIndex(), direction.negativeValue(), uncorrectCells);
            }
        }
    }

    private void setLineCellCorrect(Vector2Int index, Vector2Int direction, List<Cell> list) {
        while(gridIndexes.contains(index)){
            Cell cell = getCell(index);
            if(cell!=null)
                list.add(cell);
            index.increment(direction);
        }
    }
    public boolean isComplete(){
        for (Vector2Int index : gridIndexes) {
            Cell cell = getCell(index);
            if(cell==null || !cell.isCorrect())
                return false;
            }
            return true;
    }

    private IntBool calculateResult(Vector2Int index, Vector2Int direction) {
        int result = 0;
        int prevSign = Constante.SignAdd;
        List<Cell> cells = new LinkedList<Cell>();

        index.increment(direction);
        do {
            Cell cell = getCell(index);
            if (cell != null)
                cells.add(0, cell);
            else
                return new IntBool(0, false);
        } while (gridIndexes.contains(index));
        if (cells.size() <= 0)
            return new IntBool(0, false);
        for (Cell cell : cells) {
            if (cell.isNumber()) {

                int valueInt = cell.getValue();

                if (prevSign == Constante.SignAdd)
                    result += valueInt;
                else if (prevSign == Constante.SignSub)
                    result -= valueInt;
                else if (prevSign == Constante.SignDiv)
                    result = result / (valueInt != 0 ? valueInt : 1);
                else if (prevSign == Constante.SignMul)
                    result *= valueInt;
            }else if (cell.isSign())
                prevSign = cell.getValue();
        }
        return new IntBool(result, true);
    }

    public List<Vector2Int> getRewardsIndexes() {
        return rewardsIndexes;
    }

    public Vector2Int getSize() {
        return size;
    }

    public void setSize(Vector2Int size) {
        this.size = size;
    }

    public List<Vector2Int> getGridIndexes() {
        return gridIndexes;
    }

}
