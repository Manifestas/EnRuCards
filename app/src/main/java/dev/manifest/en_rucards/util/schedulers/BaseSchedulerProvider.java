package dev.manifest.en_rucards.util.schedulers;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
