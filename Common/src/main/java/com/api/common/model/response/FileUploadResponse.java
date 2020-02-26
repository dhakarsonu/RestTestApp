package com.api.common.model.response;

import lombok.Data;

/**
 * Created by sonudhakar on 17/03/18.
 */
@Data
public class FileUploadResponse {

    private String fileName;

    private String fileLink;

    private String contentType;

    private long size;

    private String thumbnail;

    private String filePath;

}
