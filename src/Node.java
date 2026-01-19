public class Node {
    ParkingLot parkingLot;
    Node parent;
    public int height;
    Node left;
    Node right;

    Node(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        height = 1;
    }
}
