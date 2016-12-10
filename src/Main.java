import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteOrder;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer.Info;

import fileWriters.FileWriter;
import fileWriters.WAVWriter;
import math.Bytebeat;
import math.DataManipulation;
import wrappers.Microphone;
import wrappers.Speaker;

public class Main {
	public static void main(String[] args) {
		float sampleRate = 8000.0F;
		int channels = 1;
		int sampleSizeInBits = 16;
		boolean isSigned = true;
		boolean bigEndian = false;
		if (sampleSizeInBits == 8) {
			isSigned = false;
		}
		
		for (Info info : AudioSystem.getMixerInfo()) {
			System.out.println(info.toString());
		}

		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, isSigned, bigEndian);

		System.out.println(format.getEncoding().toString());
		WAVWriter file = new WAVWriter("test2.wav", format);
		Speaker speaker = new Speaker(format);		
		Microphone mic = new Microphone(format);

		mic.disable();
		speaker.disable();
		
		file.open();
		speaker.open();
		mic.open();

		byte[] data = new byte[mic.getBufferSize()/10];
		int start = 0;
		int minute = 60;
		int minutesPerSample = (int) (minute * sampleRate);
		double volume = 0.1;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (start < minutesPerSample * 99) {
			mic.readBytes(data);

			Bytebeat bytebeat = t ->  {
				return (int) (Math.sin(t*t/(440/sampleRate)) * 64);
			};
			
			DataManipulation.fillWithBytebeat(data, start, bytebeat);
//			DataManipulation.repeat(data, data.length/8);
			DataManipulation.amplify(data, volume);
			
			start += data.length;
			if (start % minutesPerSample == 0) {
				System.out.println(start / minutesPerSample + " Minutes Recorded");
			}
			
			speaker.writeBytes(data);
			file.write(data, bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
//			volume = (Math.random()*5) * (128*Math.random());
			try {
				if (reader.ready()) {
					String line = reader.readLine();
					if (line.equals("quit")) {
						break;
					}
					else {
						volume = Double.parseDouble(line);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mic.close();
		speaker.close();
		file.close();
	}
}
