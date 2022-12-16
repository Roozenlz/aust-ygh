package love.jwf.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import love.jwf.entity.BorrowerInfo;
import love.jwf.mapper.BorrowMapper;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 借阅信息的持久化类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Slf4j
@Repository
public class BorrowMapperImpl implements BorrowMapper {

    private final File borrowerInfoFile;
    private List<BorrowerInfo> borrowerInfos;

    public BorrowMapperImpl() {
        borrowerInfoFile = new File(System.getProperty("user.dir") + "/borrowerInfo.xlsx");
        log.info("borrowerInfoFile：" + borrowerInfoFile.getAbsolutePath());
        if (!borrowerInfoFile.exists()) {
            try {
                borrowerInfoFile.createNewFile();
                log.info("borrowerInfo.xlsx文件不存在，创建文件成功！");
                initBookFile();
            } catch (IOException e) {
                log.error("borrowerInfo.xlsx文件创建失败！");
                e.printStackTrace();
            }
        }

        if (borrowerInfoFile.isDirectory()) {
            log.info("borrowerInfo.xlsx已存在，但是是一个目录。");
            if (borrowerInfoFile.delete()) {
                try {
                    borrowerInfoFile.createNewFile();
                    log.info("删除目录后创建成功！");
                    initBookFile();
                } catch (IOException e) {
                    log.error("borrowerInfo.xlsx文件创建失败！");
                    e.printStackTrace();
                }
            }
        }
    }

    private void initBookFile() {
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(borrowerInfoFile)) {
            XSSFSheet sheet = xssfWorkbook.createSheet();
            XSSFRow row = sheet.createRow(0);
            sheet.setColumnWidth(1, 256 * 15);
            sheet.setColumnWidth(2, 256 * 20);
            sheet.setColumnWidth(4, 256 * 25);
            sheet.setColumnWidth(5, 256 * 25);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("书证号");
            row.createCell(2).setCellValue("借出的书");
            row.createCell(3).setCellValue("归还期限");
            row.createCell(4).setCellValue("借出时间");
            row.createCell(5).setCellValue("归还时间");
            xssfWorkbook.setSheetName(0, "借阅信息");
            xssfWorkbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void init() {
        try (XSSFWorkbook wb = new XSSFWorkbook(borrowerInfoFile)) {
            XSSFSheet sheet = wb.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            borrowerInfos = new ArrayList<>(lastRowNum);
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Integer id = (int) row.getCell(0).getNumericCellValue();
                Long borrowerId = (long) row.getCell(1).getNumericCellValue();
                Long bookId = (long) row.getCell(2).getNumericCellValue();
                Integer dayNumber = (int) row.getCell(3).getNumericCellValue();
                LocalDateTime borrowDateTime = row.getCell(4).getLocalDateTimeCellValue();
                LocalDateTime backDateTime = null;
                if (row.getCell(5) != null) {
                    backDateTime = row.getCell(5).getLocalDateTimeCellValue();
                }
                BorrowerInfo borrowerInfo = new BorrowerInfo(id, borrowerId, bookId, dayNumber, borrowDateTime, backDateTime);
                borrowerInfos.add(borrowerInfo);
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy() {
        File backFile = new File(borrowerInfoFile.getAbsolutePath() + ".bak");
        if (backFile.exists()) {
            backFile.delete();
        }
        try {
            backFile.createNewFile();
            FileUtils.copyFile(borrowerInfoFile, backFile);
            borrowerInfoFile.delete();
            borrowerInfoFile.createNewFile();
            initBookFile();
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(borrowerInfoFile));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFCellStyle numCellStyle = workbook.createCellStyle();
            XSSFCellStyle dateCellStyle = workbook.createCellStyle();
            XSSFDataFormat dataFormat = workbook.createDataFormat();
            numCellStyle.setDataFormat(dataFormat.getFormat("0"));
            dateCellStyle.setDataFormat(dataFormat.getFormat("yyyy\"年\"m\"月\"d\"日\" hh:mm:ss"));
            int size = borrowerInfos.size();
            for (int i = 0; i < size; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                BorrowerInfo info = borrowerInfos.get(i);
                XSSFCell cell0 = row.createCell(0, CellType.NUMERIC);
                cell0.setCellValue(info.getId());
                cell0.setCellStyle(numCellStyle);
                XSSFCell cell1 = row.createCell(1, CellType.NUMERIC);
                cell1.setCellValue(info.getBorrowerId());
                cell1.setCellStyle(numCellStyle);
                XSSFCell cell2 = row.createCell(2, CellType.NUMERIC);
                cell2.setCellValue(info.getBookId());
                cell2.setCellStyle(numCellStyle);
                XSSFCell cell3 = row.createCell(3, CellType.NUMERIC);
                cell3.setCellValue(info.getDayNumber());
                cell3.setCellStyle(numCellStyle);
                XSSFCell cell4 = row.createCell(4, CellType.NUMERIC);
                cell4.setCellValue(info.getBorrowDateTime());
                cell4.setCellStyle(dateCellStyle);
                if (info.getBackDateTime() != null) {
                    XSSFCell cell5 = row.createCell(5, CellType.NUMERIC);
                    cell5.setCellValue(info.getBackDateTime());
                    cell5.setCellStyle(dateCellStyle);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(borrowerInfoFile);
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
    public void insert(BorrowerInfo borrowerInfo) {
        borrowerInfo.setId(borrowerInfos.size() + 1);
        borrowerInfo.setBorrowDateTime(LocalDateTime.now());
        borrowerInfos.add(borrowerInfo);
    }

    @Override
    public boolean update(Integer id) {
        for (BorrowerInfo b : borrowerInfos) {
            if (b.getId().equals(id)) {
                b.setBackDateTime(LocalDateTime.now());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BorrowerInfo> selectAll() {
        return borrowerInfos;
    }

    @Override
    public List<BorrowerInfo> selectByBorrowerId(Long id) {
        ArrayList<BorrowerInfo> ret = new ArrayList<>();
        for (BorrowerInfo b : borrowerInfos) {
            if (b.getBorrowerId().equals(id)) {
                ret.add(b);
            }
        }
        return ret;
    }

    @Override
    public BorrowerInfo selectById(Integer id) {
        for (BorrowerInfo b : borrowerInfos) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public List<BorrowerInfo> selectBeyond() {
        List<BorrowerInfo> ret = new ArrayList<>();
        for (BorrowerInfo b : borrowerInfos) {
            if (b.getBorrowDateTime().plusDays(b.getDayNumber()).isBefore(b.getBackDateTime() == null ? LocalDateTime.now() : b.getBackDateTime())) {
                ret.add(b);
            }
        }
        return ret;
    }
}
