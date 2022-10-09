package com.ennea.supplychainmanagement.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.ennea.supplychainmanagement.entity.SupplyChainManagement;

public class CSVHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Id", "Title", "Description", "Published" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<SupplyChainManagement> csvToSupplyChainManagement(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<SupplyChainManagement> supplyChainManagements = new ArrayList<SupplyChainManagement>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				SupplyChainManagement supplyChainManagement = new SupplyChainManagement(
						Long.parseLong(csvRecord.get("id")), csvRecord.get("code"), csvRecord.get("name"),
						csvRecord.get("batch"), Long.parseLong(csvRecord.get("stock")),
						Long.parseLong(csvRecord.get("deal")), Long.parseLong(csvRecord.get("free")),
						Double.parseDouble(csvRecord.get("mrp")), Double.parseDouble(csvRecord.get("rate")),
						csvRecord.get("exp"), csvRecord.get("company"), csvRecord.get("supplier"));

				supplyChainManagements.add(supplyChainManagement);
			}

			return supplyChainManagements;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}
