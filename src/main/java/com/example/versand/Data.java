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

    private static final int POS_ID = 0;
    private static final int POS_ORDER_PLACED = 1;
    private static final int POS_FROM_VNAME = 2;
    private static final int POS_FROM_LNAME = 3;
    private static final int POS_FROM_STREET = 4;
    private static final int POS_FROM_STREETNR = 5;
    private static final int POS_FROM_PLZ = 6;
    private static final int POS_FROM_LOC = 7;

    private static final int POS_TO_VNAME = 8;
    private static final int POS_TO_LNAME = 9;
    private static final int POS_TO_STREET = 10;
    private static final int POS_TO_STREETNR = 11;
    private static final int POS_TO_PLZ = 12;
    private static final int POS_TO_LOC = 13;

    private static final int POS_EXPRESS = 14;
    private static final int POS_ALT_LOC = 15;
    private static final int POS_ALT_LOC_PLACE = 16;
    private static final int POS_INSURED = 17;
    private static final int POS_INSUR_TYPE = 18;
    private static final int POS_PACKAGE_TYPE = 19;

    private static final String CSV_SEPARATOR = ",";

    public boolean useData(String[] data) {
        if (data == null || data.length < 20) {
            return false;
        } else{

            String ID = data[POS_ID];
        LocalDate place = parseLocalDate(data[POS_ORDER_PLACED]);
        Kunde from = new Kunde(data[POS_FROM_VNAME], data[POS_FROM_LNAME], data[POS_FROM_STREET], data[POS_FROM_STREETNR], data[POS_FROM_PLZ], data[POS_FROM_LOC]);
        Kunde to = new Kunde(data[POS_TO_VNAME], data[POS_TO_LNAME], data[POS_TO_STREET], data[POS_TO_STREETNR], data[POS_TO_PLZ], data[POS_TO_LOC]);

        Versandobjekt versandobjekt = new Versandobjekt(ID, place, from, to, data[POS_EXPRESS], data[POS_ALT_LOC],data[POS_ALT_LOC_PLACE], data[POS_INSURED], data[POS_INSUR_TYPE], data[POS_PACKAGE_TYPE]);


        String filename = "versand-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv";

            return writeVersandDataToCSV(filename, versandobjekt);
        }
    }

    private LocalDate parseLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, formatter);
    }

    public static boolean writeVersandDataToCSV(String filename, Versandobjekt versand) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("ID,From_Data,To_Data,Package_Description,Express, Insured,Alternative Packagedisplace"); // Header-Zeile
            writer.newLine();
            if (!new File(filename).exists()) {
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
            line.append(versand.getTo().getLoc()).append(CSV_SEPARATOR);
            line.append(versand.getExpress()).append(CSV_SEPARATOR);
            line.append(versand.getAltLoc()).append(CSV_SEPARATOR);
            line.append(versand.getAltLocPlace()).append(CSV_SEPARATOR);
            line.append(versand.getInsured()).append(CSV_SEPARATOR);
            line.append(versand.getInsuranceType()).append(CSV_SEPARATOR);
            line.append(versand.getPackageType());

            writer.write(line.toString());
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
