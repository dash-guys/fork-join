package forkjoin;

/**
 * Created by xiexianwu on 19/6/22.
 */
public class SumOutput implements Reduce<Integer> {
    private int result;

    public SumOutput(int result){
        this.result = result;
    }

    @Override
    public Reduce<Integer> reduce(Reduce<Integer> other) {
        return new SumOutput(this.result + other.getResult());
    }

    @Override
    public Integer getResult() {
        return result;
    }
}
