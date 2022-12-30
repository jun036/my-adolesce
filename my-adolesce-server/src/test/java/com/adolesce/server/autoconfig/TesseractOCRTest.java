package com.adolesce.server.autoconfig;

import cn.hutool.core.io.FileUtil;
import com.adolesce.common.utils.ocr.TesseractOCRUtils;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/9/26 20:33
 */
public class TesseractOCRTest {
    @Test
    public void test() throws IOException, TesseractException {
        TesseractOCRUtils utils = new TesseractOCRUtils();
        File file = new File("D:\\some-test-file\\pic\\guanggao4.png");
        byte[] bytes = FileUtil.readBytes(file);
        //byte[] 转换为bufferedImage
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(in);

        String result = utils.doOCR(bufferedImage);
        System.out.println(result);
    }
}
