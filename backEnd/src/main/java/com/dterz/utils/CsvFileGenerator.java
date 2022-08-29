package com.dterz.utils;

import com.dterz.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvFileGenerator {

  public void writeTransactionsToCsv(List<Transaction> transactions, Writer writer) {

    try {
      CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
      for (Transaction transaction : transactions) {
        printer.printRecord(transaction.getId(), transaction.getAmount(),
            transaction.getUser().getUserName(), transaction.getType());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
