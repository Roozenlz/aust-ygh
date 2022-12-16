package love.jwf;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@SpringBootTest
public class PoiTest {
    @Test
    public void test() throws IOException, InvalidFormatException {
        File bookInfo = new File(System.getProperty("user.dir") + "/.jwf/bookInfo.xlsx");
        System.out.println(bookInfo.getAbsolutePath());
        try (
//             FileOutputStream out = new FileOutputStream(bookInfo);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(bookInfo))) {
//            XSSFSheet sheet = xssfWorkbook.createSheet();
//            XSSFRow row = sheet.createRow(0);
//            row.createCell(0).setCellValue("书号");
//            row.createCell(1).setCellValue("书名");
//            row.createCell(2).setCellValue("著作者");
//            row.createCell(3).setCellValue("现存量");
//            row.createCell(4).setCellValue("库存量");
//            xssfWorkbook.setSheetName(0, "书籍信息");
//            xssfWorkbook.write(out);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            xssfWorkbook.write(new FileOutputStream(bookInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
