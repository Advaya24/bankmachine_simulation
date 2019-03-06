package bankmachine;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackingFactory<T extends Identifiable> {
    private List<T> instances = new ArrayList<>();
    protected int nextID;
    public void extend(List<T> instances){
        this.instances.addAll(instances);
        int max=0;
        for (T t:this.instances){
            if (t.getID() > max){
                max = t.getID();
            }
        }
        this.nextID = max + 1;
    }
    public List<T> getInstances(){
        return this.instances;
    }
    protected void addInstance(T instance){
        this.instances.add(instance);
        this.nextID++;
    }
    protected int getNextID(){ return nextID; }
    public boolean contains(T instance){
        return instances.contains(instance);
    }

}
