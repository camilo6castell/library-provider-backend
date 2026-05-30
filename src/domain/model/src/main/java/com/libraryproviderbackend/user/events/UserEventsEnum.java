package com.libraryproviderbackend.user.events;

/**
 * Enumerates all domain event types for the User aggregate.
 */
public enum UserEventsEnum {
    USER_CREATED,
    TEXT_QUOTED,
    BUDGET_TEXTS_QUOTED,
    VARIOUS_TEXTS_QUOTED,
    BATCH_TEXTS_QUOTED
}
