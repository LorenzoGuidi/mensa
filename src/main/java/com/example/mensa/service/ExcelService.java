package com.example.mensa.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.example.mensa.model.Alunno;
import com.example.mensa.model.Utente;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelService {
	
	private List<Alunno> studentList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Date dataInizio;
    private Date dataFine;


	public ExcelService(List<Alunno> studentList/* , Date dataInizio, Date dataFine */) {
        this.studentList = studentList;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        workbook = new XSSFWorkbook();
    }
    
    
  private void writeHeader() {
        sheet = workbook.createSheet("Rendicontazione");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        /*this.createCell(row, 0, "Nome", style);
        this.createCell(row, 1, "Cognme", style);
        this.createCell(row, 2, "Email", style);
        this.createCell(row, 3, "Data iscrizione", style);*/
    }
    
    
    
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        // sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        
       
        if (valueOfCell instanceof Date) {
    	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format((Date) valueOfCell);
            cell.setCellValue(formattedDate);
        } else if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Utente) {
            Utente utente = (Utente) valueOfCell;
            String userInfo = utente.getUsername(); // Supponendo che l'oggetto Utente abbia un campo "username"
            createCell(row, columnCount++, userInfo, style);
        } /*else if (valueOfCell instanceof LocalDate) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format((LocalDate) valueOfCell);
            cell.setCellValue(formattedDate);
        } */ 
        
        else {
            cell.setCellValue((Boolean) valueOfCell);
        }

        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        // Use reflection to get all the fields of the Alunno class
        Field[] fields = Alunno.class.getDeclaredFields();

        // Create the header row with field names
        Row headerRow = sheet.createRow(0);
        int columnCount = 0;
        for (Field field : fields) {
            createCell(headerRow, columnCount++, field.getName(), style);
        }

        // Format for date fields "dd-MM-yyyy"
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        for (Alunno record : studentList) {
            Row row = sheet.createRow(rowCount++);
            columnCount = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(record);

                    // Convert the value to String if it's a Date or any other object
                    String cellValue = (value instanceof Date) ? dateFormatter.format((Date) value) : String.valueOf(value);

                    // Check if the field is dataNascita and format it accordingly
                    if (field.getName().equals("dataNascita")) {
                        String[] lista= cellValue.split("-");
                        String anno= lista[0];
                        String mese= lista[1];
                        String giorno= lista[2];
                        String dataNasc= giorno+"-"+mese+"-"+anno; 
                        createCell(row, columnCount++, dataNasc, style);
                    }else if(field.getName().equals("utente")) {
                    	createCell(row , columnCount++, record.getUtente(), style );
                    }
                    
                    
                    else {
                        createCell(row, columnCount++, cellValue, style);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            
        }
        for(int i = 0; i<98; i++) {
        	sheet.autoSizeColumn(i);
        }
    }


    
    public void generateExcelFile(HttpServletResponse response, String i, String f) throws IOException {
        writeHeader();
        write();
        
       
        
        System.out.println("ecco la data inizio selezionata: " + i);
        
        String[] iSplit= i.split("-");
        String iSplitAnno= iSplit[0];
        String iSplitMese= iSplit[1];
        String iSplitGiorno= iSplit[2];
        
        String[] fSplit= f.split("-");
        String fSplitAnno= fSplit[0];
        String fSplitMese= fSplit[1];
        String fSplitGiorno= fSplit[2];
        
        String filename= "Rendicontazione_" + iSplitGiorno+"-"+iSplitMese+ "-" + iSplitAnno +"/"
        							  +fSplitGiorno+"-"+ fSplitMese+ "-" + fSplitAnno + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Set content type to Excel
        response.setHeader("Content-Disposition", "attachment; filename="+filename ); // Set filename with .xlsx extension
        
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}