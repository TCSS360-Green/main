
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    private final String fileName;

    public CSVHandler(String fileName) {
        this.fileName = fileName;
    }

    public List<String[]> readAll() throws IOException {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            throw new IOException("Failed to read data from file: " + fileName, e);
        }

        return data;
    }

    public void writeAll(List<String[]> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] values : data) {
                for (int i = 0; i < values.length; i++) {
                    writer.write(values[i]);

                    if (i != values.length - 1) {
                        writer.write(",");
                    }
                }

                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Failed to write data to file: " + fileName, e);
        }
    }
}
