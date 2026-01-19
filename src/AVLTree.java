import java.util.ArrayList;


/**
 * A modified AVL Tree implementation that organizes Parking Lots based on capacity constraints.
 * Supports standard rotation operations to maintain O(log N) height balance.
 */
public class AVLTree {
    private Node root;


    private Node rightRotate(Node node) {

        Node leftern = node.left;
        Node lefternRightern = leftern.right;

        // Update parent pointers.
        leftern.parent = node.parent;
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = leftern;
            } else {
                node.parent.right = leftern;
            }
        } else {
            root = leftern;
        }

        // Rotate.
        leftern.right = node;
        node.left = lefternRightern;

        // Update parent references.
        node.parent = leftern;
        if (lefternRightern != null) {
            lefternRightern.parent = node;
        }

        // Update heights.
        updateHeight(node);
        updateHeight(leftern);

        return leftern;
    }


    private Node leftRotate(Node node) {

        Node rightern = node.right;
        Node rightenLeftern = rightern.left;

        //Update parent pointers.
        rightern.parent = node.parent;
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = rightern;
            } else {
                node.parent.right = rightern;
            }
        } else {
            root = rightern;
        }

        //Rotate.
        rightern.left = node;
        node.right = rightenLeftern;

        // Update parent references
        node.parent = rightern;
        if (rightenLeftern != null) {
            rightenLeftern.parent = node;
        }

        // Update heights
        updateHeight(node);
        updateHeight(rightern);

        return rightern;
    }


    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }


    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    // Updates the height of a node based on its children's heights.
    private void updateHeight(Node node) {
        int leftHeight = 0;
        int rightHeight = 0;

        if (node.left != null) {
            leftHeight = node.left.height;
        }
        if (node.right != null) {
            rightHeight = node.right.height;
        }
        node.height = Math.max(leftHeight, rightHeight) + 1;
    }


    //Finds the node with minimum value in the given subtree.
    private Node findMin(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }


    // Finds the smaller biggest capacity ParkingLot.
    // Also checks if it is full. If full goes to the next one.
    public ParkingLot smallerBiggest(int capacity) {

        // A candidate is the next smaller biggest capacity ParkingLot.
        ParkingLot candidate = smallerBiggestCandidate(capacity);

        // Check if the candidate is full or not.
        while (candidate != null) {
            if (isNotFull(candidate)) {
                return candidate;
            } else {
                candidate = smallerBiggestCandidate(candidate.getCapacity());
            }
        }
        return null;
    }


    // Finds the smaller biggest capacity ParkingLot.
    public ParkingLot smallerBiggestCandidate(int capacity1) {
        return smallerBiggestHelper(root, capacity1);
    }

    private ParkingLot smallerBiggestHelper(Node node, int capacity) {
        // No ParkingLot found.
        if (node == null) {
            return null;
        }

        // If the current node's ParkingLot capacity is greater than or equal to capacity1
        if (node.parkingLot.getCapacity() >= capacity) {
            return smallerBiggestHelper(node.left, capacity);
        } else {

            // If the current node's capacity is less than capacity1
            ParkingLot rightResult = smallerBiggestHelper(node.right, capacity);

            if (rightResult != null) {
                return rightResult;
            } else {
                return node.parkingLot;
            }
        }
    }


    // Finds the smallest bigger ParkingLot. Also checks if it's ready section is not empty.
    public ParkingLot smallestBiggerHasReady(int capacity) {
        ParkingLot bestCandidate = smallestBiggerCandidate(capacity);
        while (bestCandidate != null) {
            if (hasReady(bestCandidate)) {
                return bestCandidate;
            }
            // Find the next smallest larger node.
            bestCandidate = smallestBiggerCandidate(bestCandidate.getCapacity());
        }
        return null;
    }



    // Finds the smallest bigger ParkingLot. Also checks if it's waiting section is not empty.
    public ParkingLot smallestBiggerHasWaiting(int capacity) {
        ParkingLot bestCandidate = smallestBiggerCandidate(capacity);
        while (bestCandidate != null) {
            if (hasWaiting(bestCandidate)) {
                return bestCandidate;
            }
            // Find the next smallest larger node
            bestCandidate = smallestBiggerCandidate(bestCandidate.getCapacity());
        }
        return null;
    }


    // Finds the smallest bigger capacity ParkingLot.
    public ParkingLot smallestBiggerCandidate(int capacity) {
        Node current = root;
        ParkingLot bestCandidate = null;


        // Traverse the tree
        while (current != null) {
            if (current.parkingLot.getCapacity() > capacity) {
                // Found a candidate larger than the desired capacity
                if (bestCandidate == null || current.parkingLot.getCapacity() < bestCandidate.getCapacity()) {
                    bestCandidate = current.parkingLot;
                }
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return bestCandidate;

    }

    // Return true if the given ParkingLot is not full.
    private boolean isNotFull(ParkingLot parkingLot){
        if (parkingLot.getTruckLimit() == parkingLot.getOccupiedCapacity()){
            return false;
        } else {
            return true;
        }
    }


    // Returns true if the given ParkingLot's waiting section is not empty.
    private boolean hasWaiting(ParkingLot parkingLot){
        if (parkingLot.waiting.size != 0){
            return true;
        }
        return false;
    }


    // Returns true if the given ParkingLot's ready section is not empty.
    private boolean hasReady(ParkingLot parkingLot){
        if (parkingLot.ready.size != 0){
            return true;
        }
        return false;
    }


    //creates parking lots.
    public void create_parking_lot(int capacity, int truckLimit){
        ParkingLot newParkingLot = new ParkingLot(capacity, truckLimit);
        insert(newParkingLot);
    }


    //inserts a ParkingLot to the avl tree.
    public void insert(ParkingLot parkingLot) {
        // If tree is empty create root node.
        if (root == null) {
            root = new Node(parkingLot);
            return;
        }

        Node current = root;
        Node parent = null;

        // Find the correct position to insert starting from the root.
        while (current != null) {
            parent = current;
            if (parkingLot.getCapacity() < current.parkingLot.getCapacity()) {
                current = current.left;
            } else if (parkingLot.getCapacity() > current.parkingLot.getCapacity()) {
                current = current.right;
            } else {
                // Duplicate capacity value
                return;
            }
        }

        Node newNode = new Node(parkingLot);
        newNode.parent = parent;

        // Link the parent to the new node
        if (parkingLot.getCapacity() < parent.parkingLot.getCapacity()) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        rebalanceAfterInsertion(newNode);
    }


    // Rebalances the tree.
    private void rebalanceAfterInsertion(Node node) {
        Node current = node;

        while (current != null) {

            updateHeight(current);

            // Calculate balance factor
            int balance = getBalance(current);

            // Perform rotations based on balance factor and node positions
            if (balance > 1 && node.parkingLot.getCapacity() < current.left.parkingLot.getCapacity()) {
                // Left left case
                current = rightRotate(current);
            }
            else if (balance < -1 && node.parkingLot.getCapacity() > current.right.parkingLot.getCapacity()) {
                // Right right case
                current = leftRotate(current);
            }
            else if (balance > 1 && node.parkingLot.getCapacity() > current.left.parkingLot.getCapacity()) {
                // Left right case
                current.left = leftRotate(current.left);
                current = rightRotate(current);
            }
            else if (balance < -1 && node.parkingLot.getCapacity() < current.right.parkingLot.getCapacity()) {
                // Right left case
                current.right = rightRotate(current.right);
                current = leftRotate(current);
            }

            // Move up to parent for next iteration
            current = current.parent;
        }
    }


    // Distributes load.
    public String load(int capacity, int loadAmount) {
        //Output to return and write later.
        String output = "";

        int loadLeft = loadAmount;

        // Is true for the first iteration.
        boolean firstIteration = true;

        while (loadLeft != 0) {

            ParkingLot lotChosen;
            Node node = root;

            // Find the node / move on to the next node
            while (node != null && node.parkingLot.getCapacity() != capacity) {
                if (capacity < node.parkingLot.getCapacity()) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }

            // If a node is found and it's ParkingLot's ready section is not empty.
            if (node != null && hasReady(node.parkingLot)) {
                lotChosen = node.parkingLot;
            } else {

                // Move on to next candidate.
                lotChosen = smallestBiggerHasReady(capacity);
            }

            // If no ParkingLot is found.
            if (lotChosen == null) {

                // If condition is needed as no ParkingLot may be found after some distribution.
                if (firstIteration) {
                    return "-1";
                }
                break;

            } else {

                // Filling trucks on a chosen lot.
                while (hasReady(lotChosen)){
                    Truck truckToBeFilled = lotChosen.ready.peek();
                    int howMuchToFill;
                    int space = truckToBeFilled.getCapacity() - truckToBeFilled.getLoad();


                    // Calculate how much to fill.
                    if (space < loadLeft) {
                        howMuchToFill = Math.min(lotChosen.getCapacity(), space);
                    } else {
                        howMuchToFill = Math.min(loadLeft, lotChosen.getCapacity());
                    }

                    if (howMuchToFill == 0) {
                        break;
                    }

                    // Fill the truck

                    truckToBeFilled.setLoad(truckToBeFilled.getLoad() + howMuchToFill);
                    loadLeft = loadLeft - howMuchToFill;

                    lotChosen.ready.dequeue();
                    lotChosen.setOccupiedCapacity(lotChosen.getOccupiedCapacity() - 1);

                    // Empty the truck if it is full
                    if (truckToBeFilled.getCapacity() == truckToBeFilled.getLoad()) {
                        truckToBeFilled.setLoad(0);
                    }

                    int whereAdded = reAdd_truck(truckToBeFilled);

                    // Output
                    if (!firstIteration) {
                        output = output + " - ";
                    }
                    output = output + Integer.toString(truckToBeFilled.getId());
                    output = output + " ";
                    output = output + Integer.toString(whereAdded);


                    firstIteration = false;
                }
            }
        }
        return output;
    }

    // Adds the truck back, returns the ParkingLot's capacity, if non-existent returns null.
    public int reAdd_truck(Truck truck) {

        int space = truck.getCapacity() - truck.getLoad();

        ParkingLot lotChosen;
        Node node = root;

        // Find the node
        while (node != null && node.parkingLot.getCapacity() != space) {
            if (space < node.parkingLot.getCapacity()) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        // If the node doesn't exist or if it is full move on to the next.
        if (node == null || !isNotFull(node.parkingLot)) {
            lotChosen = smallerBiggest(space);
        } else {
            lotChosen = node.parkingLot;
        }

        // If there is no available ParkingLot.
        if (lotChosen == null) {
            return -1;

        } else {

            // Found the ParkingLot now add the truck.
            lotChosen.setOccupiedCapacity(lotChosen.getOccupiedCapacity() + 1);
            lotChosen.waiting.enqueue(truck);
            truck.setInLot(lotChosen);
            return lotChosen.getCapacity();
        }
    }


    public String add_truck(int truck_id, int capacity) {
        Truck truck = new Truck(truck_id, capacity);

        ParkingLot lotChosen;
        Node node = root;

        // Find the node
        while (node != null && node.parkingLot.getCapacity() != capacity) {
            if (capacity < node.parkingLot.getCapacity()) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        // Found it, if full or non-existent go to next one
        if (node == null || !(isNotFull(node.parkingLot))) {
            lotChosen = smallerBiggest(capacity);
        } else {
            lotChosen = node.parkingLot;
        }

        if (lotChosen == null){
            return String.valueOf(-1);
        } else {
            // Found the lot now add the truck.
            lotChosen.setOccupiedCapacity(lotChosen.getOccupiedCapacity() + 1);
            lotChosen.waiting.enqueue(truck);
            truck.setInLot(lotChosen);

            return String.valueOf(lotChosen.getCapacity());
        }

    }

    // Finds the ParkingLot that has the capacity.
    // If not found moves to the smallest larger capacity ParkingLot.
    // Moves the first waiting truck to the ready section.
    public String ready(int capacity) {
        ParkingLot lotChosen;
        Node node = root;

        // Find the node
        while (node != null && node.parkingLot.getCapacity() != capacity) {
            if (capacity < node.parkingLot.getCapacity()) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        // If node is not found or it has no element in the waiting section.
        if (node == null || !(hasWaiting(node.parkingLot))) {
            lotChosen = smallestBiggerHasWaiting(capacity);
        } else {
            lotChosen = node.parkingLot;
        }

        // If no ParkingLot is found.
        if (lotChosen == null){
            return String.valueOf(-1);

        } else {
            Truck truckMoved = lotChosen.waiting.dequeue();
            lotChosen.ready.enqueue(truckMoved);

            return truckMoved.getId() + " " + truckMoved.getInLot().getCapacity();
        }

    }


    // finds and deletes the node with the given capacity.
    public void delete(int capacity) {
        if (root == null) {
            return;
        }

        // Find the node to delete
        Node nodeToDelete = root;
        while (nodeToDelete != null && nodeToDelete.parkingLot.getCapacity() != capacity) {
            if (capacity < nodeToDelete.parkingLot.getCapacity()) {
                nodeToDelete = nodeToDelete.left;
            } else {
                nodeToDelete = nodeToDelete.right;
            }
        }

        if (nodeToDelete == null) {
            return; // Node not found
        }

        // Store parent before deletion for rebalancing
        Node parentBeforeDeletion = nodeToDelete.parent;

        // If node to delete has no children or one child.
        if (nodeToDelete.left == null || nodeToDelete.right == null) {

            Node newChild;
            if (nodeToDelete.left != null) {
                newChild = nodeToDelete.left;

            } else {
                newChild = nodeToDelete.right;
            }

            if (newChild != null) {
                newChild.parent = nodeToDelete.parent;
            }

            // If it has no parents it should be the root.
            if (nodeToDelete.parent == null) {
                root = newChild;
            } else {
                if (nodeToDelete.parent.left == nodeToDelete) {
                    nodeToDelete.parent.left = newChild;
                } else {
                    nodeToDelete.parent.right = newChild;
                }
            }
        }
        // If node to delete has two children.
        else {
            // Finds the minimum value in right subtree.
            Node nextMin = findMin(nodeToDelete.right);

            // Store parent of successor for rebalancing.
            parentBeforeDeletion = nextMin.parent;

            nodeToDelete.parkingLot = nextMin.parkingLot;

            // Remove successor
            if (nextMin.parent == nodeToDelete) {
                nodeToDelete.right = nextMin.right;
                if (nextMin.right != null) {
                    nextMin.right.parent = nodeToDelete;
                }
            } else {
                nextMin.parent.left = nextMin.right;
                if (nextMin.right != null) {
                    nextMin.right.parent = nextMin.parent;
                }
            }
        }

        // Rebalance the tree.
        Node node = parentBeforeDeletion;
        while (node != null) {
            updateHeight(node);

            int balance = getBalance(node);

            // Left left case
            if (balance > 1 && getBalance(node.left) >= 0) {
                if (node == root) {
                    root = rightRotate(node);
                } else {
                    rightRotate(node);
                }
            }

            // Right right case
            if (balance < -1 && getBalance(node.right) <= 0) {
                if (node == root) {
                    root = leftRotate(node);
                } else {
                    leftRotate(node);
                }
            }

            // Left right case
            if (balance > 1 && getBalance(node.left) < 0) {
                node.left = leftRotate(node.left);
                if (node == root) {
                    root = rightRotate(node);
                } else {
                    rightRotate(node);
                }
            }

            // Right left case
            if (balance < -1 && getBalance(node.right) > 0) {
                node.right = rightRotate(node.right);
                if (node == root) {
                    root = leftRotate(node);
                } else {
                    leftRotate(node);
                }
            }

            node = node.parent;
        }
    }

    // Count the trucks in the ParkingLots with a higher capacity.
    public String count(int capacity) {
        int totalCount = 0;

        // Arraylist of the ParkingLots with a higher capacity.
        ArrayList<ParkingLot> lotsToCount = this.inOrderModified(capacity);

        // Adjusting the list. It holds extra parent node values.
        int pointer = 0;
        for (ParkingLot lot : lotsToCount) {
            if (lot.getCapacity() <= capacity) {
                pointer++;
            } else {
                break;
            }
        }

        // Counting the trucks.
        for (int i = pointer; i < lotsToCount.size(); i++) {
            totalCount = totalCount + lotsToCount.get(i).waiting.size + lotsToCount.get(i).ready.size;
        }

        // Output
        return Integer.toString(totalCount);
    }


    // A new version of the recursive inorder traversal where it doesn't go to the left child if:
    // the current node has a larger capacity.
    public ArrayList<ParkingLot> inOrderModified(int capacity) {
        ArrayList<ParkingLot> arrayList = new ArrayList<>();
        return inOrderModified(root, capacity, arrayList);
    }

    private ArrayList<ParkingLot> inOrderModified(Node node, int capacity, ArrayList<ParkingLot> arrayList) {
        if (node != null) {
            if (node.parkingLot.getCapacity() > capacity) {
                inOrderModified(node.left, capacity, arrayList);
            }
            arrayList.add(node.parkingLot);

            inOrderModified(node.right, capacity, arrayList);
        }
        return arrayList;
    }
}

