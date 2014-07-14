package org.apx.testing.core;

/**
 * Used to create a queue of delays when calling one of untilConditon methods in {@link org.apx.testing.core.ICommonElementFactory}
 */
class QueueAction {
    final Condition condition;
    final int timeout;

    public QueueAction(Condition c, int t){
        condition = c;
        timeout = t;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getTimeout() {
        return timeout;
    }


}
