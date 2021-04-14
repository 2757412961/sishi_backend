package cn.edu.zju.sishi.commons.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


/**
 * @author: zjh
 * @date : 2021/4/4 15:29
 * @Email : 2757412961@qq.com
 * @update:
 */

public class FileUtils {
    /**
     * @Description: 判断文件或路径是否存在
     * @param: [path]
     * @return: boolean
     * @exception: IOException On input error.
     * @Author: zjh
     * @Date: 2021/4/14
     */
    public static boolean isExits(String path) {
        return Files.exists(Paths.get(path));
    }

    /**
     * @Description: 创建文件夹
     * @param: []
     * @return: boolean
     * @exception: IOException On input error.
     * @Author: zjh
     * @Date: 2021/4/14
     */
    @SneakyThrows
    public static boolean mkdirs(String... paths) {
        if (paths != null) {
            for (String pathItem : paths) {
                Path path = Paths.get(pathItem);
                Files.createDirectories(path);
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * @Description: 新建一个文件
     * @param: [path]
     * @return: boolean
     * @exception: IOException On input error.
     * @Author: zjh
     * @Date: 2021/4/14
     */
    public static File newFile(String path) {
        Path pathParent = Paths.get(path).getParent();
        mkdirs(pathParent.toString());

        return new File(path);
    }

    @SneakyThrows
    public static void delete(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                Files.delete(file.toPath());
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile : Objects.requireNonNull(files)) {
                    delete(subFile);
                }
                Files.delete(file.toPath());
            }
        }
    }

    /**
     * @Description: 删除文件或文件夹
     * @param: [file]
     * @return: boolean
     * @exception: IOException On input error.
     * @Author: zjh
     * @Date: 2021/4/14
     */
    public static boolean delete(String... paths) {
        if (paths == null) {
            return false;
        }
        for (String path : paths) {
            delete(new File(path));
        }

        return true;
    }


}
