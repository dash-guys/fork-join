package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by xiexianwu on 19/6/22.
 */
public class MapReduceTask<T,V> extends RecursiveTask<Reduce<V>> {

    // 输入
    private Mapper<T,V> mapper;

    // 子任务的层数（防止出现死循环）
    private int level = 0;

    private static final int MAX_LEVEL = 20;

    public MapReduceTask(Mapper<T,V> mapper){
        this(mapper, 0);
    }

    MapReduceTask(Mapper<T,V> input , int level){
        this.mapper = mapper;
        this.level = level;
        if(level >= MAX_LEVEL){
           throw new RecursiveOverFlowException(String.format("recursive deep:{%s} is overflow than max level:{%s}", level, MAX_LEVEL));
        }
    }

    /**
     * 并行计算
     * @return
     */
    @Override
    protected Reduce<V> compute() {
        if(mapper.shouldBeComputedDirectly()){
           return mapper.compute();
        }

        // 分割任务
        List<Mapper<T,V>> subInputList = mapper.map();
        List<MapReduceTask> taskList = new ArrayList<>();

        for (Mapper<T,V> subInput : subInputList ){
            MapReduceTask<T,V> subTask = new MapReduceTask<>(subInput, this.level + 1);
            subTask.fork();
            taskList.add(subTask);
        }

        // 获取和聚合任务结果
        MapReduceTask<T,V> firstTask = taskList.get(0);
        Reduce<V> outputResult = firstTask.join();

        for (int i = 1 ; i<taskList.size() ; i++){
            MapReduceTask<T,V> task = taskList.get(i);
            outputResult = outputResult.reduce(task.join());
        }
        return outputResult;
    }
}
