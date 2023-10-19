package com.example.versand;

import java.time.LocalDate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.format.DateTimeParseException;

public class Data {

    private static final String CSV_SEPARATOR = ",";

    public boolean useData(String[] data) {
        if (data == null || data.length < 20) {
            return false;
        } else{

            String ID = data[0];
        LocalDate place = parseLocalDate(data[1]);
        Kunde from = new Kunde(data[2], data[3], data[4], data[5], data[6], data[7]);
        Kunde to = new Kunde(data[8], data[9], data[10], data[11], data[12], data[13]);

        Versandobjekt versandobjekt = new Versandobjekt(ID, place, from, to);


        String filename = "versand- s" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv";

            return writeVersandDataToCSV(filename, versandobjekt);
        }
    }

    private LocalDate parseLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, formatter);
    }

    public static boolean writeVersandDataToCSV(String filename, Versandobjekt versand) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            // Schreiben Sie die Header-Zeile, wenn gewünscht
            //writer.write("iD,placed,from_vName,from_lName,..."); // Header-Zeile

            if (!new File(filename).exists()) {
                // Wenn die Datei noch nicht existiert, schreiben Sie die Header-Zeile
                writer.write("iD,placed,from_vName,from_lName,from_Street,from_StreetNr,from_Plz,from_Loc,to_vName,to_lName,to_Street,to_StreetNr,to_Plz,to_Loc");
                writer.newLine();
            }

            StringBuilder line = new StringBuilder();
            line.append(versand.getiD()).append(CSV_SEPARATOR);
            LocalDate placedDate = versand.getPlaced();
            line.append(placedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append(CSV_SEPARATOR);
            line.append(versand.getFrom().getvName()).append(CSV_SEPARATOR);
            line.append(versand.getFrom().getlName()).append(CSV_SEPARATOR);
            line.append(versand.getFrom().getStreet()).append(CSV_SEPARATOR);
            line.append(versand.getFrom().getStreetNr()).append(CSV_SEPARATOR);
            line.append(versand.getFrom().getPlz()).append(CSV_SEPARATOR);
            line.append(versand.getFrom().getLoc()).append(CSV_SEPARATOR);
            line.append(versand.getTo().getvName()).append(CSV_SEPARATOR);
            line.append(versand.getTo().getlName()).append(CSV_SEPARATOR);
            line.append(versand.getTo().getStreet()).append(CSV_SEPARATOR);
            line.append(versand.getTo().getStreetNr()).append(CSV_SEPARATOR);
            line.append(versand.getTo().getPlz()).append(CSV_SEPARATOR);
            line.append(versand.getTo().getLoc());

            writer.write(line.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
