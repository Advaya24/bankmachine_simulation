package bankmachine;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackingFactory<T extends Identifiable> {
    private List<T> instances = new ArrayList<>();
    protected int nextID;

    /**
     * Add a list of instances to the factory
     *
     * @param instances the instances to add
     */
    public void extend(List<T> instances) {
        this.instances.addAll(instances);
        int max = 0;
        for (T t : this.instances) {
            if (t.getID() > max) {
                max = t.getID();
            }
        }
        this.nextID = max + 1;
    }

    public List<T> getInstances() {
        return this.instances;
    }

    /**
     * Add an instance to be tracked and increment the ID
     *
     * @param instance
     */
    protected void addInstance(T instance) {
        this.instances.add(instance);
        this.nextID++;
    }

    protected int getNextID() {
        return nextID;
    }

    /**
     * Test if the factory is storing an instance
     *
     * @param instance the instance to test
     * @return
     */
    public boolean contains(T instance) {
        return instances.contains(instance);
    }

}
