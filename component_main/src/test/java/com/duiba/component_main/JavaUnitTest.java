package com.duiba.component_main;

import com.orhanobut.logger.Logger;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class JavaUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        File file = new File("C:\\Users\\Jin\\Desktoptext.txt");
        readTextWithIo(file);
    }

    /**
     * 普通io
     */
    private void readTextWithIo(File file) {
        BufferedReader br = null;
        String sCurrentLine;
        try {
            br = new BufferedReader(
                    new FileReader(file));
            while ((sCurrentLine = br.readLine()) != null) {
                Logger.v(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}