package edu.wit.cs.comp2350;

// @Override tags tell the compiler that the method is implementing a method
// defined in an ancestor class

// TODO: document class
public class BST extends LocationContainer {
	
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
	
	@Override
	public DiskLocation find(DiskLocation d) {
		
	return find(d,root);

	}
	
	private DiskLocation findParent(DiskLocation d,DiskLocation curr, DiskLocation parent) {
		if(curr==null) {
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
		if(d.right!=null) {
			while(temp.left!=null) {
				temp=temp.left;
			}
		return temp;
		}
		else return UP(d);
	}
		
	
	
	@Override
	public DiskLocation prev(DiskLocation d) {
		DiskLocation temp = d.left;
		if(d.left!=null) {
			while(temp.right!=null) {
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
