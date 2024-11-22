package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

// file ----workbook---sheet----row---cell
public class ExcelUtility {
    public FileInputStream fis;
    public XSSFWorkbook wb;
    public XSSFSheet sh;
    public XSSFRow r;
    public XSSFCell c;
    String path;

    ExcelUtility(String path)
    {
        this.path=path;
    }

//    create a method to get total row count
    public int rowCount(String sheetname) throws IOException {
        fis= new FileInputStream(path);
        wb=new XSSFWorkbook(fis);
        sh=wb.getSheet(sheetname);
        int lastrowno=sh.getLastRowNum();
        wb.close();
        fis.close();
        return lastrowno;
    }

    // create a method to get last cell no
    public int cellCount(String sheetname, int rowno) throws IOException {

        fis= new FileInputStream(path);
        wb=new XSSFWorkbook(fis);
        sh=wb.getSheet(sheetname);
        r=sh.getRow(rowno);
        int lastcellno=r.getLastCellNum();
        wb.close();
        fis.close();
        return lastcellno;
    }

    // create method to get cell data
    public String cellData(String sheetname,int rowno, int cellno) throws IOException {

        fis= new FileInputStream(path);
        wb=new XSSFWorkbook(fis);
        sh=wb.getSheet(sheetname);
        r=sh.getRow(rowno);
        c=r.getCell(cellno);
        String cellvalue;
        try {
//        cellvalue=ce.toString();
            DataFormatter formatter= new DataFormatter();
            cellvalue=formatter.formatCellValue(c);


        } catch (Exception e) {
            cellvalue="";
        }
        wb.close();
        fis.close();
        return cellvalue;
    }


}
