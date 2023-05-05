package editor;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWorker {
    public String fileRead(String path) throws IOException{
        return new String(Files.readAllBytes((Paths.get(path))));
    }
}

