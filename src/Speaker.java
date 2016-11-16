import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Speaker {
	private SourceDataLine speaker;
	private AudioFormat format;
	
	public Speaker(AudioFormat format) {
		this.format = format;
	}
	
	public void open() {
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
		while (true) {
			try {
				speaker = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				speaker.open(format);
				speaker.start();
				System.out.println("Successfully opened a speaker");
				break;
			} catch (LineUnavailableException e) {
				System.out.println("Couldn't open a line... Trying again");
			}
		}
	}
	
	public void writeBytes(byte[] data) {
//		System.out.println("Wrote " + Arrays.toString(data) + " bytes");
		speaker.write(data, 0, data.length);
	}
	
	public void close() {
		speaker.close();
	}
}
