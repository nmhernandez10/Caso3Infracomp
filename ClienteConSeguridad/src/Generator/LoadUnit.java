/*
 * Decompiled with CFR 0_123.
 */
package Generator;

import java.io.PrintStream;
import java.util.Date;
import Generator.LoadGenerator;
import Generator.Task;

public class LoadUnit
implements Runnable {
    private Task command;
    private int id;
    private long extraTimeGap;
    private boolean sync;

    public LoadUnit(Task commandP, int idP, long extraTimeGapP, boolean syncP) {
        this.command = commandP;
        this.id = idP;
        this.extraTimeGap = extraTimeGapP;
        this.sync = syncP;
    }

    @Override
    public void run() {
        if (this.sync) {
            this.waitUntil();
        }
        this.command.execute();
        System.out.println("[LoadUnit " + this.id + "] [Executed at: " + new Date(System.currentTimeMillis()) + "]");
    }

    public void waitUntil() {
        long born = System.currentTimeMillis();
        long waitMl = born + (long)LoadGenerator.SYNC_GAP + this.extraTimeGap;
        Date wait = new Date(waitMl);
        System.out.println("[LoadUnit" + this.id + "] [Waiting Until Sync: " + wait.toString() + "**]");
        boolean isTheTime = false;
        while (!isTheTime) {
            if (!new Date(System.currentTimeMillis()).toString().equals(wait.toString())) continue;
            isTheTime = true;
        }
    }
}

