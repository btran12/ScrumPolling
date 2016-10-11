package xyz.baotran.scrumpolling;

/**
 * Created by bao on 9/6/16.
 */
public interface VotingSystemInterface {

    public void add();
    public void addValueAt(int index, int value);
    public void removeValueAt(int index);
    public int getValueAt(int index);
    public int size();
}
