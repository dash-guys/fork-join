package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by xiexianwu on 19/6/22.
 */
public class MapReduceAction<T> extends RecursiveAction {

    private ActionMapper<T> input;

    public MapReduceAction(ActionMapper<T> input){
        this.input = input;
    }

    @Override
    protected void compute() {
        if(input.shouldBeComputedDirectly()){
            input.compute();
        }

        // 分割任务
        List<ActionMapper<T>> subInputList = input.map();
        List<MapReduceAction> taskList = new ArrayList<>();

        for (ActionMapper<T> subInput : subInputList ){
            MapReduceAction<T> subTask = new MapReduceAction<>(subInput);
            subTask.fork();
            taskList.add(subTask);
        }
    }
}
