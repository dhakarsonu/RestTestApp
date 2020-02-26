package com.mncm.endpoints.api;

import com.api.common.model.response.ApiResponse;
import com.api.common.model.response.FileUploadResponse;
import com.api.common.services.FileUploader;
import com.api.common.utils.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by sonudhakar on 24/02/18.
 */
@Slf4j
@Path("v1/file")
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class FileUploadEndpoint {

    @POST
    @Path("/upload")
    public Response uploadFile(HttpServletRequest request, @QueryParam("type") String type){

        ApiResponse response = new ApiResponse();

        try{

            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator fileIterator = upload.getItemIterator(request);
            ArrayList<FileUploadResponse> fileList = new ArrayList<>();

            while (fileIterator.hasNext()) {
                try{
                    FileItemStream item = fileIterator.next();

                    if (!item.isFormField() && "fileAttach".equals(item.getFieldName())) {
                        FileUploadResponse fileUploadResponse = new FileUploader().upload(item,type);
                        fileList.add(fileUploadResponse);
                    }
                }catch(Exception e){
                    log.info(e.getMessage(), e);
                }

            }

            if(!ObjUtil.isNullOrEmpty(fileList)){
                response.setOk(true);
                response.add("attachments",fileList);
            }

        }catch(Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok(response).build();

    }
}
