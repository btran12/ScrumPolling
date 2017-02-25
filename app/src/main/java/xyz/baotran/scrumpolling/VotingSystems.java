package xyz.baotran.scrumpolling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bao on 8/27/16.
 */
public class VotingSystems implements VotingSystemInterface {
    ArrayList<List<String>> votingSystems_;

    List<String> fibonacci;
    List<String> shirtSizes;

    List<String> votingSystem;

    int systemCode; // 0 - fibonacci 1 - shirtSizes

    public VotingSystems(){
        fibonacci = Arrays.asList("1","2","3","5","8","13","21");
        shirtSizes = Arrays.asList("XS","S","M","L","XL");

        setVotingSystem(fibonacci);
        systemCode = 0;
    }

    public void setVotingSystem(List<String> votingSystem){
        this.votingSystem = votingSystem;
    }

    public void loadData(String strArray){
        switch(systemCode){
            case 0:
                fibonacci = Arrays.asList(strArray);
                break;
            case 1:
                shirtSizes = Arrays.asList(strArray);
                break;
        }
    }

    public void switchVotingSystem(int systemCode){
        switch(systemCode){
            case 0:
                setVotingSystem(fibonacci);
                this.systemCode = 0;
                break;
            case 1:
                setVotingSystem(shirtSizes);
                this.systemCode = 1;
                break;
            default:
                setVotingSystem(fibonacci);
                this.systemCode = 0;
                break;

        }
    }

    @Override
    public void add() {
        switch (systemCode){
            case 0:
                boolean listNotMaxed = this.size() < 10;

                if (listNotMaxed) {
                    int tail;
                    if (this.size() == 1) {
                        tail = 1;
                    } else {
                        tail = Integer.parseInt(fibonacci.get(fibonacci.size() - 2));
                    }

                    int head = Integer.parseInt(fibonacci.get(fibonacci.size() - 1));
                    fibonacci.add(tail + head + "");
                }
                break;
            case 1:
                break;
            default:
                break;

        }
    }

    public void remove(){
        switch (systemCode){
            case 0:
                if (fibonacci.size() > 0) {
                    fibonacci.remove(fibonacci.size()-1);
                }
                break;
            case 1:
                break;
            default:
                break;
        }

    }

    @Override
    public void addValueAt(int index, int value) {

    }

    @Override
    public void removeValueAt(int index) {

    }

    @Override
    public String getValueAt(int index) {
        switch (systemCode){
            case 0:
                return fibonacci.get(index);
            case 1:
                return shirtSizes.get(index);
            default:
                break;
        }
        return "";
    }

    @Override
    public int size() {
        switch (systemCode){
            case 0:
                return fibonacci.size();
            case 1:
                return shirtSizes.size();
            default:
                return 0;
        }
    }

    public String toString(){
        switch (systemCode){
            case 0:
                String str = fibonacci.toString();
                str = str.substring(1,fibonacci.toString().length()-1); //Remove front and end brackets []
                str = str.replaceAll("\\s","");
                return str;
            case 1:
                return "";
            default:
                break;
        }
        return "";
    }
}
