package forkjoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexianwu on 19/6/22.
 */
public class SumInput implements Mapper<Integer,Integer> {
    private int start;

    private int end;

    SumInput(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean shouldBeComputedDirectly() {
        if(end - start <= 5){
            return true;
        }
        return false;
    }

    @Override
    public Reduce<Integer> compute() {
        int result = 0;
        for(int i = start; i<= end;i++){
            result += i;
        }
        return new SumOutput(result);
    }

    @Override
    public List<Mapper<Integer,Integer>> map() {
        List<Mapper<Integer,Integer>> inputs = new ArrayList<>();
        int mid = (end + start) / 2;
        Mapper left = new SumInput(start, mid -1);
        Mapper right = new SumInput(mid, end);
        inputs.add(left);
        inputs.add(right);
        return inputs;
    }
}
