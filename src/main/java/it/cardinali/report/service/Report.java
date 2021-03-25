package it.cardinali.report.service;

import it.cardinali.report.entity.Report1Entity;
import it.cardinali.report.entity.model.AnagraficaModel;
import it.cardinali.report.repository.Report1Repository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

/************************************************************************
 * Created: 18/03/2021                                                  *
 * Author:  mcardinali                                                  *
 ************************************************************************/
@Service
@Slf4j
public class Report {

  final Report1Repository report1Repository;

  public Report(Report1Repository report1Repository) {
    this.report1Repository = report1Repository;
  }

  public static void main(String[] args) throws IOException {
  }


  private void esegui() throws IOException {

    String dirLocation = "../ale";
    List<File> files = Files.list(Paths.get(dirLocation))
      .filter(Files::isRegularFile)
      .filter(path -> path.toString().endsWith(".xlsx"))
      .map(Path::toFile)
      .collect(Collectors.toList());
    files.forEach(fileIn -> {
      log.debug("filename " + fileIn.getName());
      String filename = fileIn.getName();
      try {
        FileInputStream file = new FileInputStream(new File("../ale/" + fileIn));
//Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
//Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
//Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
          Row row = rowIterator.next();

          Report1Entity report1Entity = new Report1Entity();

//For each row, iterate through all the columns
          Iterator<Cell> cellIterator = row.cellIterator();
          while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getColumnIndex() == 0) {
              Object objT = setAll(cell);
              if (objT instanceof String)
                report1Entity.setName((String) setAll(cell));
            }
            if (cell.getColumnIndex() == 1) {
              Object objT = setAll(cell);
              if (objT instanceof Double)
                report1Entity.setSomma(BigDecimal.valueOf((Double) objT));
            }
            if (cell.getColumnIndex() == 2) {
              Object objT = setAll(cell);
              if (objT instanceof Double)
                report1Entity.setCdip(BigDecimal.valueOf((Double) objT));
            }
            if (cell.getColumnIndex() == 3) {
              Object objT = setAll(cell);
              if (objT instanceof Double)
                report1Entity.setCaz(BigDecimal.valueOf((Double) objT));
            }
            if (cell.getColumnIndex() == 4) {
              Object objT = setAll(cell);
              if (objT instanceof Double)
                report1Entity.setGg(BigInteger.valueOf(((Double) objT).intValue()));
            }
            if (cell.getColumnIndex() == 5) {
              Object objT = setAll(cell);
              if (objT instanceof String)
                report1Entity.setCdc((String) objT);
            }
            if (cell.getColumnIndex() == 6) {
              Object objT = setAll(cell);
              if (objT instanceof Double)
                report1Entity.setImp(BigInteger.valueOf(((Double) objT).intValue()));
            }


          }
          report1Entity.setNomefile(filename);
          if (report1Entity.getName() != null && !report1Entity.getName().equals("Etichette di riga") && !report1Entity.getName().equals("Totale complessivo"))
            report1Repository.save(report1Entity);

        }
        file.close();


      } catch (Exception e) {
        e.printStackTrace();
      }

    });


  }


  private Object setAll(Cell cell) {
    Object obj = new Object();
    if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
      switch (cell.getCachedFormulaResultType()) {
        case Cell.CELL_TYPE_NUMERIC:
          obj = cell.getNumericCellValue();
          break;
        case Cell.CELL_TYPE_STRING:
          obj = cell.getStringCellValue();
          break;
      }
    } else {
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:
          if (HSSFDateUtil.isCellDateFormatted(cell))
            obj = cell.getDateCellValue();
          else
            obj = cell.getNumericCellValue();
          break;
        case Cell.CELL_TYPE_STRING:
          obj = cell.getStringCellValue();
          break;
      }
    }
    return obj;
  }

  public void start() {
    try {
      this.esegui();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void anagrafica() {
    try {
      this.anagraficaExecute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void anagraficaExecute() throws IOException {
    log.debug("anagraficaExecute");

    FileInputStream file = new FileInputStream(new File("../ale/ana/ANAGRAFICHE.xlsx"));
//Create Workbook instance holding reference to .xlsx file
    XSSFWorkbook workbook = new XSSFWorkbook(file);
//Get first/desired sheet from the workbook
    XSSFSheet sheet = workbook.getSheetAt(0);
//Iterate through each rows one by one
    Iterator<Row> rowIterator = sheet.iterator();

    List<AnagraficaModel> listaAnagrafica = StreamSupport.stream(
      Spliterators.spliteratorUnknownSize(rowIterator, Spliterator.ORDERED), false)
      .collect(Collectors.toList()).stream().map(row -> {
        AnagraficaModel anMo = new AnagraficaModel();

        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
          Cell cell = cellIterator.next();
          if (cell.getColumnIndex() == 0) {
            Object objT = setAll(cell);
            if (objT instanceof Double)
              anMo.setGiorni(((Double) objT).intValue());
          }
          if (cell.getColumnIndex() == 1) {
            Object objT = setAll(cell);
            if (objT instanceof String)
              anMo.setName((String) setAll(cell));
          }
          if (cell.getColumnIndex() == 2) {
            Object objT = setAll(cell);
            if (objT instanceof Date)
              anMo.setDataInizio((Date) setAll(cell));
          }
          if (cell.getColumnIndex() == 3) {
            Object objT = setAll(cell);
            if (objT instanceof Date)
              anMo.setDataFine((Date) setAll(cell));
          }
          if (cell.getColumnIndex() == 4) {
            Object objT = setAll(cell);
            if (objT instanceof String)
              anMo.setMansione((String) setAll(cell));
          }
          if (cell.getColumnIndex() == 5) {
            Object objT = setAll(cell);
            if (objT instanceof Double)
              anMo.setGiorni(((Double) objT).intValue());
          }
          if (cell.getColumnIndex() == 6) {
            Object objT = setAll(cell);
            if (objT instanceof String)
              anMo.setFtpt((String) setAll(cell));
          }
        }


        return anMo;
      }).collect(Collectors.toList());

    file.close();

    //listaAnagrafica.forEach(anag -> log.debug(anag.getName()));

    Map<String, List<AnagraficaModel>> postsPerType = listaAnagrafica.stream().collect(groupingBy(AnagraficaModel::getName));


    postsPerType.values().stream().forEach(anagraficaModels -> {

      Date minIniDate = anagraficaModels.stream().map(AnagraficaModel::getDataInizio).filter(Objects::nonNull).min(Date::compareTo).orElseGet(Date::new);

      Date maxFineDate = anagraficaModels.stream().map(AnagraficaModel::getDataFine).filter(Objects::nonNull).max(Date::compareTo).orElseGet(Date::new);

      String name = anagraficaModels.stream().map(AnagraficaModel::getName).findAny().get();
      Integer giorni = anagraficaModels.stream().map(AnagraficaModel::getGiorni).reduce(0, Integer::sum);

      //log.debug("name " + name + " giorni " + giorni + "  minIniDate   - > " + minIniDate + "  maxFineDate  - > " + maxFineDate);

      Collection<Report1Entity> update = report1Repository.findByName(name);

      update.stream().forEach(report1Entity -> {
        report1Entity.setDataIni(minIniDate);
        report1Entity.setDataFin(maxFineDate);
        report1Entity.setGiorni(BigInteger.valueOf(giorni));
        report1Repository.save(report1Entity);
      });


    });
    log.debug("fine");

  }


  private void stampa(Iterator<Row> rowIterator) {
    List<Row> result = StreamSupport.stream(
      Spliterators.spliteratorUnknownSize(rowIterator, Spliterator.ORDERED), false)
      .collect(Collectors.toList());

    result.forEach(row -> {
      String stampa = "";
      Iterator<Cell> cellIterator = row.cellIterator();
      while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();

        switch (cell.getCellType()) {
          case Cell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
              stampa += cell.getDateCellValue();
            } else
              stampa += cell.getNumericCellValue();
            break;
          case Cell.CELL_TYPE_STRING:
            stampa += cell.getStringCellValue();
            break;

        }


      }
      log.debug(stampa);
    });
  }

}

