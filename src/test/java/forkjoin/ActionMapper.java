package forkjoin;

import java.util.List;

/**
 * Created by xiexianwu on 19/6/22.
 */
public interface ActionMapper<T> {
    boolean shouldBeComputedDirectly();

    void compute();

    List<ActionMapper<T>> map();
}
