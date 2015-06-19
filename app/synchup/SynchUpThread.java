package synchup;

import static core.dbi.NGOConstants.*;

/**
 * Created by nhosgur on 8/8/14.
 */
class SynchUpThread extends Thread {

    private Thread st;

    private class SR implements Runnable {
        @Override
        public void run() {
            try {
                SynchUp.writeIntoCSVFromTable();
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                new Thread(new SR()).run();
                Thread.sleep(MINUTE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public SynchUpThread() {
    }
}
