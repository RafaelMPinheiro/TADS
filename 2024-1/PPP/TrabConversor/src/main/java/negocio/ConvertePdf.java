package negocio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertePdf extends ConverteArquivo {

  public ConvertePdf(String caminhoArquivo, String caracterSeparador) {
    super(caminhoArquivo, caracterSeparador);
  }

  private void addTableHeader(PdfPTable table) {
    Stream.of(getLinhas().get(0).split(getCaracterSeparador())).forEach(columnTitle -> {
      PdfPCell header = new PdfPCell();
      header.setBorderWidth(1);
      header.setPhrase(new Phrase(columnTitle));
      table.addCell(header);
    });
  }

  @Override
  public void escreverArquivo() {
    try {
      Document document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(getNomeDoArquivo() + ".pdf", true));

      document.open();

      PdfPTable table = new PdfPTable(getLinhas().get(0).split(getCaracterSeparador()).length);
      addTableHeader(table);
      for (int i = 1; i < getLinhas().size(); i++) {
        for (String cell : getLinhas().get(i).split(getCaracterSeparador())) {
          PdfPCell row = new PdfPCell();
          row.setBorderWidth(1);
          row.setPhrase(new Phrase(cell));
          table.addCell(row);
        }
      }

      document.add(table);
      document.close();
    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
  }

}
