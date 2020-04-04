package com.forestsoftware.receipe.service;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class Imageupload {
    public String uploadImage(MultipartFile file, String uploadPath, String physicalUploadPath ) {

        String filePath = physicalUploadPath + file.getOriginalFilename();

        try {
            File targetFile=new File(filePath);
            FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadPath + "/" + file.getOriginalFilename();
    }

//    public String watermarkAdd( File imgFile, String imageFileName, String uploadPath, String realUploadPath ) {
//
//        String imgWithWatermarkFileName = "watermark_" + imageFileName;
//        OutputStream os = null;
//
//        try {
//            Image image = ImageIO.read(imgFile);
//
//            int width = image.getWidth(null);
//            int height = image.getHeight(null);
//
//            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  // ①
//            Graphics2D g = bufferedImage.createGraphics();  // ②
//            g.drawImage(image, 0, 0, width,height,null);  // ③
//
//            String logoPath = realUploadPath + "/" + Const.LOGO_FILE_NAME;  // Watermark image address
//            File logo = new File(logoPath);        // read the watermark image
//            Image imageLogo = ImageIO.read(logo);
//
//            int markWidth = imageLogo.getWidth(null);    // the width and height of the watermark image
//            int markHeight = imageLogo.getHeight(null);
//
//            g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Const.ALPHA) );  //Set the watermark transparency
//            g.rotate(Math.toRadians(-10), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);  //Set the rotation of the watermark image
//
//            int x = Const.X;
//            int y = Const.Y;
//
//            int xInterval = Const.X_INTERVAL;
//            int yInterval = Const.Y_INTERVAL;
//
//            double count = 1.5;
//            while ( x < width*count ) {  // Add multiple watermarks logo in a loop
//                    y = -height / 2;
//                while( y < height*count ) {
//                    g.drawImage(imageLogo, x, y, null);  // ④
//                    y += markHeight + yInterval;
//                }
//                x += markWidth + xInterval;
//            }
//
//            g.dispose();
//
//            os = new FileOutputStream(realUploadPath + "/" + imgWithWatermarkFileName);
//            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os); // ⑤
//            en.encode(bufferedImage); // ⑥
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(os!=null){
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return uploadPath + "/" + imgWithWatermarkFileName;
//    }
}
