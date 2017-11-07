// Brian Jordan (bjj17)
public class PercolationUF implements IPercolate{
	// Instance Variables
	public boolean[][] myGrid;
	public IUnionFind myFinder;
	public int myOpenCount = 0;
	public int index;
	public int indexUp;
	public int indexDown;
	public int indexRight;
	public int indexLeft;
	public static final boolean Closed = false;
	public static final boolean OPEN = true;
	private final int VTOP;
	private final int VBOTTOM;
	// Constructors of PercolationUF objects
	public PercolationUF(int size, IUnionFind finder){
		VTOP = size * size;
		VBOTTOM = size * size + 1;
		myGrid = new boolean[size][size];
		for (int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				myGrid[i][j] = false;
			}
		}
		finder.initialize((size * size) + 2);
		myFinder = finder;
	}
	// Helper Methods
	// updateOnOpen Method -- calls IUnionFind union Method with surrounding cells that are in Bounds
	// and Open if the cell is in Bounds
	public void updateOnOpen(int row, int col) {
		if (! inBounds(row,col)){
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		else{
			int index = getIndex(row,col);
			if (inBounds(row - 1,col)){
				indexUp = getIndex(row - 1, col);
			}
			if (inBounds(row + 1,col)){
				indexDown = getIndex(row + 1, col);
			}
			if (inBounds(row,col - 1)){
				indexLeft = getIndex(row, col - 1);
			}
			if (inBounds(row,col + 1)){
				indexRight = getIndex(row, col + 1);
			}
			if (row == 0)
				myFinder.union(index, VTOP);
			if (row == (myGrid.length - 1))
				myFinder.union(index, VBOTTOM);
			if (inBounds(row - 1,col) && isOpen(row - 1,col))
				myFinder.union(index, indexUp);
			if (inBounds(row + 1, col) && isOpen(row + 1,col))
				myFinder.union(index, indexDown);
			if (inBounds(row, col - 1) && isOpen(row, col - 1))
				myFinder.union(index, indexLeft);
			if (inBounds(row, col + 1) && isOpen(row, col + 1))
				myFinder.union(index, indexRight);
			}
		}
	// getIndex Method -- returns unique integer value of cell
	public int getIndex(int row, int col){
		int index = (row * myGrid.length) + col;
		return index;
	}
	// inBounds Method -- returns true if the cell is in Bounds and false if it is not
	public boolean inBounds(int row, int col){
		if (row >= myGrid.length || row < 0 || col >= myGrid[0].length || col < 0){
			return false;
		}
		else {
			return true;
		}
	}
	// open Method -- iterates myOpenCount, opens the cell, and calls updateOnOpen if the cell is in Bounds
	@Override
	public void open(int row, int col){
		if (! inBounds(row,col)){
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		else{
			if (myGrid[row][col] == OPEN)
				return;
			myOpenCount += 1;
			myGrid[row][col] = OPEN;
			updateOnOpen(row,col);
		}
	}
	// isOpen Method -- checks to see if the cell is open if it is in Bounds
	@Override
	public boolean isOpen(int row, int col){
		if (! inBounds(row,col)){
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		else{
			return myGrid[row][col] == OPEN;
		}
	}
	// isFull Method -- calls IUnionFind connected method with VTOP if the cell is in Bounds
	@Override
	public boolean isFull(int row, int col){
		if (! inBounds(row,col)){
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		else{
			return myFinder.connected(getIndex(row,col),VTOP);
		}
	}
	// percolates Method -- calls IUnionFind connected method with VTOP and VBOTTOM
	@Override
	public boolean percolates(){
			return myFinder.connected(VBOTTOM, VTOP);
	}
	// numberOfOpenSites -- returns myOpenCount
	@Override
	public int numberOfOpenSites(){
		return myOpenCount;
	}
}
