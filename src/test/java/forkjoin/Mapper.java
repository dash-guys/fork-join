package forkjoin;

import java.util.List;

/**
 * Created by xiexianwu on 19/6/22.
 */
public interface Mapper<T,V> {
    boolean shouldBeComputedDirectly();

    Reduce<V> compute();

    List<Mapper<T,V>> map();
}
