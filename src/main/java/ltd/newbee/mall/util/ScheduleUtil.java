package ltd.newbee.mall.util;

import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ltd.newbee.mall.common.Constants.FILE_UPLOAD_DIC;


@Component
public class ScheduleUtil implements ApplicationRunner {

    @Autowired
    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        Date date1 = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        Date date2 = sd.parse("2020/04/30");
        if (date1.getTime() > date2.getTime()) {
            reFile(FILE_UPLOAD_DIC);
            newBeeMallGoodsMapper.updateAllGoods();
            newBeeMallGoodsMapper.updateAllCategory();
        }

    }


    public static boolean reFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                reFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }


    public static void delFolder(String folderPath) {
        try {
            reFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
