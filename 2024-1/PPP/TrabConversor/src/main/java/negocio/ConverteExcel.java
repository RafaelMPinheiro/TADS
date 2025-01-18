package negocio;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConverteExcel extends ConverteArquivo {

  public ConverteExcel(String caminhoArquivo, String caracterSeparador) {
    super(caminhoArquivo, caracterSeparador);
  }

  @Override
  public void escreverArquivo() {
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream outputStream = new FileOutputStream(getNomeDoArquivo() + ".xlsx")) {
      XSSFSheet sheet = workbook.createSheet("CSV to Excel");

      int rowCount = 0;
      for (String linhas : getLinhas()) {
        Row row = sheet.createRow(rowCount++);

        String[] linha = linhas.split(getCaracterSeparador());
        int columnCount = 0;
        for (String valor : linha) {
          Cell cell = row.createCell(columnCount++);
          cell.setCellValue(valor);
        }
      }

      workbook.write(outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
