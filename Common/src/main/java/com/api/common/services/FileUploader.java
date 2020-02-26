package com.api.common.services;

import com.api.common.model.common.Constants;
import com.api.common.model.response.FileUploadResponse;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.tools.cloudstorage.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by sonudhakar on 24/02/18.
 */
@Slf4j
public class FileUploader {

    public FileUploadResponse upload(FileItemStream fileInfo,String type) throws IOException {

        try {
            FileUploadResponse fileUploadResponse = new FileUploadResponse();
            GcsService gcsService = GcsServiceFactory.createGcsService();
            String appsBucket = Constants.gcsBucketName();
            String uuid = UUID.randomUUID().toString();
            String fileName = String.format(type+"/%s/%s", uuid, fileInfo.getName());

            // uploading file to gcs
            GcsFileOptions options = new GcsFileOptions.Builder().acl("public-read").mimeType(fileInfo.getContentType()).build();
            GcsFilename gcsFileName = new GcsFilename(appsBucket, fileName);

            GcsOutputChannel writeChannel = gcsService.createOrReplace(gcsFileName, options);
            ByteBuffer byteBuffer = ByteBuffer.wrap(IOUtils.toByteArray(fileInfo.openStream()));
            writeChannel.write(byteBuffer);
            writeChannel.close();
            // getting servicingUrl of stored file in gcs
            String servingUrl = "http://" + gcsFileName.getBucketName() + "/" + gcsFileName.getObjectName();

            fileUploadResponse.setFileLink(servingUrl);

            if(type.equals("application")) {
                fileUploadResponse.setContentType(fileInfo.getContentType());
                fileUploadResponse.setFileName(fileInfo.getName());
                fileUploadResponse.setSize(byteBuffer.array().length);
                fileUploadResponse.setFilePath(fileName);
                switch (fileInfo.getContentType()) {
                    case "image/png":
                    case "image/jpeg":
                    case "image/gif":
                    case "image/bmp":
                        ImagesService imagesService = ImagesServiceFactory.getImagesService();
                        ServingUrlOptions servingUrlOptions = ServingUrlOptions.Builder.withGoogleStorageFileName("/gs/"+gcsFileName.getBucketName() + "/" + gcsFileName.getObjectName()).secureUrl(true);
                        String thumbnailUrl = imagesService.getServingUrl(servingUrlOptions);
                        thumbnailUrl += getAspectRatio(thumbnailUrl);
                        fileUploadResponse.setThumbnail(thumbnailUrl);
                        break;

                }
            }
            return fileUploadResponse;

        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return null;
    }

    public boolean deleteFileFromGCS(String fileName) throws IOException{

        try{

            GcsService gcsService = GcsServiceFactory.createGcsService();
            String appsBucket = Constants.gcsBucketName();
            GcsFilename gcsFileName = new GcsFilename(appsBucket, fileName);

            return gcsService.delete(gcsFileName);

        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    private String getAspectRatio(String url){
        String ratio = " ";
        try {
            boolean flag = false;
            double aspectRatio;
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"=s32").openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            if(conn.getResponseCode() >= HttpURLConnection.HTTP_OK && conn.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {
                Metadata metadata = ImageMetadataReader.readMetadata(conn.getInputStream());
                loop : for(Directory directory : metadata.getDirectories()) {
                    for(Tag tag : directory.getTags()) {
                        switch(tag.getTagName()) {
                            case "Image Height" : {
                                Img.Height.setValue(tag.getDescription());
                                if(flag) {
                                    break loop;
                                }
                                flag = true;
                            }
                            break;
                            case "Image Width" : {
                                Img.Width.setValue(tag.getDescription());
                                if(flag) {
                                    break loop;
                                }
                                flag = true;
                            }
                            break;
                        }
                    }
                }
                if(Img.Width.value > 0 && Img.Height.value > 0) {
                    aspectRatio = (double) Math.round(Img.Width.value/Img.Height.value * 1000) / 1000;
                    Img.AspectRatio.value = aspectRatio;
                    ratio = "?aspectRatio="+aspectRatio;
                    log.info("Aspect ratio of image : "+aspectRatio);
                } else {
                    log.info("Wrong metadata value");
                }
            }
        } catch(Exception e) {
            log.info("Exception occured on finding aspect ratio of image");
            log.info(e.getMessage());
        }
        return ratio;
    }

    public enum Img {
        Height(0),Width(0),AspectRatio(0);
        private double value;

        private Img(double value) {
            this.value = value;
        }
        public void setValue(String value) {
            try{
                int pos = value.indexOf(" ");
                if(pos > -1){
                    value = value.substring(0, pos);
                }
                this.value = Double.parseDouble(value);
            } catch(Exception e){
                log.info("Exception on image metadata conversion");
                log.info(e.getMessage());
            }
        }
    }

}
