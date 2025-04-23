package cls;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;


public class Bag {
    private int valueBag;
    private int cntEntity;
    ArrayList<Object> itemsBag = new ArrayList<Object>();

    Random rndm = new Random();

    public Bag(int valueBag) {
        this.valueBag = valueBag;
    }

    public void addToBag(Object item){
        if (cntEntity < valueBag){
            itemsBag.add(item);
            this.cntEntity++;
        }
        else {System.out.println("Рюкзак полон!");}
    }

    public Object remofeFromBag(){
        if (cntEntity > 0){
            int ch = ThreadLocalRandom.current().nextInt(1,cntEntity);
            Object obj = itemsBag.get(ch);
            if(cntEntity>2){

            }
            else ;
            return obj;
        }
        else {
            System.out.println("Рюкзак пуст!");
            return 0;
        }
    }
}
