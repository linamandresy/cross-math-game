package levelgenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import logic.Cell;
import logic.LevelModel;
import logic.basic.Vector2Int;
import logic.util.Constante;

public class LevelGenerator {

    private static final int SignAdd = Constante.SignAdd;
    private static final int SignSub = Constante.SignSub;
    private static final int SignDiv = Constante.SignDiv;
    private static final int SignMul = Constante.SignMul;
    private static final int SignEql = Constante.SignEql;

    private List<Integer> _signs1 = new LinkedList<Integer>(Arrays.asList(SignAdd, SignSub));
    private List<Integer> _signs2 = new LinkedList<Integer>(Arrays.asList(SignDiv, SignMul));

    public LevelModel generate() {
        LevelModel levelModel = generateBoard(Constante.TAILLE_GRILLE, Constante.TAILLE_GRILLE, Constante.MAX_NUMBER, 20);
        levelModel.cellToPocket(90, 0);
        return levelModel;
    }
    private boolean containsEql(LevelModel model){
        for (List<Cell> cells : model.get_lines()) {
            int nbEql = 0;
            for (Cell cell : cells) {
                if(cell.getValue()== Constante.SignEql)
                    nbEql++;
            }
            if(nbEql!=1) return false;
        }
        return true;
    }

    private LevelModel generateBoard(int w, int h, int maxNumber, int countTries) {
        LevelModel result = null;
        while (countTries > 0) {
            LevelModel levelModel = generateBoard(w, h, maxNumber);
            if ((result == null || result.getGridIndexes().size() < levelModel.getGridIndexes().size()) && containsEql(levelModel))
                result = levelModel;

            countTries--;
        }
        return result;
    }

    private LevelModel generateBoard(int w, int h, int maxNumber) {
        Cell[][] m = new Cell[w][h];
        List<List<Cell>> lines = new LinkedList<List<Cell>>();
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                m[i][j] = null;

        int steps = 1000;
        Random random = new Random();
        while (steps > 0) {
            int length = random.nextInt(100) > 90 ? 7 : 5;
            List<FreeIndexModel> freeIndexes = getFreeIndexesForLength(length, m);
            if (freeIndexes.size() > 0) {
                int rand = random.nextInt(freeIndexes.size());
                FreeIndexModel randonIndex = freeIndexes.get(rand);
                List<Cell> cells = getNewEqualityCells(randonIndex, m);
                if (fillCellsNumbers(cells, maxNumber, randonIndex.isIncrease)) {
                    for (Cell cell : cells) {
                        if (m[cell.getCurrentIndex().getX()][cell.getCurrentIndex().getY()] == null)
                            m[cell.getCurrentIndex().getX()][cell.getCurrentIndex().getY()] = cell;
                    }
                    lines.add(cells);
                }
            } else
                break;
            steps--;
        }
        return new LevelModel(m, new Vector2Int(w, h), lines);
    }

    private boolean fillCellsNumbers(List<Cell> cells, int maxNumber, boolean isIncrease) {
        if (!isIncrease)
            Collections.reverse(cells);

        if (cells.size() > 2 && cells.get(1).isEqual()) {
            cells.add(cells.get(1));
            cells.add(cells.get(0));
            cells.remove(0);
            cells.remove(1);
            cells.remove(2);
        }

        boolean result = generateNumbersForCells(cells, maxNumber);
        return result;

    }

    private boolean generateNumbersForCells(List<Cell> cells, int maxNumber) {
        Random random = new Random();
        if (cells.size() > 0) {
            List<Integer> mask = new LinkedList<Integer>();
            boolean allIsZero = true;
            int cellsSum = cells.get(cells.size() - 1).getValue();

            for (Cell cell : cells) {
                if (cell.isEqual())
                    break;
                if (cell.isNumber()) {
                    mask.add(cell.getValue());
                    if (cell.getValue() != 0) {
                        allIsZero = false;
                    }
                }
            }
            if (allIsZero){
                if(mask.size()==0)
                    mask.add(0, random.nextInt(maxNumber - 1) + 1);
                else 
                    mask.set(0, random.nextInt(maxNumber - 1) + 1);
            }

            int numbersCount = mask.size();
            List<Integer> allIndexes = new LinkedList<Integer>();
            getIndexesForMask(mask, maxNumber, numbersCount, numbersCount, allIndexes);
            Collections.shuffle(allIndexes);

            for (Integer i : allIndexes) {
                int sum = 0;
                int sign = SignAdd;

                for (int j = 0; j < numbersCount; j++) {
                    int number = i / (int) Math.pow(maxNumber, numbersCount - j - 1) % maxNumber + 1;
                    // System.out.println("Number = "+number);
                    if (sign == SignAdd)
                        sum += number;
                    else if (sign == SignSub)
                        sum -= number;
                    else if (sign == SignMul)
                        sum *= number;
                    else if (sign == SignDiv) {
                        if (sum % number == 0)
                            sum = sum / number;
                        else
                            sum = 0;
                        break;
                    }

                    int signIndex = 2*j+1;
                    if(signIndex>=0 && signIndex<cells.size())
                        sign = cells.get(signIndex).getValue();
                }

                if (sum >= 1 && sum <= maxNumber && (cellsSum == 0 || cellsSum == sum)) {
                    int n = 0;
                    for (Cell cell : cells) {
                        if (cell.isNumber()) {
                            int value;
                            if (n < numbersCount)
                                value = i / (int) Math.pow(maxNumber, numbersCount - n - 1) % maxNumber + 1;
                            else
                                value = sum;

                            cell.setValue(value);
                            n++;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void getIndexesForMask(List<Integer> mask, int maxNumber, int numbersCount, int iteration,
            List<Integer> result) {
        int number = 0;
        getIndexesForMask(mask, maxNumber, numbersCount, iteration, result, number);
    }

    private void getIndexesForMask(List<Integer> mask, int maxNumber, int numbersCount, int iteration,
            List<Integer> result, int number) {
        int maxCount = 100;
        getIndexesForMask(mask, maxNumber, numbersCount, iteration, result, number, maxCount);
    }

    private void getIndexesForMask(List<Integer> mask, int maxNumber, int numbersCount, int iteration,
            List<Integer> result, int number, int maxCount) {
        if (result.size() >= maxCount)
            return;
        if (iteration >= 1) {
            int numberIndex = number * maxNumber;
            int maskValue = mask.get(numbersCount - iteration);
            if (maskValue == 0) {
                for (int i = 0; i < maxNumber; i++)
                    getIndexesForMask(mask, maxNumber, numbersCount, iteration - 1, result, numberIndex + i);
            }else{
                getIndexesForMask(mask, maxNumber, numbersCount, iteration-1, result,numberIndex+maskValue-1);
            }
        }else
            result.add(number);
    }

    private List<Cell> getNewEqualityCells(FreeIndexModel model, Cell[][] m) {
        List<Cell> result = new LinkedList<Cell>();
        int i = model.index.getX();
        int j = model.index.getY();
        int step = model.isIncrease ? 1 : -1;
        Random random = new Random();
        List<Integer> signs = model.length == 5 && random.nextInt(100) > 80 ? _signs2 : _signs1;

        for (int n = 0; n < model.length; n++) {
            if(i>=0 && i<m.length && j>=0 && j<m[i].length){
                if (m[i][j] == null) {
                    Cell cell = new Cell(0, new Vector2Int(i, j));
                    if (model.isIncrease)
                        cell.setValue(n % 2 == 0 ? 0
                                : (n == model.length - 2 ? SignEql : signs.get(random.nextInt(signs.size()))));
                    else
                        cell.setValue(n % 2 == 0 ? 0 : (n == 1 ? SignEql : signs.get(random.nextInt(signs.size()))));
                    result.add(cell);
                } else {
                    result.add(m[i][j]);
                }
                if (model.isHorizontal) {
                    i += step;
                } else {
                    j += step;
                }
            }
        }

        return result;
    }

    private List<FreeIndexModel> getFreeIndexesForLength(int length, Cell[][] m) {
        LinkedList<FreeIndexModel> result = new LinkedList<FreeIndexModel>();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if ((i == 0 && j == 0 && m[i][j] == null) || (m[i][j] != null && m[i][j].isNumber())) {
                    List<Vector2Int> directions = new LinkedList<Vector2Int>(Arrays.asList(Vector2Int.getRight(),
                            Vector2Int.getUp(), Vector2Int.getDown(), Vector2Int.getLeft()));
                    for (Vector2Int direction : directions) {
                        boolean isIncrease = direction == Vector2Int.getRight() || direction == Vector2Int.getUp();
                        if (isAvailableInsert(m, i, j, length, direction, isIncrease)) {
                            FreeIndexModel model = new FreeIndexModel();
                            model.length = length;
                            model.index = new Vector2Int(i, j);
                            model.isHorizontal = direction == Vector2Int.getRight()
                                    || direction == Vector2Int.getRight();
                            model.isIncrease = isIncrease;
                            result.add(model);
                        }
                    }
                }
            }

        }
        return result;
    }

    private boolean isAvailableInsert(Cell[][] m, int i, int j, int length, Vector2Int direction, boolean isIncrease) {
        if (isCheck(m, i - direction.getX(), j - direction.getY())
                && m[i - direction.getX()][j - direction.getY()] != null)
            return false;
        while (isCheck(m, i, j) && (m[i][j] == null || m[i][j].isNumber())) {
            length--;
            i += direction.getX();
            j += direction.getY();
        }

        if (isCheck(m, i, j) && m[i][j] != null) {
            length = 1;
        }
        return length <= 0;
    }

    private boolean isCheck(Cell[][] m, int i, int j) {
        return i >= 0 && i < m.length && j >= 0 && j < m[0].length;
    }
}
