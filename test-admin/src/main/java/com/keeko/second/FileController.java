package com.keeko.second;

import com.keeko.common.ResultResponse;
import com.keeko.common.enums.StatusEnum;
import com.keeko.master.entity.User;
import com.keeko.utils.StringUtils;
import com.keeko.utils.excel.ImportExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/test")
public class FileController {

    /**
     * Excel 导入
     *
     * @param file excel 督导的内容
     */
    @PostMapping("/addOrUpdate")
    public ResultResponse list(@RequestParam(value = "file", required = false) MultipartFile file) {
        List<User> planContentVOList = new ArrayList<>();
        String fileName = "";
        if (file != null) {
            fileName = file.getOriginalFilename();
            try {
                ImportExcel ei = new ImportExcel(file, 1, 0);
                planContentVOList = ei.getDataList(User.class);
                if (planContentVOList.size() <= 0 || StringUtils.isEmpty(planContentVOList.get(0).getName())) {
                    return new ResultResponse(false, "文件没有计划任务!");
                }
            } catch (Exception e) {
                return new ResultResponse(false, "导入文件失败！失败信息：" + e.getMessage());
            }
        }

        return new ResultResponse(false, "");
    }
}
