import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHandler {
    private final String fileName;

    @Autowired
    public CSVHandler(String fileName) {
        this.fileName = fileName;
    }

    public List<String[]> readAll() throws DataAccessException {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            throw new DataAccessException("Failed to read data from file: " + fileName, e) {};
        }

        return data;
    }

    public void writeAll(List<String[]> data) throws DataAccessException {
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
            throw new DataAccessException("Failed to write data to file: " + fileName, e) {};
        }
    }
}