package com.matteoveroni.mydiary.bundles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ResourceBundleFileHandler {

    private static final String RESOURCE_BUNDLE_FILE_NAME = "default_locale.properties";
    private static final Logger LOG = LoggerFactory.getLogger(ResourceBundleFileHandler.class);

    public void setDefaultLocale(Locale locale) {
        Properties propertiesOfResourceBundleFile = new Properties();
        OutputStream outputStream = null;
        try {
            File resourceBundleFile = new File(RESOURCE_BUNDLE_FILE_NAME);
            propertiesOfResourceBundleFile.setProperty("locale", locale.toString());
            outputStream = new FileOutputStream(resourceBundleFile);
            propertiesOfResourceBundleFile.store(outputStream, RESOURCE_BUNDLE_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    public Locale getDefaultLocale() {
        Locale localeSetted;
        String defaultLocaleContent = readDefaultLocaleFileContent();
        switch (defaultLocaleContent) {
            case "en_EN":
                localeSetted = ResourceBundleFramework.SUPPORTED_ENGLISH_LOCALE;
                LOG.info("Locale setted to English");
                break;
            case "it_IT":
                localeSetted = ResourceBundleFramework.SUPPORTED_ITALIAN_LOCALE;
                LOG.info("Locale setted to Italian");
                break;
            default:
                localeSetted = ResourceBundleFramework.SUPPORTED_DEFAULT_LOCALE;
                LOG.info("Locale file damaged. Setting the Locale to default Locale");
                setDefaultLocale(ResourceBundleFramework.SUPPORTED_DEFAULT_LOCALE);
                break;
        }
        return localeSetted;
    }

    private String readDefaultLocaleFileContent() {
        Properties propertiesOfResourceBundleFile = new Properties();
        InputStream inputStream = null;
        String localeReadedFromResourceBundle = "";
        try {
            File file = new File(RESOURCE_BUNDLE_FILE_NAME);
            inputStream = new FileInputStream(file);
            propertiesOfResourceBundleFile.load(inputStream);
            localeReadedFromResourceBundle = propertiesOfResourceBundleFile.getProperty("locale");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
            }
        }
        return localeReadedFromResourceBundle;
    }

}
