import java.util.Arrays;

/*
 * Conner Lewis
 * 12/4/18
 * Comp Sci 201
 */
public class PercolationUF implements IPercolate {
	//Creates all necessary class variables
	private final int VTOP;
	private final int VBOTTOM;
	protected boolean[][] myGrid;
	protected int myOpenCount;
	IUnionFind myFinder;
	
	//Creates a PercolationUF object with a IUnionFinder
	public PercolationUF(IUnionFind finder, int n) {
		//Creates a grid of booleans and sets a VTOP and VBOTTOM, and fills the grid with false
		myGrid = new boolean[n][n];
		myOpenCount = 0;
		VTOP = n*n;
		VBOTTOM = n*n+1;
		finder.initialize((n*n)+2);
		myFinder = finder;
		for (boolean[] row : myGrid)
			Arrays.fill(row, false);
	}
	
	@Override
	public void open(int row, int col) {
		//Checks to see if the cell is both inbound and not already open
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		if(isOpen(row, col)) {
			return;
		}
		//Sets the cell to true and increases open count by 1
		myGrid[row][col] = true;
		int size = myGrid.length;
		myOpenCount += 1;
		
		//Uses the finder to union the set of the new cell to any open cells adjacent to it
		//Or to the VTOP if in the top row or the VBOTTOM if in the bottom row
		if(inBounds(row+1,col) && isOpen(row+1,col)) {
			myFinder.union((row*size + col), ((row+1)*size + col));
		}
		if(inBounds(row-1,col) && isOpen(row-1, col)) {
			myFinder.union((row*size + col), ((row-1)*size + col));
		}
		if(inBounds(row,col-1) && isOpen(row, col-1)) {
			myFinder.union((row*size + col), (row*size + (col-1)));
		}
		if(inBounds(row, col+1) && isOpen(row, col+1)) {
			myFinder.union((row*size + col), (row*size + (col+1)));
		}
		if(row == 0) {
			myFinder.union((row*size + col), VTOP);
		}
		if(row == myGrid.length-1) {
			myFinder.union((row*size + col), VBOTTOM);
		}
	}

	@Override
	public boolean isOpen(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		//Returns the state of the cell if in bounds
		return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		//Checks to see if the open cell that is in bounds is connected by set to VTOP
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		int size = myGrid.length;
		
		if(myFinder.connected((row*size + col), VTOP)) return true;
		
		return false;
	}

	@Override
	public boolean percolates() {
		//Checks to see if VTOP is connected to VBOTTOM
		if(myFinder.connected(VTOP, VBOTTOM)) return true;
		
		return false;
	}

	@Override
	public int numberOfOpenSites() {
		//Returns the number of open cells
		return myOpenCount;
	}

	protected boolean inBounds(int row, int col) {
		//Checks if the cell is in the bounds of the length
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
}
