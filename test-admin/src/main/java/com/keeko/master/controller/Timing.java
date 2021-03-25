package com.keeko.master.controller;

import com.keeko.utils.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 定时任务相关
 *
 * @author chensq
 */
@Component
public class Timing {

    /**
     * 0 0 0/1 * * ? 整点执行 0 0 0-23 * * ?
     * 经典案例：
     * “30 * * * * ?” 每半分钟触发任务
     * “30 10 * * * ?” 每小时的10分30秒触发任务
     * “30 10 1 * * ?” 每天1点10分30秒触发任务
     * “30 10 1 20 * ?” 每月20号1点10分30秒触发任务
     * “30 10 1 20 10 ? *” 每年10月20号1点10分30秒触发任务
     * “30 10 1 20 10 ? 2011” 2011年10月20号1点10分30秒触发任务
     * “30 10 1 ? 10 * 2011” 2011年10月每天1点10分30秒触发任务
     * “30 10 1 ? 10 SUN 2011” 2011年10月每周日1点10分30秒触发任务
     * “15,30,45 * * * * ?” 每15秒，30秒，45秒时触发任务
     * “15-45 * * * * ?” 15到45秒内，每秒都触发任务
     * “15/5 * * * * ?” 每分钟的每15秒开始触发，每隔5秒触发一次
     * “15-30/5 * * * * ?” 每分钟的15秒到30秒之间开始触发，每隔5秒触发一次
     * “0 0/3 * * * ?” 每小时的第0分0秒开始，每三分钟触发一次
     * “0 15 10 ? * MON-FRI” 星期一到星期五的10点15分0秒触发任务
     * “0 15 10 L * ?” 每个月最后一天的10点15分0秒触发任务
     * “0 15 10 LW * ?” 每个月最后一个工作日的10点15分0秒触发任务
     * “0 15 10 ? * 5L” 每个月最后一个星期四的10点15分0秒触发任务
     * “0 15 10 ? * 5#3” 每个月第三周的星期四的10点15分0秒触发任务
     * <p>
     * 0 0 18 * * ? 每天下午6点执行
     */
    //@Scheduled(cron = "0 0 0/1 * * ?")
    @Scheduled(cron = "20 * * * * ?")
    public void count(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //日期-1天
        calendar.add(Calendar.DATE, -1);
        String time = simpleDateFormat.format(calendar.getTime());

        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);

        System.out.println("定时任务服务开始=================================");
    }

    private String  getAdd1Year(String time){
        if (StringUtils.isNotEmpty(time)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parse;
            try {
                parse = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                return time;
            }
            Calendar ca = Calendar.getInstance();
            ca.setTime(parse);
            //当前时间减去一年，即一年前的时间
            ca.add(Calendar.YEAR, 1);
            return simpleDateFormat.format(ca.getTime());
        }
        return time;
    }

}
