public class Truck {

    //keeps the node truck is in.
    private ParkingLot inLot;
    private int id;
    private int capacity;
    private int load;



    Truck(int truck_id, int capactiy) {
        id = truck_id;
        this.capacity = capactiy;
        load = 0;
    }

    public ParkingLot getInLot() {
        return inLot;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLoad() {
        return load;
    }


    public void setInLot(ParkingLot inLot) {
        this.inLot = inLot;
    }


    public void setLoad(int load) {
        this.load = load;
    }
}