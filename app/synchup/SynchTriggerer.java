package synchup;

/**
 * Created by nhosgur on 8/9/14.
 */
public final class SynchTriggerer {
    /**
     * The constant trigggerThem.
     */
    public static final boolean trigggerThem;

    static {
        trigggerThem = true;
        SynchUp.doTrigger();
    }

    /**
     * Calll me to trigger.
     */
    public static void calllMeToTrigger() {
    }
}
