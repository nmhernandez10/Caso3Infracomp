/*
 * Decompiled with CFR 0_123.
 */
package Generator;

import Generator.LoadGenerator;
import Generator.Task;
import Generator.ClientServerTask;

public class Generator {
    private LoadGenerator generator;

    public Generator() {
        Task work = this.createTask();
        int numberOfTasks = 100;
        int gapBetweenTasks = 1000;
        this.generator = new LoadGenerator("Client - Server Load Test", numberOfTasks, work, gapBetweenTasks);
        this.generator.generate();
    }

    private Task createTask() {
        return new ClientServerTask();
    }

    public static /* varargs */ void main(String ... args) {
        Generator gen = new Generator();
    }
}

