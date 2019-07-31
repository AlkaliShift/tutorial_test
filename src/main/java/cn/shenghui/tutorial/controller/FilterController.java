package cn.shenghui.tutorial.controller;

import cn.shenghui.tutorial.rest.request.FileRequest;
import cn.shenghui.tutorial.rest.response.FileResponse;
import cn.shenghui.tutorial.service.FilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.ArrayList;

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
    public void setFilterService(FilterService filterService){
        this.filterService = filterService;
    }

    @ApiOperation(value = "filter sql.txt",
            notes = "statusCode = 1, success; statusCode = 0, return failed message.")
    @PostMapping("/filter")
    public FileResponse filter(@RequestBody FileRequest fileRequest){
        FileResponse response = new FileResponse();
        String path = fileRequest.getFilePath();
        File file = new File(path);
        BufferedReader reader = null;
        if (!file.exists()){
            response.setStatusInfo(0, "File does not exist.");
        }else{
            try{
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 0;
                int wrong = 0;
                ArrayList<String[]> temp = new ArrayList<String[]>();
                while ((tempString = reader.readLine()) != null) {
                    temp.add(tempString.split(","));
                    line++;
                }
                String[] tempStringList;
                JSONObject tempJson = new JSONObject();
                String msg = "";
                for(int i = 0; i < temp.size(); i++){
                    tempStringList = temp.get(i);
                    tempJson = filterService.filter(tempStringList);
                    if((tempJson) != null){
                        msg = msg + tempJson.getString("id") + " " + tempJson.getString("msg") + "\n\n\n";
                        wrong++;
                    }
                }
                response.setLine(line);
                response.setWrong(wrong);
                response.setStatusInfo(0, msg);
            }catch (IOException e){
                e.printStackTrace();
            }catch (JSONException j){
                j.getMessage();
            }finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe) {
                    }
                }
            }
        }
        return response;
    }
}
