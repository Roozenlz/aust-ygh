package love.jwf.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import love.jwf.entity.BookInfo;
import love.jwf.mapper.BookMapper;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 书籍信息的持久化类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Slf4j
@Repository
public class BookMapperImpl implements BookMapper {

    private final File bookInfoFile;
    private List<BookInfo> bookInfos;

    public BookMapperImpl() {
        bookInfoFile = new File(System.getProperty("user.dir") + "/bookInfo.xlsx");
        log.info("bookInfoFile：" + bookInfoFile.getAbsolutePath());
        if (!bookInfoFile.exists()) {
            try {
                bookInfoFile.createNewFile();
                log.info("bookInfo.xlsx文件不存在，创建文件成功！");
                initBookFile();
            } catch (IOException e) {
                log.error("bookInfo.xlsx文件创建失败！");
                e.printStackTrace();
            }
        }

        if (bookInfoFile.isDirectory()) {
            log.info("bookInfo.xlsx已存在，但是是一个目录。");
            if (bookInfoFile.delete()) {
                try {
                    bookInfoFile.createNewFile();
                    log.info("删除目录后创建成功！");
                    initBookFile();
                } catch (IOException e) {
                    log.error("bookInfo.xlsx文件创建失败！");
                    e.printStackTrace();
                }
            }
        }
    }

    private void initBookFile() {
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(bookInfoFile)) {
            XSSFSheet sheet = xssfWorkbook.createSheet();
            XSSFRow row = sheet.createRow(0);
            sheet.setColumnWidth(0, 256 * 20);
            sheet.setColumnWidth(1, 256 * 30);
            sheet.setColumnWidth(2, 256 * 10);
            sheet.setColumnWidth(3, 256 * 10);
            sheet.setColumnWidth(4, 256 * 10);
            row.createCell(0).setCellValue("书号");
            row.createCell(1).setCellValue("书名");
            row.createCell(2).setCellValue("著作者");
            row.createCell(3).setCellValue("现存量");
            row.createCell(4).setCellValue("库存量");
            xssfWorkbook.setSheetName(0, "书籍信息");
            xssfWorkbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void init() {
        try (XSSFWorkbook wb = new XSSFWorkbook(bookInfoFile)) {
            XSSFSheet sheet = wb.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            bookInfos = new ArrayList<>(lastRowNum);
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Long id = (long) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                String author = row.getCell(2).getStringCellValue();
                Integer standingStock = (int) row.getCell(3).getNumericCellValue();
                Integer stock = (int) row.getCell(4).getNumericCellValue();
                BookInfo bookInfo = new BookInfo(id, name, author, standingStock, stock);
                bookInfos.add(bookInfo);
            }

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy() {
        File backFile = new File(bookInfoFile.getAbsolutePath() + ".bak");
        if (backFile.exists()) {
            backFile.delete();
        }
        try {
            backFile.createNewFile();
            FileUtils.copyFile(bookInfoFile, backFile);
            bookInfoFile.delete();
            bookInfoFile.createNewFile();
            initBookFile();
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(bookInfoFile));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFCellStyle numCellStyle = workbook.createCellStyle();
            XSSFDataFormat dataFormat = workbook.createDataFormat();
            numCellStyle.setDataFormat(dataFormat.getFormat("0"));
            int size = bookInfos.size();
            for (int i = 0; i < size; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                BookInfo info = bookInfos.get(i);
                XSSFCell cell0 = row.createCell(0, CellType.NUMERIC);
                cell0.setCellValue(info.getId());
                cell0.setCellStyle(numCellStyle);
                row.createCell(1, CellType.STRING).setCellValue(info.getName());
                row.createCell(2, CellType.STRING).setCellValue(info.getAuthor());
                row.createCell(3, CellType.NUMERIC).setCellValue(info.getStandingStock());
                row.createCell(4, CellType.NUMERIC).setCellValue(info.getStock());
            }
            FileOutputStream fileOutputStream = new FileOutputStream(bookInfoFile);
            workbook.write(fileOutputStream);

            //一定要刷新！！！
            fileOutputStream.flush();
            fileOutputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insert(BookInfo bookInfo) {
        bookInfos.add(bookInfo);
        bookInfos.sort((b1, b2) -> (int) (b1.getId() - b2.getId()));
    }

    @Override
    public boolean delete(Long id) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(id);
        int index = Collections.binarySearch(bookInfos, bookInfo, (b1, b2) -> (int) (b1.getId() - b2.getId()));
        if (index >= 0 && index < bookInfos.size()) {
            bookInfos.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public int update(BookInfo bookInfo) {
        if (bookInfo == null) {
            return 0;
        }
        int count = 0;
        BookInfo bookInfo1 = null;
        for (BookInfo b : bookInfos) {
            if (b.getId().equals(bookInfo.getId())) {
                bookInfo1 = b;
            }
        }
        if (bookInfo.getName() != null && !"".equals(bookInfo.getName())) {
            bookInfo1.setName(bookInfo.getName());
            count++;
        }
        if (bookInfo.getAuthor() != null && !"".equals(bookInfo.getAuthor())) {
            bookInfo1.setAuthor(bookInfo.getAuthor());
            count++;
        }
        if (bookInfo.getStock() != null) {
            bookInfo1.setStock(bookInfo.getStock());
            count++;
        }
        if (bookInfo.getStandingStock() != null) {
            bookInfo1.setStandingStock(bookInfo.getStandingStock());
            count++;
        }
        return count;
    }

    @Override
    public BookInfo selectById(Long id) {
        for (BookInfo b : bookInfos) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public List<BookInfo> selectByName(String name) {
        List<BookInfo> ret = new ArrayList<>();
        for (BookInfo b : bookInfos) {
            if (b.getName().contains(name)) {
                ret.add(b);
            }
        }
        return ret;
    }

    @Override
    public List<BookInfo> selectByAuthor(String author) {
        List<BookInfo> ret = new ArrayList<>();
        for (BookInfo b : bookInfos) {
            if (b.getAuthor().equals(author)) {
                ret.add(b);
            }
        }
        return ret;
    }

    @Override
    public List<BookInfo> selectAll() {
        return bookInfos;
    }
}
