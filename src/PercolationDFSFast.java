// Brian Jordan (bjj17)
public class PercolationDFSFast extends PercolationDFS{
	// Constructor of PercolationDFSFast Objects
	public PercolationDFSFast(int n){
		super(n);
	}
	// open Method -- calls the PercolationDFS open method if the cell is in bounds
	@Override
	public void open(int row, int col) {
		if (! inBounds(row,col)){
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		else{
			super.open(row, col);
		}
	}
	// isOpen Method -- calls the PercolationDFS isOpen method if the cell is in bounds
	@Override
	public boolean isOpen(int row, int col) {
		if (! inBounds(row,col)){
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		return super.isOpen(row, col);
	}
	// isFull Method -- calls the PercolationDFS isFull method if the cell is in bounds
	@Override
	public boolean isFull(int row, int col) {
		if (! inBounds(row,col)){
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		else{
			return super.isFull(row, col);
		}
	}
	// updateOnOpen Method -- calls the PercolationDFS dfs method on the surrounding cells
	// if they are in Bounds and full
	@Override
	protected void updateOnOpen(int row, int col) {
		if ((inBounds(row,col - 1) && isFull(row,col - 1)) || 
				(inBounds(row,col + 1) && isFull(row,col + 1)) || 
				(inBounds(row - 1, col) && isFull(row - 1,col)) || 
				(inBounds(row + 1, col) && isFull(row + 1, col)) || 
				row == 0){
			myGrid[row][col] = FULL;
			dfs(row,col - 1);
			dfs(row,col + 1);
			dfs(row - 1, col);
			dfs(row + 1, col);
		}
	}
}
