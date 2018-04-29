/*
 * Decompiled with CFR 0_123.
 */
package Generator;

import Generator.IFallible;

public abstract class Task
implements IFallible {
    public static final String OK_MESSAGE = "OK_TEST";
    public static final String MENSAJE_FAIL = "FAIL_TEST";

    public abstract void execute();
}

