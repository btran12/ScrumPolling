package xyz.baotran.scrumpolling;

import java.util.ArrayList;

/**
 * Created by bao on 10/31/16.
 */
public class ShirtsSize implements VotingSystemInterface {
    final int NUMBER_OF_DEFAULT_VALUES = 3;
    final int MAXIMUM_COUNT_OF_VALUES = 5;
    final String[] SHIRT_SIZES_ARRAY = {"S","M","L","XL","XXL"};
    ArrayList<String> list;

    public ShirtsSize(){
        list = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DEFAULT_VALUES; i++){
            list.add(SHIRT_SIZES_ARRAY[i]);
        }
    }

    public ShirtsSize(ArrayList _list){
        list = _list;
    }

    @Override
    public void add() {
        boolean listNotMaxed = this.size() < MAXIMUM_COUNT_OF_VALUES;

        if (listNotMaxed){
            list.add(SHIRT_SIZES_ARRAY[list.size()]);
        }
    }

    @Override
    public void addValueAt(int index, int value) {

    }

    @Override
    public void removeValueAt(int index) {

    }

    @Override
    public int getValueAt(int index) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }
}
