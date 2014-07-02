package org.apx.testing.core;

import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 01.07.14
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
class QueueAction {
    Condition condition;
    int timeout;

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
