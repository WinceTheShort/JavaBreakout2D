
package org.example;

/**
 * The {@code ActionEventIDs} class contains constants representing different action events within the application.
 * <p>
 * This class provides unique identifiers for various game state changes, including level changes, score changes,
 * and lives changes. Each of these constants can be used throughout the application to identify specific events
 * and trigger corresponding actions.
 * </p>
 * <p>
 * Since this class only contains static constants, it is declared as final to prevent subclassing and has a
 * private constructor to prevent instantiation.
 * </p>
 *
 * <ul>
 *   <li>{@link #LEVEL_CHANGED} - Identifier for a level change event.</li>
 *   <li>{@link #SCORE_CHANGED} - Identifier for a score change event.</li>
 *   <li>{@link #LIVES_CHANGED} - Identifier for a lives change event.</li>
 * </ul>
 *
 */
public final class ActionEventIDs {
    /**
     * Identifier for a level change event.
     */
    public static final int LEVEL_CHANGED = 0;

    /**
     * Identifier for a score change event.
     */
    public static final int SCORE_CHANGED = 1;

    /**
     * Identifier for a lives change event.
     */
    public static final int LIVES_CHANGED = 2;

    /**
     * Private constructor to prevent instantiation.
     */
    private ActionEventIDs() {}
}
