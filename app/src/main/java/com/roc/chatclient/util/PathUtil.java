package com.roc.chatclient.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class PathUtil {

    public static String pathPrefix;
    public static final String historyPathName = "/chat/";
    public static final String imagePathName = "/image/";
    public static final String voicePathName = "/voice/";
    public static final String filePathName = "/file/";
    public static final String videoPathName = "/video/";
    public static final String netdiskDownloadPathName = "/netdisk/";
    public static final String meetingPathName = "/meeting/";
    private static File storageDir = null;
    private static PathUtil instance = null;
    private File voicePath = null;
    private File imagePath = null;
    private File thumbImagePath = null;
    private File historyPath = null;
    private File videoPath = null;
    private File filePath;

    private PathUtil() {
    }

    public static PathUtil getInstance() {
        if (instance == null) {
            instance = new PathUtil();
        }

        return instance;
    }

    public void initDirs(String var1, String var2, Context var3) {
        String var4 = var3.getPackageName();
        pathPrefix = "/Android/data/" + var4 + "/";
        this.voicePath = generateVoicePath(var1, var2, var3);
        if (!this.voicePath.exists()) {
            this.voicePath.mkdirs();
        }

        this.imagePath = generateImagePath(var1, var2, var3);
        if (!this.imagePath.exists()) {
            this.imagePath.mkdirs();
        }

        this.thumbImagePath = generateThumbImagePath(var1, var2, var3);
        if (!this.thumbImagePath.exists()) {
            this.thumbImagePath.mkdir();
        }

        this.historyPath = generateHistoryPath(var1, var2, var3);
        if (!this.historyPath.exists()) {
            this.historyPath.mkdirs();
        }

        this.videoPath = generateVideoPath(var1, var2, var3);
        if (!this.videoPath.exists()) {
            this.videoPath.mkdirs();
        }

        this.filePath = generateFiePath(var1, var2, var3);
        if (!this.filePath.exists()) {
            this.filePath.mkdirs();
        }

    }

    public File getImagePath() {
        return this.imagePath;
    }

    public File getVoicePath() {
        return this.voicePath;
    }

    public File getFilePath() {
        return this.filePath;
    }

    public File getVideoPath() {
        return this.videoPath;
    }

    public File getThumbImagePath() {
        return this.thumbImagePath;
    }

    public File getHistoryPath() {
        return this.historyPath;
    }

    private static File getStorageDir(Context var0) {
        if (storageDir == null) {
            File var1 = Environment.getExternalStorageDirectory();
            if (var1.exists()) {
                return var1;
            }

            storageDir = var0.getFilesDir();
        }

        return storageDir;
    }

    private static File generateImagePath(String var0, String var1, Context var2) {
        String var3 = null;
        if (StringUtils.isEmpty(var0)) {
            var3 = pathPrefix + var1 + imagePathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + imagePathName;
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateThumbImagePath(String var0, String var1, Context var2) {
        String var3 = null;
        if (StringUtils.isEmpty(var0)) {
            var3 = pathPrefix + var1 + imagePathName + "thumb/";
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + imagePathName + "thumb/";
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateVoicePath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + voicePathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + voicePathName;
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateFiePath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + filePathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + filePathName;
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateVideoPath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + videoPathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + videoPathName;
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateHistoryPath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + historyPathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + historyPathName;
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateMessagePath(String var0, String var1, Context var2) {
        File var3 = new File(generateHistoryPath(var0, var1, var2), var1 + File.separator + "Msg.db");
        return var3;
    }

    public static File getTempPath(File var0) {
        File var1 = new File(var0.getAbsoluteFile() + ".tmp");
        return var1;
    }
}