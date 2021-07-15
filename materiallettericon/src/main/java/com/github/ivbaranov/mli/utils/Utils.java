package com.github.ivbaranov.mli.utils;

import ohos.agp.text.Font;
import ohos.app.Context;
import ohos.global.resource.RawFileDescriptor;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utils.
 */
public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MaterialLetterIcon");

    /**
     * returns the font given the path of fontfamilty.
     *
     * @param context context.
     * @param fontfamily fontfamily
     * @return font
     */
    public static Font getFont(Context context, String fontfamily) {
        byte[] buffer = null;
        int bytesRead = 0;
        File file = new File(context.getCacheDir(), fontfamily);
        RawFileEntry rawFileEntry = context.getResourceManager().getRawFileEntry(fontfamily);
        try (Resource resource = rawFileEntry.openRawFile();
             FileOutputStream fileOutputStream = new FileOutputStream(file);
             RawFileDescriptor rawFileDescriptor = rawFileEntry.openRawFileDescriptor()) {
            buffer = new byte[(int) rawFileDescriptor.getFileSize()];
            bytesRead = resource.read(buffer);
            fileOutputStream.write(buffer, 0, bytesRead);
        } catch (IOException ioException) {
            HiLog.error(label, "Font family not identified.");
        }
        return new Font.Builder(file).build();
    }
}
