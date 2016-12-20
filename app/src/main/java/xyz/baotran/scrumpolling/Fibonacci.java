package xyz.baotran.scrumpolling;

import java.util.ArrayList;

/**
 * Created by bao on 8/27/16.
 */
public class Fibonacci implements VotingSystemInterface{

    final int NUMBER_OF_DEFAULT_VALUES = 7;
    final int MAXIMUM_COUNT_OF_VALUES = 15;

    ArrayList<Integer> fibArray;

    /**
     * Initialize object with default values
     */
    public Fibonacci(){
        fibArray = new ArrayList<>();
        initArrayWithDefaultValues();
    }

    /**
     * Initialize object with saved or existing values
     * @param _fibArray list of existing or saved values
     */
    public Fibonacci(ArrayList _fibArray){
        fibArray = _fibArray;
    }

    private void initArrayWithDefaultValues(){
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

    /**
     * Append the next fibonacci number in the sequence
     *
     */
    @Override
    public void add(){
        boolean listNotMaxed = this.size() < MAXIMUM_COUNT_OF_VALUES;

        if (listNotMaxed) {
            int tail;
            if (this.size() == 1) {
                tail = 1;
            } else {
                tail = fibArray.get(size() - 2);
            }

            int head = fibArray.get(size() - 1);
            fibArray.add(tail + head);
        }
    }

    /**
     * Remove the last value in array
     */
    public void remove(){
        if (fibArray.size() > 0) {
            fibArray.remove(fibArray.size()-1);
        }
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

    public String toString(){
        String str = fibArray.toString();
        str = str.substring(1,fibArray.toString().length()-1); //Remove front and end brackets []
        str = str.replaceAll("\\s","");
        return str;
    }
}
