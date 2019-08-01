package cn.shenghui.tutorial.controller;

import cn.shenghui.tutorial.rest.request.FileRequest;
import cn.shenghui.tutorial.rest.response.FileResponse;
import cn.shenghui.tutorial.service.FilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/31 12:57
 */
@RestController
@Api(value = "Filter")
public class FilterController {
    private FilterService filterService;

    @Autowired
    public void setFilterService(FilterService filterService) {
        this.filterService = filterService;
    }

    @ApiOperation(value = "filter sql.txt",
            notes = "statusCode = 1, success; statusCode = 0, return failed message.")
    @PostMapping("/filter")
    @Transactional(rollbackFor = Exception.class)
    public FileResponse filter(@RequestBody FileRequest fileRequest) {
        FileResponse response = new FileResponse();
        String path = fileRequest.getFilePath();
        File file = new File(path);
        BufferedReader reader = null;
        if (!file.exists()) {
            response.setStatusInfo(0, "File does not exist.");
        } else {
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString;
                int line = 0;
                int wrong = 0;
                JSONObject tempJson;
                StringBuilder errorMessages = new StringBuilder();
                errorMessages.append("错误信息：")
                        .append("\r\n");
                while ((tempString = reader.readLine()) != null) {
                    line++;
                    tempJson = filterService.filter(tempString);
                    if (tempJson.length() != 0) {
                        StringBuilder errorMessage = new StringBuilder();
                        errorMessage.append("错误行号：")
                                .append(line)
                                .append(",行内容：")
                                .append(tempJson.getString("content"))
                                .append(",错误详情：");
                        if(tempJson.length() > 1){
                            errorMessage.append(tempJson.getString("msg"));
                        }
                        errorMessage.append("\r\n");
                        errorMessages.append(errorMessage.toString());
                        wrong++;
                    }
                }
                response.setLine(line);
                response.setWrong(wrong);
                response.setStatusInfo(0, errorMessages + "");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException j) {
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
        return response;
    }
}
