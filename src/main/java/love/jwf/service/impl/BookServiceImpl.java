package love.jwf.service.impl;

import love.jwf.entity.BookInfo;
import love.jwf.mapper.BookMapper;
import love.jwf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 书籍信息的业务逻辑类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public void insert(BookInfo bookInfo) {
        bookMapper.insert(bookInfo);
    }

    @Override
    public boolean delete(Long id) {
        return bookMapper.delete(id);
    }

    @Override
    public int update(BookInfo bookInfo) {
        return bookMapper.update(bookInfo);
    }

    @Override
    public BookInfo selectById(Long id) {
        return bookMapper.selectById(id);
    }

    @Override
    public List<BookInfo> selectByName(String name) {
        return bookMapper.selectByName(name);
    }

    @Override
    public List<BookInfo> selectByAuthor(String author) {
        return bookMapper.selectByAuthor(author);
    }

    @Override
    public List<BookInfo> selectAll() {
        return bookMapper.selectAll();
    }
}


