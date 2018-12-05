/*
 * Conner Lewis
 * 12/4/18
 * Comp Sci 201
 */
public class PercolationDFSFast extends PercolationDFS {
	public PercolationDFSFast(int n) {
		super(n);
	}
	
	@Override
	protected void updateOnOpen(int row, int col) {
		//This method simply returns true if any of the cells next to the openned cell is full
		//filling the newly openned cell
		boolean fill=false;
		if(row==0)
			fill=true;
		if(inBounds(row+1,col) && isFull(row+1,col))
			fill=true;
		if(inBounds(row-1,col) && isFull(row-1, col))
			fill=true;
		if(inBounds(row,col-1) && isFull(row, col-1))
			fill=true;
		if(inBounds(row, col+1) && isFull(row, col+1))
			fill=true;
		if(fill==true)
			dfs(row,col);
	}
}
