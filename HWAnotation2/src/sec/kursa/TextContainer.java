package sec.kursa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextContainer {

    @SaveTo(path = "test.txt")
    private String text = "this text for save";
    private String nosave = "this text no save";

    @Saver
    public void writeToFile (String path, String text) throws IOException {
        File file = new File(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));){
            bw.append(text);
        }



    }


}
