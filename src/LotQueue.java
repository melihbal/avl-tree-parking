
public class LotQueue {
    private static class QueueNode{
        Truck content;
        QueueNode nextNode = null;

        public QueueNode(Truck truck) {
            content = truck;
        }
    }
    QueueNode head;
    QueueNode tail;
    int size = 0;


    public void enqueue(Truck truck){
        QueueNode newNode = new QueueNode(truck);
        if (size == 0) {
           head = newNode;
        } else {
            tail.nextNode = newNode;
        }
        tail = newNode;
        size++;
    }

    public Truck dequeue(){
        Truck removed = head.content;
        head = head.nextNode;
        size--;
        if (size == 0){
            tail = null;
        }
        return removed;
    }

    public Truck peek(){
        return head.content;
    }


}
