import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Alarm {
    private File alarmFile;
    private Clip alarmSound;
    
    public Alarm(){
        try {
            alarmSound = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        alarmFile = new File("src/Alarm_Sounds/warning-alarm-38930-PREVIEW-[AudioTrimmer.com] (2).wav");
        setAlarmFile(alarmFile);
        
    }
    //create an alarm event
    public File getAlarmFile(){
        return alarmFile;
    }
    public void setAlarmFile(File alarmFile) {
        this.alarmFile = alarmFile;
    }

    public void ringAlarm(File file) throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        alarmSound.open(AudioSystem.getAudioInputStream(file));
        alarmSound.start();
        
        //alarmSound.getMicrosecondLength()/100
        Thread.sleep(1000);
        alarmSound.stop();
    }
    public void stopAlarm(){
        alarmSound.stop();
    }
    public void playAlarm(File file) throws FileNotFoundException, IOException{
        InputStream inStream = new FileInputStream(getAlarmFile());
        AudioStream audio = new AudioStream(inStream);
        AudioPlayer.player.start(audio);
    }
}
