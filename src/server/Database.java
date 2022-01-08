package server;

import java.util.ArrayList;

public class Database {

    ArrayList<String> data;
    int size;

    public Database(int n) {
        this.data  = new ArrayList<String>(n);
        this.size = n;
        fillDatabase();
    }

    public void fillDatabase () {
        for(int i = 0; i < size; i++) {
            this.data.add("");
        }
    }

    public void printDatabase () {
        for(int i = 0; i < size; i++) {
            System.out.println(this.data.get(i));
        }
    }


}