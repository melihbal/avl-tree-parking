public class ParkingLot {
    private int occupiedCapacity = 0;
    private int capacity;
    private int truckLimit;
    public LotQueue waiting = new LotQueue();
    public LotQueue ready = new LotQueue();


    public ParkingLot(int capacity, int truckLimit){
        this.capacity = capacity;
        this.truckLimit = truckLimit;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedCapacity() {
        return occupiedCapacity;
    }

    public int getTruckLimit() {
        return truckLimit;
    }


    public void setOccupiedCapacity(int occupiedCapacity) {
        this.occupiedCapacity = occupiedCapacity;
    }

}
