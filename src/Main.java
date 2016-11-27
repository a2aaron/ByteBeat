import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.sampled.AudioFormat;

import fileWriters.FileWriter;
import fileWriters.WAVWriter;
import math.Bytebeat;
import math.DataManipulation;
import wrappers.Microphone;
import wrappers.Speaker;

public class Main {
	public static void main(String[] args) {
		float sampleRate = 8000.0F;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean isSigned = false;
		boolean bigEndian = false;

		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, isSigned, bigEndian);
		System.out.println(format.getEncoding().toString());
		WAVWriter file = new WAVWriter("test.wav", format);
		Speaker speaker = new Speaker(format);
		Microphone mic = new Microphone(format);
		speaker.open();
		mic.open();
		
		byte[] data = new byte[mic.getBufferSize()/10];
		int start = 0;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while (start < 10000000) {
			mic.readBytes(data);
//			DataManipulation.repeat(data, data.length/4);
//			DataManipulation.amplify(data, 8);
//			Bytebeat bytebeat = t -> (t>>4 & t>>8) * t;
//			DataManipulation.fillWithBytebeat(data, start, bytebeat);
			start += data.length;
			speaker.writeBytes(data);
			file.write(data);
			
			try {
				if (reader.ready()) {
					String line = reader.readLine();
					if (line.equals("quit")) {
						break;
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
