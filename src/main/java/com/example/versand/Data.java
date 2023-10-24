package com.example.versand;

import java.io.*;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static final int POS_DESCRIPTION = 14;
    private static final int POS_EXPRESS = 15;
    private static final int POS_ALT_LOC = 16;
    private static final int POS_ALT_LOC_PLACE = 17;
    private static final int POS_INSURED = 18;
    private static final int POS_INSUR_TYPE = 19;
    private static final int POS_PACKAGE_TYPE = 20;

    private static final String CSV_SEPARATOR = ",";
    private static Path filePath;

    public boolean useData(String[] data) {
        if (data == null || data.length <= 20) {
            return false;
        } else {

            String ID = data[POS_ID];
            LocalDate place = parseLocalDate(data[POS_ORDER_PLACED]);
            Kunde from = new Kunde(data[POS_FROM_VNAME], data[POS_FROM_LNAME], data[POS_FROM_STREET], data[POS_FROM_STREETNR],
                    data[POS_FROM_PLZ], data[POS_FROM_LOC]);
            Kunde to = new Kunde(data[POS_TO_VNAME], data[POS_TO_LNAME], data[POS_TO_STREET], data[POS_TO_STREETNR], data[POS_TO_PLZ], data[POS_TO_LOC]);

            Versandobjekt versandobjekt = new Versandobjekt(ID, place, from, to, data[POS_DESCRIPTION], data[POS_EXPRESS], data[POS_ALT_LOC], data[POS_ALT_LOC_PLACE], data[POS_INSURED], data[POS_INSUR_TYPE], data[POS_PACKAGE_TYPE]);


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
            if (!new File(filename).exists()) {
                writer.write("Auftrag" + versand.getiD());
                writer.newLine();
            }

            StringBuilder line = new StringBuilder();
            line.append("ID: " + versand.getiD()).append(CSV_SEPARATOR).append(System.lineSeparator());
            LocalDate placedDate = versand.getPlaced();
            line.append("Aufgegeben am: " + placedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append(CSV_SEPARATOR).append(System.lineSeparator());
            line.append("Absender: ");
            line.append("Vorname: " + versand.getFrom().getvName()).append(CSV_SEPARATOR);
            line.append("Nachname: " + versand.getFrom().getlName()).append(CSV_SEPARATOR);
            line.append("Straße: " + versand.getFrom().getStreet()).append(CSV_SEPARATOR);
            line.append("StraßenNr: " + versand.getFrom().getStreetNr()).append(CSV_SEPARATOR);
            line.append("PLZ: " + versand.getFrom().getPlz()).append(CSV_SEPARATOR);
            line.append("Ort: " + versand.getFrom().getLoc()).append(CSV_SEPARATOR).append(System.lineSeparator());
            line.append("Empfänger: ");
            line.append("Vorname: " + versand.getTo().getvName()).append(CSV_SEPARATOR);
            line.append("Nachname: " + versand.getTo().getlName()).append(CSV_SEPARATOR);
            line.append("Straße: " + versand.getTo().getStreet()).append(CSV_SEPARATOR);
            line.append("StraßenNr: " + versand.getTo().getStreetNr()).append(CSV_SEPARATOR);
            line.append("PLZ: " + versand.getTo().getPlz()).append(CSV_SEPARATOR);
            line.append("Ort: " + versand.getTo().getLoc()).append(CSV_SEPARATOR);
            line.append("Inhalt: " + versand.getDescription()).append(CSV_SEPARATOR);
            line.append(versand.getAltLoc()).append(CSV_SEPARATOR);
            line.append(versand.getAltLocPlace()).append(CSV_SEPARATOR);
            line.append(versand.getExpress()).append(CSV_SEPARATOR);
            line.append(versand.getInsured()).append(CSV_SEPARATOR);
            line.append(versand.getInsuranceType()).append(CSV_SEPARATOR);
            line.append(versand.getPackageType());

            writer.write(line.toString());
            writer.newLine();
            line.append(System.lineSeparator());
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkData(String orderPlaced) {
        String directoryPath = "./";
        String fileName = "versand-" + orderPlaced + ".csv";

        filePath = Paths.get(directoryPath, fileName);

        return Files.exists(filePath);
    }

    public static Versandobjekt getData(String orderPlaced, String ID) {
        ArrayList<String> lines = new ArrayList<String>();

        if (checkData(orderPlaced)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
                String line;

                String [] keyWords = {
                        "ID",
                        "Aufgegeben am",
                        "Absender",
                        "Empfänger",
                        "Vorname",
                        "Nachname",
                        "Straße",
                        "StraßenNr",
                        "PLZ",
                        "Ort",
                         "Inhalt",
                        "Alternativer Abgabeort",
                        "Ablageort",
                        "Versichert",
                        "Express",
                        "Versicherungshöhe",
                        "Paketart"
                };

                int i = 0;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if(parts[0].equals("ID: "+ ID)){
                        found = true;
                    }
                    if(found){
                        String[] data = line.split(",");
                        for (int x = 0; x < data.length; x++) {
                            if (data[x].contains(":")) {
                                String[] keyValue = data[x].split(":");
                                lines.add(keyValue[0].trim());
                                lines.add(keyValue[1].trim());
                            }
                        }
                        i++;
                    }
                    if(i == 3) break;
                }
                for(String keyword : keyWords){
                    lines.removeIf(item -> item.equals(keyword));
                }
                return createObject(lines);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return createObject(lines);
    }

    public static Versandobjekt createObject(ArrayList<String> data){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;


        String ID = data.get(POS_ID);
        LocalDate place = LocalDate.parse(data.get(POS_ORDER_PLACED), formatter);
        Kunde from = new Kunde(data.get(POS_FROM_VNAME), data.get(POS_FROM_LNAME), data.get(POS_FROM_STREET), data.get(POS_FROM_STREETNR),
                data.get(POS_FROM_PLZ), data.get(POS_FROM_LOC));
        Kunde to = new Kunde(data.get(POS_TO_VNAME), data.get(POS_TO_LNAME), data.get(POS_TO_STREET), data.get(POS_TO_STREETNR), data.get(POS_TO_PLZ), data.get(POS_TO_LOC));

        Versandobjekt versandobjekt = new Versandobjekt(ID, place, from, to, data.get(POS_DESCRIPTION), data.get(POS_EXPRESS), data.get(POS_ALT_LOC), data.get(POS_ALT_LOC_PLACE), data.get(POS_INSURED), data.get(POS_INSUR_TYPE), data.get(POS_PACKAGE_TYPE));

        return versandobjekt;
    }
}
