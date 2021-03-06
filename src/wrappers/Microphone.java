package wrappers;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Microphone implements SystemResource {
	private TargetDataLine microphone;
	private AudioFormat format;
	boolean enabled = true;

	public Microphone(AudioFormat format) {
		this.format = format;
	}

	public void open() {
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
		while (true) {
			try {
				microphone = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
				microphone.open(format);
				microphone.start();
				System.out.println("Successfully opened a microphone. ");
				break;

			} catch (LineUnavailableException e) {
				System.out.println("Couldn't open a line... Trying again");
			}
		}
	}

	public void readBytes(byte[] data) {
		if (enabled) {
			microphone.read(data, 0, data.length);
		}
	}

	public void close() {
		microphone.close();
	}

	public int getBufferSize() {
		return microphone.getBufferSize();
	}

	@Override
	public void enable() {
		enabled = true;
	}

	@Override
	public void disable() {
		enabled = false;
	}
}
