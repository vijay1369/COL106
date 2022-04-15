// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        if(this == null){
            return null;
        }
        BSTree node = new BSTree(address, size, key);
        BSTree temp = this;
        BSTree root = root(temp);
        if(root.right == null){
            root.right = node;
            node.parent = root;
            return node;
        }
        root = root.right;
        BSTree parent = root.parent;
        while(root != null){
            parent = root;
            if(root.key < key){
                root = root.right;
            }
            else if(root.key > key){
                root = root.left;
            }
            else if(root.key == key && root.address > address){
                root = root.left;
            }
            else if(key == root.key && address > root.address){
                root = root.right;
            }
        }
        node.parent = parent;
        if(key < parent.key){
            parent.left = node;
        }
        else if(key > parent.key){
            parent.right = node;
        }
        else if(key == parent.key && address < parent.address){
            parent.left = node;
        }
        else if(key == parent.key && address > parent.address){
            parent.right = node;
        }
        return node;
    }

    public boolean Delete(Dictionary e)
    { 
        BSTree temp = this;
        BSTree root = root(temp);
        root = root.right;
        while(root != null){
            if(root.key > e.key){
                root = root.left;
            }
            else if(root.key < e.key){
                root = root.right;
            }
            else if(root.key == e.key){
                if(root.address == e.address && root.size == e.size){
                    root.delete_now();
                    return true;
                }
                else if(root.address > e.address){
                    root = root.left;
                }
                else if(root.address < e.address){
                    root = root.right;
                }
            }
        }
        return false;
    }
        
    public BSTree Find(int key, boolean exact)
    { 
        BSTree temp = this;
        if(temp == null){
            return null;
        }
        while(temp.parent != null){
            temp = temp.parent;
        }
        temp = temp.right;
        if(temp == null){
            return null;
        }
        
        if(exact == true){
            BSTree first1 = temp.getFirst();
            while(first1 != null && first1.key != key){
                first1 = first1.getNext();
            }
            return first1;
        }
        else{
            BSTree first = temp.getFirst();
            while(first != null && first.key < key){
                first = first.getNext();
            }
            return first;
        }
    }

    public BSTree getFirst()
    { 
        BSTree temp = this;
        if(temp.parent == null && temp.right == null){
            return null;
        }
        BSTree root = root(temp);
        root = root.right;
        while(root.left != null){
            root = root.left;
        }
        return root;
    }

    public BSTree getNext()
    { 
        BSTree temp = this;
        if(temp == null){
            return null;
        }
        if(temp.parent == null){
            return null;
        }
        return successor(temp);
    }

    public boolean sanity()
    { 
        BSTree root = this;
        if(root == null){
            return true;
        }
        if(root.right != null && root.key > minimum(root.right).key && (root.right).parent != root){
            return false;
        }
        if(root.left != null && root.key < maximum(root.left).key && (root.left).parent != root){
            return false;
        }
        if(root.left != null){
            if((root.left).sanity() == false){
                return false;
            }
        }
        if(root.right != null){
            if((root.right).sanity() == false){
                return false;
            }
        }
        return true;
    }
    private BSTree root(BSTree temp){
        while(temp.parent != null){
            temp = temp.parent;
        }
        return temp;
    }
    private void delete_now(){
        BSTree temp = this;
        if(temp == null){
            return;
        }
        BSTree x, y;
        if(temp.left == null || temp.right == null){
            y = temp;
        }
        else{
            y = minimum(temp.right);
        }
        if(y.left != null){
            x = y.left;
        }
        else{
            x = y.right;
        }
        if(x != null){
            x.parent = y.parent;
        }
        if(y == (y.parent).left){
            (y.parent).left = x;
        }
        else{
            (y.parent).right = x;
        }
        y.parent = null;
        y.left = null;
        y.right = null;
        if(y != temp){
            y.left = temp.left;
            y.right = temp.right;
            y.parent = temp.parent;
            if(temp.left != null){
                (temp.left).parent = y;
            }
            if(temp.right != null){
                (temp.right).parent = y;
            }
            
            if(temp == (temp.parent).left){
                (temp.parent).left = y;
            }
            else{
                (temp.parent).right = y;
            }
        }
    }
    private BSTree minimum(BSTree temp){
        while(temp.left != null){
            temp = temp.left;
        }
        return temp;
    }
    private BSTree maximum(BSTree temp){
        while(temp.right != null){
            temp = temp.right;
        }
        return temp;
    }
    private BSTree successor(BSTree temp){
        if(temp.right != null){
            return minimum(temp.right);
        }
        BSTree y = temp.parent;
        while(y != null && temp == y.right){
            temp = y;
            y = y.parent;
        }
        return y;
    }
}


 


