package week3;

import java.util.Dictionary;
import java.util.Hashtable;

public class KeyIndexedCountingSort {
    public void sort(){
        User[] users = new User[7];
        users[0] = new User("Artem", 4);
        users[1] = new User("Sasha", 2);
        users[2] = new User("Artem", 4);
        users[3] = new User("Vasya", 4);
        users[4] = new User("Vika", 2);
        users[5] = new User("Petya", 1);
        users[6] = new User("Seva", 3);

        int r = 5;

        int[] count = new int[r+1];
        String[] aux = new String[users.length];

        for(int i = 0; i < users.length; i++){
            count[users[i].age + 1]++;
        }

        for(int i = 0; i < r; i++){
            count[i+1] = count[i+1] + count[i];
        }

        //users[1] = aux[1];
    }
}
