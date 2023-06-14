package model;

import view.ModelListener;

import java.util.ArrayList;

// this class is used for observer design pattern
public abstract class Subject {

    protected ArrayList<ModelListener> modelListeners;

    public Subject() {
        this.modelListeners = new ArrayList<>();
    }
    public void addListener(ModelListener modelListener){
        this.modelListeners.add(modelListener);

    }

    public void notifyListeners(){
        for (ModelListener modelListener : modelListeners) {
            modelListener.update();
        }
    }





}
