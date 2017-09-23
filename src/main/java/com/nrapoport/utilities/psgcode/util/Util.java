/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 16, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;

import org.apache.commons.io.FileUtils;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Utility methods for the project</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class Util {
    //@SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Util.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Copy all files from a passed {@code oldPath} to the projects ./data directory</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param oldPath
     */
    public static void copyFilesToData(final String oldPath) {
        final Path absoluteSourcePath = FileSystems.getDefault().getPath(oldPath).toAbsolutePath();
        final Path absoluteTargetPath = FileSystems.getDefault().getPath("data").toAbsolutePath();

        try {
            FileUtils.copyDirectory(absoluteSourcePath.toFile(), absoluteTargetPath.toFile(), true);
            //             Path res = java.nio.file.Files.copy(
            //                absoluteSourcePath,
            //                absoluteTargetPath,
            //                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
            //                java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
            //                java.nio.file.LinkOption.NOFOLLOW_LINKS);
        } catch (final IOException ex) {
            final String msg = String.format("Error copying files to './data' from path '%1$s'", oldPath);
            log.error(msg, ex);
            final RuntimeException newEx = new RuntimeException(msg, ex);
            newEx.fillInStackTrace();
            throw newEx;
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Create a new JSON file for the passed name. if that file already exists, do nothing</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param jsonFile
     *            the file to create
     * @throws IOException
     *             if an error occurs
     */
    public static void createNewJSONSettingsFile(final String jsonFile) throws IOException {
        final Path jsonAbsoluteName = validateLocation(jsonFile); //FileSystems.getDefault().getPath(jsonFile).toAbsolutePath();
        final Path jsonFileName = jsonAbsoluteName.getFileName();
        final Path parent = jsonAbsoluteName.getParent();
        if (!Files.exists(jsonAbsoluteName)) {
            if (Files.exists(parent)) {
                try {
                    Files.createFile(jsonAbsoluteName,
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
                    //EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
                    //StandardOpenOption WRITE
                } catch (final IOException ex) {
                    log.error("caught IOException :", ex); //$NON-NLS-1$
                    ex.fillInStackTrace();
                    throw ex;
                }
            } else {
                final NotDirectoryException ex =
                    new NotDirectoryException("Invalid parent path specified for '" + jsonFileName + "' : " + parent);
                ex.fillInStackTrace();
                throw ex;
            }
        }
        //        final FileChannel wFC = FileChannel.open(jsonAbsoluteName,
        //            EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING),
        //            PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
        //
        //        final String emptyJSON = "{}";
        //        final ByteBuffer buffer = ByteBuffer.allocate(emptyJSON.getBytes().length);
        //        buffer.put(emptyJSON.getBytes());
        //        buffer.flip();
        //        wFC.write(buffer);
        //        wFC.force(true);
        //        wFC.close();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>create the path and all it's parents if it does not exist</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param newPath
     */
    public static void createPath(final String newPath) {
        final Path absolutePath = FileSystems.getDefault().getPath(newPath).toAbsolutePath();
        try {
            Files.createDirectories(absolutePath,
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
        } catch (final IOException ex) {
            final String msg = String.format("Error Creating path '%1$s'", newPath);
            log.error(msg, ex);
            final RuntimeException newEx = new RuntimeException(msg, ex);
            newEx.fillInStackTrace();
            throw newEx;
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>initialize the data directory with default files</DD>
     * <DT>Date:</DT>
     * <DD>Sep 17, 2017</DD>
     * </DL>
     */
    public static void initData() {
        createPath("data");
        copyFilesToData("src/main/resources/data");
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Make sure that the file path passed in is in the data folder of the project.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param location
     *            the path to check
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>return the absolute path of the passed path</DD>
     *         <DT><code>false</code></DT>
     *         <DD>prepend the current project "data" folder as the last path element of the file and return it as an
     *         absolute path</DD>
     *         </DL>
     */
    public static Path validateLocation(final Path location) {
        if (!Files.exists(FileSystems.getDefault().getPath("data"))) {
            initData();
        }
        if (location.getParent().getFileName().toString().equalsIgnoreCase("data")) {
            return location.toAbsolutePath();
        }
        final Path dataLocation = FileSystems.getDefault().getPath("data", location.getFileName().toString());
        return dataLocation.toAbsolutePath();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Make sure that the file name passed in is in the data folder of the project.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param fileName
     *            the file name to check
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>return the absolute path of the passed file name</DD>
     *         <DT><code>false</code></DT>
     *         <DD>prepend the "data" as the last path element to the file and return it as an absolute path</DD>
     *         </DL>
     */
    public static Path validateLocation(final String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            final IllegalArgumentException ex = new IllegalArgumentException("fileName Cannot be null or empty");
            ex.fillInStackTrace();
            throw ex;
        }
        final Path location = FileSystems.getDefault().getPath(fileName);
        return validateLocation(location);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>write to file</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param fileName
     * @param bytes
     */
    public static void writeFile(final String fileName, final byte[] bytes) {
        //        createDataPath();
        //        final Path p = FileSystems.getDefault().getPath(fileName);
        final Path absolutePath = validateLocation(fileName);//p.toAbsolutePath();
        if (!Files.exists(absolutePath)) {
            try {
                Files.createFile(absolutePath,
                    PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
            } catch (final IOException ex) {
                final String msg = String.format("Error creating file '{}'", absolutePath.toString());
                log.error(msg, ex);
                final RuntimeException newEx = new RuntimeException(msg, ex);
                newEx.fillInStackTrace();
                throw newEx;
            }
        }
        try (final FileChannel wFC =
            FileChannel.open(absolutePath, EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING),
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")))) {
            //EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
            //            final FileChannel wFC = FileChannel.open(absolutePath,
            //                EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING),
            //                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
            //            //final String emptyJSON = "{}";
            final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            wFC.write(buffer);
            wFC.force(true);
            wFC.close();
        } catch (final IOException ex) {
            final String msg = String.format("Error writing to file '{}'", absolutePath.toString());
            log.error(msg, ex);
            final RuntimeException newEx = new RuntimeException(msg, ex);
            newEx.fillInStackTrace();
            throw newEx;
        } finally {
        }

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Write the string array to file (add line serator to the end of each array element</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param fileName
     * @param data
     */
    public static void writeFile(final String fileName, final String[] data) {
        final StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (final String line : data) {
            sb.append(line);
            if (idx++ < 49) {
                sb.append(System.lineSeparator());
            }
        }
        writeFile(fileName, sb.toString().getBytes());
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Create a new JSON file for the passed name. if that file already exists, do nothing</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param json
     *            the serialized JSON content to save
     * @param jsonFile
     *            the file to create
     * @throws IOException
     *             if an error occurs
     */
    public static void writeSettingsToFile(final String json, final String jsonFile) throws IOException {
        final Path jsonAbsoluteName = validateLocation(jsonFile);//FileSystems.getDefault().getPath(jsonFile).toAbsolutePath();
        final Path jsonFileName = jsonAbsoluteName.getFileName();
        final Path parent = jsonAbsoluteName.getParent();
        if (!Files.exists(jsonAbsoluteName)) {
            if (Files.exists(parent)) {
                try {
                    Files.createFile(jsonAbsoluteName,
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
                    //EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
                    //StandardOpenOption WRITE
                } catch (final IOException ex) {
                    log.error("caught IOException :", ex); //$NON-NLS-1$
                    ex.fillInStackTrace();
                    throw ex;
                }
            } else {
                final NotDirectoryException ex =
                    new NotDirectoryException("Invalid parent path specified for '" + jsonFileName + "' : " + parent);
                ex.fillInStackTrace();
                throw ex;
            }
        }
        final FileChannel wFC = FileChannel.open(jsonAbsoluteName,
            EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING));
        //PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
        final byte[] jsonBytes = json.getBytes();
        final ByteBuffer buffer = ByteBuffer.allocate(jsonBytes.length);
        buffer.put(jsonBytes);
        buffer.flip();
        wFC.write(buffer);
        wFC.force(true);
        wFC.close();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Util Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     */
    private Util() {
    }

}
