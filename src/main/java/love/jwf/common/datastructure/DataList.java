package love.jwf.common.datastructure;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONReader;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import love.jwf.entity.Book;
import love.jwf.entity.User;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Slf4j
@Component
@Data
public class DataList {
    private List<Book> books;
    private List<User> users;

    File bookFile;
    File userFile;

    public DataList() {
        books = new ArrayList<>();
        File userDirectory = FileUtils.getUserDirectory();
        File file = new File(userDirectory, ".jwf");
        if (!file.exists()) {
            file.mkdir();
        }
        bookFile = new File(file, "book.json");
        checkFile(bookFile);
        userFile = new File(file, "user.json");
        checkFile(userFile);
    }

    private boolean checkFile(File file) {
        String name = file.getName();
        try {
            if (!file.exists()) {
                file.createNewFile();
                log.info(name + "不存在，创建" + name + "文件");
            } else if (file.isDirectory()) {
                file.delete();
                log.info(name + "存在，但是是一个目录，删除后创建" + name + "文件");
                file.createNewFile();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            log.error(name + "文件创建失败！");
            return false;
        }
    }

    private <T> List<T> convertTo(Class<T> clazz, File file) {
        JSONArray jsonArray = null;
        try {
            jsonArray = JSON.parseArray(FileUtils.readFileToString(file, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonArray != null && jsonArray.size() != 0) {
            return jsonArray.toJavaList(clazz, JSONReader.Feature.IgnoreSetNullValue);
        }
        return null;
    }

    @PostConstruct
    public void init() {
        books = convertTo(Book.class, bookFile);
        users = convertTo(User.class, userFile);
    }

    @PreDestroy
    public void destroy(){
        try {
            FileUtils.writeStringToFile(bookFile,JSON.toJSONString(books),"UTF-8");
            FileUtils.writeStringToFile(userFile,JSON.toJSONString(users),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
