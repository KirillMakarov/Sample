package me.makarov.lintsample;

import timber.log.Timber;

public class LOLClassName {
    private int mJavaField = 1;

    void foo(String s) {
        Timber.d("Foo " + "Badr");
    }

    void bar() {
        foo(null);
    }
}
