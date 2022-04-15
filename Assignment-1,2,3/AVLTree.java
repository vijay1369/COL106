// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        if(this == null){
            return null;
        }
        AVLTree node = new AVLTree(address, size, key);
        AVLTree temp = this;
        AVLTree root = root(temp);
        if(root.right == null){
            root.right = node;
            node.parent = root;
            return node;
        }
        root = root.right;
        AVLTree parent = root.parent;
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
        if(parent.parent == null){
            return node;
        }
        reBalance(node);
        
        return node;
    }

    public boolean Delete(Dictionary e)
    {
        AVLTree temp = this;
        AVLTree root = root(temp);
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
    public AVLTree Find(int k, boolean exact)
    { 
        AVLTree temp = this;
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
            AVLTree first1 = temp.getFirst();
            while(first1 != null && first1.key != k){
                first1 = first1.getNext();
            }
            return first1;
        }
        else{
            AVLTree first = temp.getFirst();
            while(first != null && first.key < k){
                first = first.getNext();
            }
            return first;
        }
    }
    
    public AVLTree getFirst()
    { 
        AVLTree temp = this;
        if(temp.parent == null && temp.right == null){
            return null;
        }
        AVLTree root = root(temp);
        root = root.right;
        while(root.left != null){
            root = root.left;
        }
        return root;
    }
    
    public AVLTree getNext()
    {
        AVLTree temp = this;
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
        AVLTree root = this;
        if(root == null){
            return true;
        }
        if(root.parent == null){
            root = root.right;
        }
        if(root == null){
            return true;
        }
        if(root.right != null){
            if(root.key > minimum(root.right).key){
                return false;
            }
            if((root.right).parent != root){
                return false;
            }
            if(height(root.left) - height(root.right) > 1 || height(root.right) - height(root.left) > 1){
                return false;
            }
        }
        if(root.left != null){
            if(root.key < maximum(root.left).key){
                return false;
            }
            if((root.left).parent != root){
                return false;
            }
            if(height(root.left) - height(root.right) > 1 || height(root.right) - height(root.left) > 1){
                return false;
            }
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
    private int height(AVLTree temp){
        if(temp == null){
            return -1;
        }
        return temp.height;
    }
    private AVLTree root(AVLTree temp){
        while(temp.parent != null){
            temp = temp.parent;
        }
        return temp;
    }
    private void reBalance(AVLTree temp){
        if(temp == null || temp.parent == null){
            return;
        }
        
        if((height(temp.left) - height(temp.right)) > 1){
            AVLTree x = temp.left;
            if(height(x.left) > height(x.right)){
                leftleftRotate(temp, x);
            }
            else{
                leftrightRotate(temp, x, x.right);
            }
        }
        else if((height(temp.left) - height(temp.right)) < -1){
            AVLTree x = temp.right;
            if(height(x.right) > height(x.left)){
                rightrightRotate(temp, x);
            }
            else{
                rightleftRotate(temp, x, x.left);
            }
        }
        AVLTree temp1 = temp.parent;
        if(temp1.parent != null){
            temp1.height = Math.max(height(temp1.left), height(temp1.right)) + 1;
        }
        
        reBalance(temp.parent);
    }
    private void leftleftRotate(AVLTree y, AVLTree x){
        y.left = x.right;
        if(x.right != null){
            (x.right).parent = y; 
        }
        x.right = y;
        x.parent = y.parent;
        //AVLTree temp = x.parent;
        if(y == (y.parent).right){
            (y.parent).right = x;
        }
        else{
            (y.parent).left = x;
        }
        y.parent = x;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
    }
    private void rightrightRotate(AVLTree y, AVLTree x){
        y.right = x.left;
        if(x.left != null){
            (x.left).parent = y;
        }
        x.left = y;
        x.parent = y.parent;
        //AVLTree temp = x.parent;
        if(y == (y.parent).right){
            (y.parent).right = x;
        }
        else{
            (y.parent).left = x;
        
        }
        y.parent = x;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
    }   
    private void leftrightRotate(AVLTree y, AVLTree x, AVLTree z){
        rightrightRotate(x, z);
        leftleftRotate(y, y.left);
    }
    private void rightleftRotate(AVLTree y, AVLTree x, AVLTree z){
        leftleftRotate(x, z);
        rightrightRotate(y, y.right);
    }
    private void delete_now(){
        AVLTree temp = this;
        if(temp == null){
            return;
        }
        AVLTree x, y;
        if(temp.left == null || temp.right == null){
            y = temp;
        }
        else{
            y = minimum(temp.right);
        }
        AVLTree temp1;
        if(y.parent != temp){
            temp1 = y.parent;
        }
        else{
            temp1 = y;
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
        temp1.height = Math.max(height(temp1.left), height(temp1.right)) + 1;
        reBalance(temp1);
    }
    private AVLTree minimum(AVLTree temp){
        while(temp.left != null){
            temp = temp.left;
        }
        return temp;
    }
    private AVLTree successor(AVLTree temp){
        if(temp.right != null){
            return minimum(temp.right);
        }
        AVLTree y = temp.parent;
        while(y != null && temp == y.right){
            temp = y;
            y = y.parent;
        }
        return y;
    }
    private AVLTree maximum(AVLTree temp){
        while(temp.right != null){
            temp = temp.right;
        }
        return temp;
    }
}


