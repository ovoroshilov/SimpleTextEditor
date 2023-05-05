package editor;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class WorkWithFile {

    protected void writeFile(String pathToFile, JTextArea textArea, JFileChooser fileChooser) {
        try {
            fileChooser.showOpenDialog(null);
            File file = fileChooser.getSelectedFile();
            pathToFile = file.getAbsolutePath();
        }catch (Exception e){
            System.out.println("Please choose file");
        }
        try (FileWriter writer = new FileWriter(pathToFile)) {
            writer.write(textArea.getText());
        } catch (Exception e) {
            System.out.println("Error with write file");
        }
    }

    protected void readFile(JFileChooser fileChooser, String pathToFile, JTextArea textArea) {
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        pathToFile = file.getAbsolutePath();
        try {
            FileReader reader = new FileReader(pathToFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            textArea.read(bufferedReader, null);
            bufferedReader.close();
            textArea.requestFocus();
        } catch (Exception e) {
            textArea.setText("");
        }
    }

}
