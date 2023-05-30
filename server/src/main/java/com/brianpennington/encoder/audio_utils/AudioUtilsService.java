package com.brianpennington.encoder.audio_utils;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AudioUtilsService {

    public int getDuration(final String filePath) {
        try {
            final String command = "ffprobe -v quiet -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 -i "
                    + filePath;

            final Process process = Runtime.getRuntime().exec(command);

            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            final String output = sb.toString();
            final int exit = process.waitFor();

            reader.close();
            if (exit != 0) {
                return -1;
            }

            return (int) Double.parseDouble(output);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }

    public Optional<String> encodeAac(final String filePath, final long id) {
        try {
            final String outputFilePath = Paths.get(filePath).getParent().toString() + "/" + id + ".aac";
            final String command = "ffmpeg -i " + filePath + " -c:a aac -b:a 64k " + outputFilePath;
            final Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return Optional.of(outputFilePath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null; // Return null to indicate an error
        }
    }
}
