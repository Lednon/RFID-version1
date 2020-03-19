import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class TestAlarm {
    public static void main(String[]  args){
        Alarm alarm = new Alarm();
        
        Thread alarmRinger = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    alarm.ringAlarm(alarm.getAlarmFile());
                } catch (IOException ex) {
                    Logger.getLogger(TestAlarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TestAlarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(TestAlarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestAlarm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        alarmRinger.start();
    }
}
