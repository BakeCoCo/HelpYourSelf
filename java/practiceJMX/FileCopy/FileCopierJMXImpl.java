package com.example.test;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

public class FileCopierJMXImpl implements FileCopier{

    @Override
    public void copyFile(String srcDir,String destDir,String filename) throws IOException {
        File srcFile = new File(srcDir,filename);
        File destFile = new File(destDir,filename);
        FileCopyUtils.copy(srcFile,destFile);
    }
}
