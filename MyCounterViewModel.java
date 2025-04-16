package com.example.mycounter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCounterViewModel extends ViewModel {
    private final MutableLiveData<Integer> counter = new MutableLiveData<>();

    public MyCounterViewModel() {
        counter.setValue(0);
    }

    public MutableLiveData<Integer> getCounter() {
        return counter;
    }

    public void incrementCounter() {
        counter.setValue(counter.getValue() + 1);
    }

    public void decrementCounter() {
        counter.setValue(counter.getValue() - 1);
    }

    public void resetCounter() {
        counter.setValue(0);
    }
}
