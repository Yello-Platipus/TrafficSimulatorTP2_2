package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {
    @Override
    public List<Vehicle> dequeue(List<Vehicle> q) {
		return new LinkedList<Vehicle>(q);
    }
}
