package edu.wit.cs.comp2350;

// TODO Document class
public class RBTree extends LocationContainer {

	/**
	 * sets a disk location's color to red.
	 * 
	 * Use this method on fix-insert instead of directly
	 * coloring nodes red to avoid setting nil as red.
	 */
	private void setRed(DiskLocation z) {
		if (z != nil)
			z.color = RB.RED;
	}
	private void setBlack(DiskLocation z) {
		if (z != nil)
			z.color = RB.BLACK;
	}

	private void rotateLeft(DiskLocation x) {
		DiskLocation y = x.right;
		x.right=y.left;
		if(!y.left.equals(nil)) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if(x.parent.equals(nil)) {
			root=y;
		}
		else if(x.equals(x.parent.left)) {
			x.parent.left=y;
		}
		else {x.parent.right=y;}
			y.left=x;
			x.parent=y;
		
	}
	private void rotateRight(DiskLocation x) {
		DiskLocation y = x.left;
		x.left=y.right;
		if(!y.right.equals(nil)) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if(x.parent.equals(nil)) {
			root=y;
		}
		else if(x.equals(x.parent.right)) {
			x.parent.right=y;
		}
		else {x.parent.left=y;}
			y.right=x;
			x.parent=y;
		
	}
	
	private DiskLocation UP(DiskLocation d) {
		DiskLocation p = d.parent;
		
		if (p.equals(nil) || d.equals(p.left)) {
			return p;
		}
		
		else
		return	UP(p);
		
	}
	private DiskLocation UPP(DiskLocation d) {
		DiskLocation p = d.parent;
		
		if (p.equals(nil) || d.equals(p.right)) {
			return p;
		}
		
		else
		return	UPP(p);
		
	}


	@Override
	public DiskLocation find(DiskLocation d) {
		
	return find(d,root);

	}
	
	private DiskLocation find(DiskLocation d,DiskLocation curr) {
			
		if (d.equals(curr))
			return curr;
		else if(d.isGreaterThan(curr)) {
			return find(d,curr.right);
		}
		else if(curr.isGreaterThan(d)) {
				return find(d,curr.left);			
		}
		else {
			return nil;
		}
	}

	private DiskLocation findParent(DiskLocation d,DiskLocation curr, DiskLocation parent) {
		if(curr==null) {
			return parent;
		}
		if(curr.equals(nil)) {
			return parent;
		}
		if(curr.isGreaterThan(d)) {
			return findParent(d,curr.left,curr);
		}
		else
			return findParent(d,curr.right,curr);
	}

	@Override
	public DiskLocation next(DiskLocation d) {
		DiskLocation temp = d.right;
		if(!d.right.equals(nil)) {
			while(!temp.left.equals(nil)) {
				temp=temp.left;
			}
		return temp;
		}
		else return UP(d);
	}

	@Override
	public DiskLocation prev(DiskLocation d) {
		DiskLocation temp = d.left;
		if(!d.left.equals(nil)) {
			while(!temp.right.equals(nil)) {
				temp=temp.right;
			}
		return temp;
		}
		else return UPP(d);
	}

	@Override
	public void insert(DiskLocation d) {
			d.parent = findParent(d,root,nil);
			if (d.parent.equals(nil)) {
				root=d;
			}
			else {
				if(d.parent.isGreaterThan(d)) {
					d.parent.left=d;
				}
				else
					d.parent.right=d;
			}
		d.left=nil;
		d.right=nil;
		setRed(d);
		FixInsert(d);
	}

	private void FixInsert(DiskLocation d) {
		while(d.parent.color == RB.RED) {
			if (d.parent.equals(d.parent.parent.left)) {
				DiskLocation y = d.parent.parent.right;
				if (y.color == RB.RED) {
					setBlack(d.parent);
					setBlack(y);
					setRed(d.parent.parent);
					d=d.parent.parent;
				}
				else {
					if (d.equals(d.parent.right)) {
						d=d.parent;
						rotateLeft(d);
					}
					setBlack(d.parent);
					setRed(d.parent.parent);
					rotateRight(d.parent.parent);
				}
			}
			else {
				DiskLocation y = d.parent.parent.left;
				if (y.color == RB.RED) {
					setBlack(d.parent);
					setBlack(y);
					setRed(d.parent.parent);
					d=d.parent.parent;
				}
				else {
					if (d.equals(d.parent.left)) {
						d=d.parent;
						rotateRight(d);
					}
					setBlack(d.parent);
					setRed(d.parent.parent);
					rotateLeft(d.parent.parent);
				}
			}
		}
		setBlack(root);
	}
	
	private int height(DiskLocation d) {
		if(d==null) {
			return 0;
		}
		else {
			int lDepth = height(d.left);
            int rDepth = height(d.right);
  
            /* use the larger one */
            if (lDepth > rDepth+1)
                return (lDepth);
             else
                return (rDepth+1);
		}
	}
	
	@Override
	public int height() {
	return height(root)-1;	
	}

}