package com.main.Thread;

public class ThreadID {
    
    private int baseID;
    
    public ThreadID(int baseID){
        this.baseID = baseID;
    }
    
    public int next(){
        return baseID++;
    }
    
    public int getCurrentID(){
        return baseID;
    }

}
