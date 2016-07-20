package com.android.mvp.utils.file;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yehuijifeng
 * on 2016/1/5.
 * 文件类型
 */
public class FileType {
    private List<String> fileTypes;

    //音乐类型文件
    public List<String> music() {
        fileTypes = new ArrayList<>();
        fileTypes.add("mp3");
        fileTypes.add("WAV");
        fileTypes.add("MIDI");
        fileTypes.add("MP3Pro");
        fileTypes.add("WMA");
        fileTypes.add("SACD");
        fileTypes.add("QuickTime");
        fileTypes.add("lrc");
        return fileTypes;
    }


    //视频类型文件
    public List<String> video() {
        fileTypes = new ArrayList<>();
        fileTypes.add("avi");
        fileTypes.add("mp4");
        fileTypes.add("mpeg");
        fileTypes.add("wmv");
        fileTypes.add("WMA");
        fileTypes.add("mov");
        fileTypes.add("flv");
        fileTypes.add("VQF");
        fileTypes.add("rmvb");
        return fileTypes;
    }

    //文档类型文件
    public List<String> word() {
        fileTypes = new ArrayList<>();
        fileTypes.add("doc");
        fileTypes.add("docx");
        return fileTypes;
    }

    //表格类型文件
    public List<String> excel() {
        fileTypes = new ArrayList<>();
        fileTypes.add("xls");
        fileTypes.add("xlsx");
        return fileTypes;
    }

    //表格类型文件
    public List<String> ppt() {
        fileTypes = new ArrayList<>();
        fileTypes.add("ppt");
        fileTypes.add("pptx");
        return fileTypes;
    }

    //记事本类型文件
    public List<String> txt() {
        fileTypes = new ArrayList<>();
        fileTypes.add("txt");
        return fileTypes;
    }

    //app类型文件
    public List<String> apk() {
        fileTypes = new ArrayList<>();
        fileTypes.add("apk");
        return fileTypes;
    }

    //图片类型文件
    public List<String> image() {
        fileTypes = new ArrayList<>();
        fileTypes.add("BMP");
        fileTypes.add("GIF");
        fileTypes.add("JPEG");
        fileTypes.add("TIFF");
        fileTypes.add("PSD");
        fileTypes.add("PNG");
        fileTypes.add("3gp");
        fileTypes.add("jpg");
        return fileTypes;
    }

    //压缩文件类型文件
    public List<String> zip() {
        fileTypes = new ArrayList<>();
        fileTypes.add("zip");
        fileTypes.add("rar");
        fileTypes.add("iso");
        fileTypes.add("tar");
        fileTypes.add("jar");
        fileTypes.add("UUEuue");
        fileTypes.add("ARJ");
        fileTypes.add("KZ");
        return fileTypes;
    }
}
