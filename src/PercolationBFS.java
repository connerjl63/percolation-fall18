import java.awt.List;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Conner Lewis
 * 12/4/18
 * Comp Sci 201
 */
public class PercolationBFS extends PercolationDFSFast {

	public PercolationBFS(int n) {
		super(n);
	}

	@Override
	protected void dfs(int row, int col) {
		//This method creates a queue and sets the newly openned cell to full, then it runs through
		//and fills any cells next to it that are open and not full, adding them to the queue and
		//then running through the same check of all adjacent cells with themselves
		Queue<Integer> cells = new LinkedList();
		int size = myGrid.length;
		if (! inBounds(row,col)) return;

		myGrid[row][col] = FULL;
		cells.add(row*size + col);

		while(!cells.isEmpty()) {
			int i = cells.element();
			int currow = i/size;
			int curcol = i%size;
			
			
			if(inBounds(currow+1,curcol) && isOpen(currow+1,curcol) && ! isFull(currow+1, curcol)) {
				myGrid[currow+1][curcol] = FULL;
				cells.add((currow+1)*size + curcol);
			}
			if(inBounds(currow-1,curcol) && isOpen(currow-1, curcol) && ! isFull(currow-1, curcol)) {
				myGrid[currow-1][curcol] = FULL;
				cells.add((currow-1)*size + curcol);
			}
			if(inBounds(currow,curcol-1) && isOpen(currow, curcol-1) && ! isFull(currow, curcol-1)) {
				myGrid[currow][curcol-1] = FULL;
				cells.add((currow)*size + (curcol-1));
			}
			if(inBounds(currow, curcol+1) && isOpen(currow, curcol+1) && ! isFull(currow, curcol+1)) {
				myGrid[currow][curcol+1] = FULL;
				cells.add(currow*size + (curcol+1));
			}
			cells.remove(i);

		}
	}
}
