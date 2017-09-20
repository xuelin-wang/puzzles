package xl.learn.uber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuelin on 9/19/17.
 */
public class AlgoX {
    public static class Cell {
        int val;
        int metadata;
        Cell left;
        Cell right;
        Cell up;
        Cell down;
        public Cell(int val, int metadata, Cell left, Cell right, Cell up, Cell down) {
            this.val = val;
            this.metadata = metadata;
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }
    }

    /**
     * convert grid with 0/1 values to row/columns for cells of 1 value
     * @param matrix
     * @return
     */
    public static Cell createGrid(int[][] matrix)
    {
        Cell head = new Cell(0, 0, null, null, null, null);
        head.up = head;
        head.down = head;
        head.left = head;
        head.right = head;

        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        Cell[] rowHeaders = new Cell[rowCount];
        for (int i = 0; i < rowCount; i++) {
            rowHeaders[i] = new Cell(i, 0, null, null, null, null);
            rowHeaders[i].left = rowHeaders[i];
            rowHeaders[i].right = rowHeaders[i];
        }
        for (int i = 0; i < rowCount; i++) {
            if (i > 0)
                rowHeaders[i].up = rowHeaders[i-1];
            if (i < rowCount - 1)
                rowHeaders[i].down = rowHeaders[i+1];
        }
        rowHeaders[0].up = head;
        rowHeaders[rowCount-1].down = head;
        head.up = rowHeaders[rowCount - 1];
        head.down = rowHeaders[0];

        Cell[] colHeaders = new Cell[colCount];
        for (int i = 0; i < colCount; i++) {
            colHeaders[i] = new Cell(i, 0, null, null, null, null);
            colHeaders[i].up = colHeaders[i];
            colHeaders[i].down = colHeaders[i];
        }
        for (int i = 0; i < colCount; i++) {
            if (i > 0)
                colHeaders[i].left = colHeaders[i - 1];
            if (i < colCount - 1)
                colHeaders[i].right = colHeaders[i + 1];
        }
        colHeaders[0].left = head;
        colHeaders[colCount - 1].right = head;
        head.right = colHeaders[0];
        head.left = colHeaders[colCount - 1];

        for (int row = 0; row < rowCount; row++)
            for (int col = 0; col < colCount; col++) {
                if (matrix[row][col] == 0)
                    continue;

                Cell cell = new Cell(matrix[row][col], 0, null, null, null, null);
                Cell rowHeader = rowHeaders[row];
                rowHeader.metadata++;
                cell.left = rowHeader.left;
                cell.right = rowHeader;
                rowHeader.left.right = cell;
                rowHeader.left = cell;

                Cell colHeader = colHeaders[col];
                colHeader.metadata++;
                cell.up = colHeader.up;
                cell.down = colHeader;
                colHeader.up.down = cell;
                colHeader.up = cell;
            }

        //remove empty rowheaders colheaders
        Cell cell = head.right;
        while (cell != head) {
            if (cell.metadata == 0) {
                unlinkLR(cell);
            }
            cell = cell.right;
        }

        cell = head.down;
        while (cell != head) {
            if (cell.metadata == 0) {
                unlinkUD(cell);
            }
            cell = cell.down;
        }

        return head;
    }

    private static void relinkLR(Cell cell) {
        cell.left.right = cell;
        cell.right.left = cell;
    }
    private static void unlinkLR(Cell cell) {
        cell.left.right = cell.right;
        cell.right.left = cell.left;
    }
    private static void relinkUD(Cell cell) {
        cell.up.down = cell;
        cell.down.up = cell;
    }
    private static void unlinkUD(Cell cell) {
        cell.up.down = cell.down;
        cell.down.up = cell.up;
    }

    private static Cell pickRow(boolean heuristic, Cell head, int minVal) {
        Cell rowHeader = null;
        Cell tmp = head.down;
        while (tmp != head) {
            if (heuristic) {
                if ((rowHeader == null || rowHeader.metadata < tmp.metadata) && tmp.val > minVal)
                    rowHeader = tmp;
            }
            else {
                if (rowHeader == null && tmp.val > minVal) {
                    rowHeader = tmp;
                    break;
                }
            }
            tmp = tmp.down;
        }
        return rowHeader;
    }

    private static Cell getHeader(Cell cell, boolean rowOrColumn) {
        Cell retval = cell;
        while (cell.metadata == 0) {
            cell = rowOrColumn ? cell.left : cell.up;
        }
        return cell;
    }

    private static Cell getHead(Cell cell, boolean rowOrColumn) {
        Cell tmp = cell;
        while (tmp.metadata > 0) {
            tmp = rowOrColumn ? tmp.down : tmp.right;
        }
        return tmp;
    }


    private static void cover(Cell rowHeader) {
//        System.out.println("before cover: " + rowHeader.val);
//        showGrid(getHead(rowHeader, true));

        //adjust column headers' counts of all cells in this row
        Cell rowCell = rowHeader.right;
        while (rowCell != rowHeader) {
            Cell colCell = rowCell.down;
            while (colCell != rowCell) {
                if (colCell.metadata > 0) {
                    //column header. unlink it
                    unlinkLR(colCell);
                }
                else {
                    //unlink rows of this colCell
                    Cell tmpCell = colCell.right;
                    while (tmpCell != colCell) {
                        unlinkUD(tmpCell);
                        tmpCell = tmpCell.right;
                    }
                }
                colCell = colCell.down;
            }
            rowCell = rowCell.right;
        }
        unlinkUD(rowHeader);

//        System.out.println("after cover: " + rowHeader.val);
//        showGrid(getHead(rowHeader, true));
    }

    private static void uncover(Cell rowHeader) {
//        System.out.println("before uncover: " + rowHeader.val);
//        showGrid(getHead(rowHeader, true));

        relinkUD(rowHeader);
        Cell rowCell = rowHeader.right;
        while (rowCell != rowHeader) {
            Cell colCell = rowCell.down;
            while (colCell != rowCell) {
                if (colCell.metadata > 0) {
                    //column header. relink it
                    relinkLR(colCell);
                }
                else {
                    //relink rows of this colCell
                    Cell tmpCell = colCell.right;
                    while (tmpCell != colCell) {
                        relinkUD(tmpCell);
                        tmpCell = tmpCell.right;
                    }
                }
                colCell = colCell.down;
            }
            rowCell = rowCell.right;
        }

//        System.out.println("after uncover: " + rowHeader.val);
//        showGrid(getHead(rowHeader, true));

    }

    private static void backtrack(List<Cell> rowHeaders, Cell head) {
        while (!rowHeaders.isEmpty()) {
            Cell lastRowHeader = rowHeaders.remove(rowHeaders.size() - 1);
            uncover(lastRowHeader);
            Cell rowHeader = lastRowHeader.down;
            if (rowHeader != head) {
                rowHeaders.add(rowHeader);
                cover(rowHeader);
                break;
            }
        }
    }

    private static void showGrid(Cell head) {
        //column headers
        Cell tmp = head.right;
        System.out.print(" / : ");
        while (tmp != head) {
            System.out.print(tmp.val + "/" + tmp.metadata + ", ");
            tmp = tmp.right;
        }
        System.out.println();

        tmp = head.down;
        while (tmp != head) {
            System.out.print(tmp.val + "/" + tmp.metadata + ": ");
            Cell cell = tmp.right;
            while (cell != tmp) {
                Cell colHeader = getHeader(cell, false);
                System.out.print("col: " + colHeader.val + ", ");
                cell = cell.right;
            }
            System.out.println();
            tmp = tmp.down;
        }
    }
    /**
     * head is a cell with down point to first row head, up point to last row head
     * right to first column head, left to last column head.
     * row head points to all cells in the row using left and right
     * column head points to all cells in the column using up and down
     *
     * @param head
     * @param <T>
     * @return a list of rows which covers all columns.
     */
    public static List<List<Cell>> coverages(Cell head, boolean heuristic, boolean firstSolution) {
        if (heuristic && !firstSolution) {
            throw new IllegalArgumentException("Heuristic only for firstSolution");
        }

        List<List<Cell>> solutions = new ArrayList<>();


        boolean done = false;
        while (!done) {
            if (firstSolution && solutions.size() > 0)
                break;

            List<Cell> lastSolution = solutions.isEmpty() ? null : solutions.get(solutions.size() - 1);
            List<Cell> thisSolution = new ArrayList<>();
            if (lastSolution != null)
                thisSolution.addAll(lastSolution);

            Cell rowHeader = null;
            if (thisSolution.isEmpty()) {
                //just started
                rowHeader = pickRow(heuristic, head, -1);
                thisSolution.add(rowHeader);
                cover(rowHeader);
            } else {
                //backtrack
                backtrack(thisSolution, head);
                if (thisSolution.isEmpty()) {
                    done = true;
                    break;
                }
            }

            while (true) {
                if (head.left == head) {
                    solutions.add(thisSolution);
                    break;
                }

                if (head.down == head) {
                    //backtrack
                    backtrack(thisSolution, head);
                    if (thisSolution.isEmpty()) {
                        done = true;
                        break;
                    }
                }
                else {
                    int minVal = thisSolution.isEmpty() ? -1 : thisSolution.get(thisSolution.size() - 1).val;
                    Cell nextRow = pickRow(heuristic, head, minVal);
                    if (nextRow == null) {
                        done = true;
                        break;
                    }
                    thisSolution.add(nextRow);
                    cover(nextRow);
                }
            }
        }
        return solutions;
    }

    public static void main(String[] args) {
        boolean useHeuristic;
        if (args.length > 0) {
            useHeuristic = "true".equalsIgnoreCase(args[0]);
        }
        else {
            useHeuristic = false;
        }

        boolean findFirstSolution;
        if (args.length > 1) {
            findFirstSolution = "true".equalsIgnoreCase(args[1]);
        }
        else {
            findFirstSolution = false;
        }

        int[][] matrix = {
                {0, 1, 1},
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}
        };
        solveMatrix(useHeuristic, findFirstSolution, matrix);
    }

//    private static int[][] createSudokuGrid(int[][] sudoku) {
//        int[][] grid = new int[81 * 9][81 * 3];
//
//        for (int row = 0; row < 9; row++) {
//            for (int col = 0; col < 9; col++) {
//                int k = sudoku[row][col];
//                if (k > 0) {
//                    int[] gridRow = new int[81 * 3];
//                    for (int i = 0; i < 81 * 3; i++)
//                        gridRow[i] = 0;
//
//                    int rowColIndex = 9 * k + row;
//                    gridRow[rowColIndex] = 1;
//                    int colColIndex = 9 * k + col;
//                    gridRow[81 + colColIndex] = 1;
//                    int gridIndex = row / 3 * 3 + col / 3;
//                    int gridColIndex = 9 * k + gridIndex;
//                    gridRow[81 * 2 + gridColIndex] = 1;
//                    grid.add(gridRow);
//                }
//            }
//        }
//
//
//
//
//
//        Cell head = new Cell(0, 0, null, null, null, null);
//        head.left = head.right = head.up = head.down = head;
//
//        Cell prev = head;
//        Cell[] rowCols = new Cell[81];
//        Cell[] colCols = new Cell[81];
//        Cell[] gridCols = new Cell[81];
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                Cell header = new Cell(10 * i + j, 0, null, null, null, null);
//                rowCols[i * 9 + j] = header;
//                header.up = header.down = header;
//
//                header.left = prev;
//                prev.right = header;
//                header.right = head;
//                head.left = header;
//                prev=header;
//            }
//
//            for (int j = 0; j < 9; j++) {
//                Cell header = new Cell(100 * i + j, 0, null, null, null, null);
//                header.up = header.down = header;
//                colCols[i * 9 + j] = header;
//
//                header.left = prev;
//                prev.right = header;
//                header.right = head;
//                head.left = header;
//                prev=header;
//            }
//
//
//            for (int j = 0; j < 9; j++) {
//                Cell header = new Cell(1000 * i + j, 0, null, null, null, null);
//                header.up = header.down = header;
//                gridCols[i * 9 + j] = header;
//
//                header.left = prev;
//                prev.right = header;
//                header.right = head;
//                head.left = header;
//                prev=header;
//            }
//        }
//
//        return head;
//    }
//
    public static void solveSudoku(int[][] sudoku) {

    }

    public static void solveMatrix(boolean useHeuristic, boolean findFirstSolution, int[][] matrix) {

        Cell head = createGrid(matrix);

        List<List<Cell>> solutions = coverages(head, useHeuristic, findFirstSolution);

        System.out.println("Total solutions: " + solutions.size());

        int index = 0;
        for (List<Cell> answer: solutions) {
            System.out.println("solution " + index++ + ":");
            for (Cell rowHeader: answer) {
                System.out.println("row: " + rowHeader.val);
            }
        }
    }
}
