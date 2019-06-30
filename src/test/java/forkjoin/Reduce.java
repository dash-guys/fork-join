package forkjoin;

/**
 * Created by xiexianwu on 19/6/22.
 */
public interface Reduce<T> {

    Reduce<T> reduce(Reduce<T> other);

    T getResult();
}
