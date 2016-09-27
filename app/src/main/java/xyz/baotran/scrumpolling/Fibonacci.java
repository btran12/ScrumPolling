package xyz.baotran.scrumpolling;

import java.util.ArrayList;

/**
 * Created by bao on 8/27/16.
 */
public class Fibonacci implements VotingSystemInterface{

    final int NUMBER_OF_DEFAULT_VALUES = 7;

    ArrayList<Integer> fibArray;

    public Fibonacci(){
        fibArray = new ArrayList<>();
        initFibArrayWithDefaultValues();
    }

    public void initFibArrayWithDefaultValues(){
        int tail = 0;
        int head = 1;

        // 1 2 3 5 8 13 21
        for (int i = 0; i < NUMBER_OF_DEFAULT_VALUES; i++){
            int cur = tail + head;
            fibArray.add(cur);
            tail = head;
            head = cur;
        }
    }

    @Override
    public void add (int value){
        int tail = fibArray.get(size()-2);
        int head = fibArray.get(size()-1);
        fibArray.add(tail + head);
    }

    @Override
    public void addValueAt(int index, int value) {
        fibArray.add(index, value);
    }

    @Override
    public void removeValueAt(int index) {
        fibArray.remove(index);
    }

    @Override
    public int getValueAt(int index){
        return fibArray.get(index);
    }

    @Override
    public int size(){
        return fibArray.size();
    }
}
