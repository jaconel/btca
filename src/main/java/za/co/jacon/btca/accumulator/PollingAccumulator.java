package za.co.jacon.btca.accumulator;

import za.co.jacon.btca.distributor.Distributor;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The polling accumulator class.
 */
public abstract class PollingAccumulator<T> extends AbstractAccumulator<T> implements Runnable {

    protected final int pollDelay;

    private static final Integer INITIAL_SCHEDULE_DELAY_IN_SECONDS = 0;

    private ScheduledFuture schedule;

    /**
     * The scheduled
     */
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Class constructor.
     *
     * Sets the polling delay for the scheduled task.
     *
     * @param distributors the distributors responsible for distributing the data
     * @param pollDelay the polling delay
     */
    public PollingAccumulator(final List<Distributor<T>> distributors, final int pollDelay) {
        super(distributors);

        this.pollDelay = pollDelay;
    }

    /**
     * Starts the accumulation process by initiate a scheduled task.
     */
    @Override
    public void start() {
        this.schedule = scheduler.scheduleAtFixedRate(this, INITIAL_SCHEDULE_DELAY_IN_SECONDS, this.pollDelay, TimeUnit.SECONDS);
    }

    /**
     * Stops the accumulation process by elegantly shutting down the scheduled tasks.
     */
    @Override
    public void stop() {
        this.schedule.cancel(false);
    }
}
