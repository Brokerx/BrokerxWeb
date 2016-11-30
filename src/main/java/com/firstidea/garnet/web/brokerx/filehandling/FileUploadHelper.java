package com.firstidea.garnet.web.brokerx.filehandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.jboss.logging.Logger;

/**
 *
 * @author Govind
 */
public class FileUploadHelper {

    public static String fileSeparator = System.getProperty("file.separator");
//    public static String UPLOAD_DIRECTORY_PATH = System.getProperty("jboss.home.dir")
//                                                        +fileSeparator
//                                                        +PMBSSubstypeConstants.CURRENT_SUBS_TYPE_NODE_NAME
//                                                        //+"standalone"
//                                                        +fileSeparator
//                                                        +"UploadedFiles"
//                                                        +fileSeparator
//                                                        +"Subscriptions"
//                                                        +fileSeparator;
    
    public static String IMAGES_URL = "http://localhost:8080/Garnet/Images/";
    
    public static String FILTE_TYPE_ADVERTISEMENT = "Advertisements";
    public static String FILTE_TYPE_CATEGORY_INFO = "CategoryInfo";
    public static String FILTE_TYPE_COMMUNICATIONS = "Communications";
    public static String FILTE_TYPE_USER_PROFILE_PHOTO = "UserProfilePhotos";

    public static String getUploadDirectoryPath() {
        //System.getProperty("jboss.home.dir") Use it for Jboss
        String UPLOAD_DIRECTORY_PATH = System.getProperty("catalina.base") //For Tomcat
                + fileSeparator
                + "BrokerxFiles"
                + fileSeparator;

        return UPLOAD_DIRECTORY_PATH;
    }

    public static boolean UploadImage(FileItem inputPart, String fileName) {
//        for (InputPart inputPart : inputParts) {

        try {

            InputStream inputStream = inputPart.getInputStream();//(InputStream.class, null);

            byte[] bytes = IOUtils.toByteArray(inputStream);

            writeFile(bytes, fileName);

        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(FileUploadHelper.class).error(e.getMessage());
            return false;
        }

//        }
        return true;

    }

    /**
     * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
     * name="file"; filename="filename.extension"] }
     *
     */
    public static String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    /**
     * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
     * name="file"; filename="filename.extension"] }
     *
     */
    public static String getFileType(MultivaluedMap<String, String> header) {
        String[] contentType = header.getFirst("Content-Type").split("/");

        if (contentType.length > 0) {
            return contentType[0];
        }

        return "unknown";
    }

    //save to somewhere
    private static void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);
       
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }
    
    public static String[] getPathToUploadFile(String fileName, String... fileCategory) {
        String [] resultParams = {"",""};
        String path = getUploadDirectoryPath(), fileExtension = "jpg"; //default extension for webcam image
        String fileNameDetails[] = fileName.split("\\.");
        Integer maxLength = 200;
        if (fileNameDetails.length >= 2) {
            fileExtension = fileNameDetails[fileNameDetails.length - 1];
            fileName = fileName.substring(0, fileName.indexOf("." + fileExtension));
            fileExtension = fileExtension.toLowerCase();
        }
         path += fileCategory[0] + fileSeparator;
         if(fileCategory.length > 1) {
             for(int i=1; i< fileCategory.length; i++) {
                 path += fileCategory[i] + fileSeparator;
             }
         }
        if (checkIfExistOrCreateDirectory(path)) {
                fileName = fileName.replaceAll("[@#$%&*:^]", "");
                if (fileName.length() > maxLength) {
                    fileName = fileName.substring(0, maxLength);
                }
                resultParams[1] = fileName + "_" + System.currentTimeMillis() + "." + fileExtension;
                path += resultParams[1];
                resultParams[0] = path;
                
                return resultParams;
            } else {
                return null;
            }
    }
    public static boolean createThumbNailForUploadedImage(String uploadPath, String uploadedFileName,
            String thumbNailImagePath, Boolean isThumb, Integer printHeight, Integer printWidth) throws IOException {
        String fileNamePreFix = "thumb_";
        String fileFormat = "jpg";
        if (uploadedFileName.toLowerCase().endsWith(".jpeg")) {
            fileFormat = "jpeg";
        } else if (uploadedFileName.toLowerCase().endsWith(".png")) {
            fileFormat = "png";
        }
        Integer height = 200, width = 200;
        if (!isThumb) {
            fileNamePreFix = "print_";
            height = printHeight;
            width = printWidth;
        }
        File uploadedImage = new File(uploadPath);
        if (!uploadedImage.exists()) {
            return false;
        }
        BufferedImage img = ImageIO.read(uploadedImage); // load image

//          Quality indicate that the scaling implementation should do everything
//          create as nice of a result as possible , other options like speed
//          will return result as fast as possible
//          Automatic mode will calculate the resultant dimensions according
//          to image orientation .so resultant image may be size of 50*36.if you want
//          fixed size like 50*50 then use FIT_EXACT
//          other modes like FIT_TO_WIDTH..etc also available.
        BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
                height, width, Scalr.OP_ANTIALIAS);

//        convert bufferedImage to outpurstream 
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(thumbImg, "jpg", os);
//        or wrtite to a file
        String thumbNailImageName = fileNamePreFix + uploadedFileName;
        File thumbNailImage = new File(thumbNailImagePath + fileSeparator + thumbNailImageName);
        ImageIO.write(thumbImg, fileFormat, thumbNailImage);

        return true;
    }
    private static boolean checkIfExistOrCreateDirectory(String path) {
        File file = new File(path);
        try {
            if (file.isDirectory()) {
                return true;
            } else {
                return file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Logger.getLogger(FileUploadHelper.class).errorf(" checkIfExistOrCreateDirectory() : ", e.getMessage());
        }
        return false;
    }

    public static boolean deleteFile(String fileName) {
        fileName = fileName.replace("/", fileSeparator);
        String filePath = getUploadDirectoryPath() + fileName;

        File file = new File(filePath);

        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
            return true;
        } else {
            System.out.println("Delete operation is failed.");
        }
        return false;
    }
}
