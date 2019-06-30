package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by xiexianwu on 19/6/22.
 */
public class MapReduceTaskCenter {
    private final ForkJoinPool pool;

    public MapReduceTaskCenter(int numThreads) {
        this.pool = new ForkJoinPool(numThreads);
    }

    public <T,V> V execute(Mapper<T,V> mapper) {
        ForkJoinTask<Reduce<V>> task = new MapReduceTask(mapper);
        Reduce<V> output = pool.invoke(task);
        return output.getResult();
    }

    public <T> void executeAction(ActionMapper<T> mapper) {
        ForkJoinTask action = new MapReduceAction<T>(mapper);
        pool.invoke(action);
    }

    public static void main(String[] args){
        MapReduceTaskCenter taskCenter = new MapReduceTaskCenter(5);
        Integer result = taskCenter.execute(new SumInput(0, 10000));
        System.out.println(result);
    }
}
