package algo

import "fmt"

type node struct {
	left, right, up, down *node
	columnNode *columnNode
	row int
}

func newNode(cn *columnNode, row int) *node{
	n := node{columnNode: cn, row: row}
	p := &n
	n.left, n.right, n.up, n.down = p, p, p, p
	return p
}

func (this *node) unlinkLR() {
	this.left.right = this.right;
	this.right.left = this.left;
}

func (this *node) relinkLR() {
	this.left.right = this;
	this.right.left = this;
}

func (this *node) unlinkUD() {
	this.up.down = this.down;
	this.down.up = this.up;
}

func (this *node) relinkUD() {
	this.up.down = this;
	this.down.up = this;
}

func (this *node) String() string {
    return fmt.Sprintf("Row: %d", this.row )
}

type columnNode struct {
	node
	size int
	name string
	primary bool
}

func (this *columnNode) String() string {
	return fmt.Sprintf("column: %s", this.name)
}

func newColumnNode(name string, primary bool) (*columnNode) {
	cn := columnNode{*newNode(nil, -1), 0, name, primary}
	cn.columnNode = &cn
	return &cn
}

func (this *columnNode) cover() {
	this.unlinkLR();

	for colDataObj := this.down; colDataObj != &this.node; colDataObj = colDataObj.down {
		for rowDataObj := colDataObj.right; rowDataObj != colDataObj; rowDataObj = rowDataObj.right {
			rowDataObj.unlinkUD();
			rowDataObj.columnNode.size--;
		}
	}
}

func (this *columnNode) uncover() {
	for colDataObj := this.up;  colDataObj != &this.node;  colDataObj = colDataObj.up {
		for rowDataObj := colDataObj.left; rowDataObj != colDataObj; rowDataObj = rowDataObj.left {
			rowDataObj.columnNode.size++;
			rowDataObj.relinkUD();
		}
	}
	this.relinkLR();
}


func fromMatrix(grid [][]int, removedRows []int, primaryCols []int) *columnNode {
	header := newColumnNode("header", true)

	rowCount := len(grid);
	columnCount := len(grid[0])

	colNodes := make([]columnNode, columnCount);
	for j := 0; j < columnCount; j++ {
		isPrimary := true
		for _, col := range primaryCols {
			if col == j {
				isPrimary = false
				break
			}
		}
		colNode := newColumnNode(""+string(j), isPrimary)
		colNodes[j] = *colNode
	}

	rowToRowNode := make(map[int]node)
	for i := 0; i < rowCount; i++ {
		rowNodes := make([]node, rowCount)
		for j := 0; j < columnCount; j++ {
			colNode := colNodes[j]
			if (grid[i][j] != 0) {
				node := newNode(nil, i);
				rowNodes = append(rowNodes, *node);
				node.columnNode = colNode.columnNode;
				node.up = &colNode.node
				node.down = colNode.down;
				node.up.down = node;
				node.down.up = node;
				colNode.columnNode.size++;
			}
		}
		if len(rowNodes) > 0 {
			rowToRowNode[i] = rowNodes[0]
			for k := 0; k < len(rowNodes); k++ {
				last := len(rowNodes) - 1
				if k > 0 {
					rowNodes[k].left = &rowNodes[k - 1]
				} else {
					rowNodes[k].left = &rowNodes[last]
				}
				if k < last {
					rowNodes[k].right = &rowNodes[k + 1];
				} else {
					rowNodes[k].right = &rowNodes[0];
				}
			}
		}
	}

	for i := len(colNodes) - 1; i >= 0; i-- {
		tmpNode := colNodes[i]
		if (tmpNode.size == 0) {
			colNodes = append(colNodes[:i], colNodes[i+1:]...)
		}
	}

	colNodes = append(colNodes, *header)
	for i := len(colNodes) - 1; i >= 0; i-- {
		tmpNode := colNodes[i]
		if i == 0 {
			tmpNode.left = &colNodes[len(colNodes) - 1].node
		} else {
			tmpNode.left = &colNodes[i - 1].node
		}
		if i == len(colNodes) - 1 {
			tmpNode.right = &colNodes[0].node
		} else {
			tmpNode.right = &colNodes[i + 1].node
		}
	}

	if len(removedRows) > 0 {
		for _, removedRow := range removedRows {
			if rowNode, ok := rowToRowNode[removedRow]; ok {
				for node := rowNode.left; node != &rowNode; node = node.left {
					node.columnNode.cover();
				}
				rowNode.columnNode.cover();
			}
		}
	}

	return header;
}

type AlgoX struct {
	answers [][]node
	currAnswer []node
	header columnNode
    useHeuristic bool
    findFirstSolution bool

}

func newAlgoX(h columnNode, heuristic bool, findFirstSolution bool) *AlgoX {
	this := AlgoX{}
	this.header = h
	this.useHeuristic = heuristic
	this.findFirstSolution = findFirstSolution
	return &this
}

func (this *AlgoX) search() {
	hasPrimaryCols := false
	for (node := (ColumnNode)header.right; node != header; node = (ColumnNode)node.right) {
if (node.primary) {
hasPrimaryCols = true;
break;
}
}
if (!hasPrimaryCols) {
answers.add((ArrayList)currAnswer.clone());
return;
}

ColumnNode selectedCol = (ColumnNode)header.right;
if (useHeuristic) {
int curr = Integer.MAX_VALUE;
ColumnNode col = (ColumnNode)header.right;
while (col != header && col.primary) {
if (col.size < curr) {
curr = col.size;
selectedCol = col;
}
col = (ColumnNode)col.right;
}
}

selectedCol.cover();

ColumnNode lastCovered = selectedCol;
for (Node colDataObj = selectedCol.down; colDataObj != selectedCol; colDataObj = colDataObj.down) {
currAnswer.add(colDataObj);

for (Node rowDataObj = colDataObj.right; rowDataObj != colDataObj; rowDataObj = rowDataObj.right) {
rowDataObj.columnNode.cover();
}

search();
if (findFirstSolution && answers.size() > 0) {
return;
}

Node lastNode = currAnswer.remove(currAnswer.size() - 1);
lastCovered = lastNode.columnNode;

for (Node rowDataObj = lastNode.left; rowDataObj != lastNode; rowDataObj = rowDataObj.left) {
rowDataObj.columnNode.uncover();
}
}

lastCovered.uncover();
}

public static List<List<Integer>> solveGrid(Collection<Collection<Integer>> grid, Collection<Integer> primaryCols,
List<Integer> removedRows, boolean useHeuristic, boolean findFirst)
{
int rowCount = grid.size();
int[][] gridArr = new int[rowCount][];
int colCount = grid.iterator().next().size();
int rowIndex = 0;
for (Collection<Integer> row: grid) {
gridArr[rowIndex] = new int[colCount];

int colIndex = 0;
for (Integer col: row) {
gridArr[rowIndex][colIndex] =  col;
colIndex++;
}

rowIndex++;
}

ColumnNode header = fromMatrix(gridArr, removedRows, primaryCols);
AlgoX algoX = new AlgoX(header, useHeuristic, findFirst);
algoX.search();
List<ArrayList<Node>> answers = algoX.answers;
List<List<Integer>> retval = new ArrayList<>();
for (ArrayList<Node> answer: answers) {
List<Integer> rows = new ArrayList<>();
retval.add(rows);
for (Node node: answer) {
rows.add(node.row);
}
}
return retval;
}

public static void main(String[] args) {
boolean useHeuristic;
if (args.length > 0) {
useHeuristic = "true".equalsIgnoreCase(args[0]);
}
else {
useHeuristic = true;
}

boolean findFirstSolution;
if (args.length > 1) {
findFirstSolution = "true".equalsIgnoreCase(args[1]);
}
else {
findFirstSolution = false;
}

int[][] grid = {
{0, 1, 1},
{1, 1, 0},
{0, 0, 1},
{1, 0, 0}
};
ColumnNode header = fromMatrix(grid, null, null);
AlgoX algoX = new AlgoX(header, useHeuristic, findFirstSolution);
algoX.search();

int index = 0;
for (List<Node> answer: algoX.answers) {
System.out.println("answer " + index++ + ":");
for (Node node: answer) {
System.out.println("row: " + node.row);
}
}
}
