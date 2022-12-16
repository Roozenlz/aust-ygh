package love.jwf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class AustYghApplicationTests {

    @Test
    public void getCurrentJarDir() {
        try {
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
            path = java.net.URLDecoder.decode(path, "UTF-8");
            File file = new File(path);
            System.out.println(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
