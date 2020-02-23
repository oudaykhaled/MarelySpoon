package com.ouday.marelyspoon.core.di.modules;

import com.ouday.marelyspoon.core.di.qualifier.CoroutinesIO;
import com.ouday.marelyspoon.core.di.qualifier.CoroutinesMainThread;
import com.ouday.marelyspoon.core.di.qualifier.IO;
import com.ouday.marelyspoon.core.di.qualifier.MainThread;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;

@Module
public class SchedulersModule {

    @Provides
    @IO
    public Scheduler bindIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @MainThread
    public Scheduler bindMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }



    @CoroutinesIO
    @Provides
    public CoroutineContext providesIoDispatcher() {
      return  Dispatchers.getIO();
    }

    @CoroutinesMainThread
    @Provides
    public CoroutineContext providesMainThreadDispatcher(){
        return Dispatchers.getMain();
    }

}
