package cn.infinitumstudios.infinitumEconomy.event.utility;

public interface Event<T> {
    T invoker();
    void register(T listener);
}