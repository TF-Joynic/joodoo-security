package indi.joynic.joodoo.security.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author Terrance Fung
 */
public class FileSystemKeyStore implements GeneralKeyStore {

    public static class Builder extends GeneralKeyStore.Builder {
        GeneralKeyStore.Builder filePath(String filePath) {

            File file = new File(filePath);

            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
            }

            this.inputStream(fileInputStream);
            return this;
        }

        @Override
        public BaseKeyStore build() {
            return new BaseKeyStore(this);
        }
    }
}