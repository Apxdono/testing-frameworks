package org.apx.testing.core;

/**
 * Element dom state. Used internally to determine what type of ExpectedCondition to use.
 * @see org.openqa.selenium.support.ui.ExpectedCondition
 * @see org.openqa.selenium.support.ui.ExpectedConditions
 */
public enum Condition {
    VISIBLE,
    PRESENT;
}
