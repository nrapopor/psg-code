/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardCopyOption.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add description</DD>
 * <DT>Date:</DT>
 * <DD>Sep 4, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasePDETestingAncestor {
    /**
     * A {@code FileVisitor} that copies a file-tree ("cp -r")
     */
    static class TreeCopier implements FileVisitor<Path> {
        private final Path source;

        private final Path target;

        private final boolean preserve;

        TreeCopier(final Path source, final Path target, final boolean preserve) {
            this.source = source;
            this.target = target;
            this.preserve = preserve;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) {
            // fix up modification time of directory when done
            if (exc == null && preserve) {
                final Path newdir = target.resolve(source.relativize(dir));
                try {
                    final FileTime time = Files.getLastModifiedTime(dir);
                    Files.setLastModifiedTime(newdir, time);
                } catch (final IOException x) {
                    System.err.format("Unable to copy all attributes to: %s: %s%n", newdir, x);
                }
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) {
            // before visiting entries in a directory we copy the directory
            // (okay if directory already exists).
            final CopyOption[] options = preserve ? new CopyOption[] { COPY_ATTRIBUTES } : new CopyOption[0];

            final Path newdir = target.resolve(source.relativize(dir));
            try {
                Files.copy(dir, newdir, options);
            } catch (final FileAlreadyExistsException x) {
                // ignore
            } catch (final IOException x) {
                System.err.format("Unable to create: %s: %s%n", newdir, x);
                return SKIP_SUBTREE;
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
            copyFile(file, target.resolve(source.relativize(file)), preserve);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(final Path file, final IOException exc) {
            if (exc instanceof FileSystemLoopException) {
                System.err.println("cycle detected: " + file);
            } else {
                System.err.format("Unable to copy: %s: %s%n", file, exc);
            }
            return CONTINUE;
        }
    }

    // @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BasePDETestingAncestor.class);

    private final static String testSettingsdataDir = "target/data";

    private final static String dataDir = "data";

    /**
     * Copy source file to target location. If {@code prompt} is true then prompt user to overwrite target if it exists.
     * The {@code preserve} parameter determines if file attributes should be copied/preserved.
     */
    static void copyFile(final Path source, final Path target, final boolean preserve) {
        final CopyOption[] options =
            preserve ? new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING } : new CopyOption[] { REPLACE_EXISTING };

        try {
            Files.copy(source, target, options);
        } catch (final IOException x) {
            System.err.format("Unable to copy: %s: %s%n", source, x);
        }
    }

    protected static void copyFiles(final String oldPath) {
        final Path absoluteSourcePath = FileSystems.getDefault().getPath(oldPath).toAbsolutePath();
        final Path absoluteTargetPath = FileSystems.getDefault().getPath(getDatadir()).toAbsolutePath();

        try {
            FileUtils.copyDirectory(absoluteSourcePath.toFile(), absoluteTargetPath.toFile(), true);
            //             Path res = java.nio.file.Files.copy(
            //                absoluteSourcePath,
            //                absoluteTargetPath,
            //                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
            //                java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
            //                java.nio.file.LinkOption.NOFOLLOW_LINKS);
        } catch (final IOException ex) {
            log.error("caught IOException :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add createEmptyJson description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param jSONFileName
     */
    protected static void createEmptyJson(final String jSONFileName) {
        final Path absolutePath = FileSystems.getDefault().getPath(jSONFileName).toAbsolutePath();
        final Path fileName = absolutePath.getFileName();
        final Path parent = absolutePath.getParent();
        if (!Files.exists(absolutePath)) {
            if (Files.exists(parent)) {
                try {
                    Files.createFile(absolutePath,
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
                    //EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
                    final FileChannel wFC = FileChannel.open(absolutePath,
                        EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING),
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
                    final String emptyJSON = "{}";
                    final ByteBuffer buffer = ByteBuffer.allocate(emptyJSON.getBytes().length);
                    buffer.put(emptyJSON.getBytes());
                    buffer.flip();
                    wFC.write(buffer);
                    wFC.force(true);
                    wFC.close();
                    //StandardOpenOption WRITE
                } catch (final IOException ex) {
                    log.error("caught IOException :", ex); //$NON-NLS-1$
                }
            } else {
                fail("Invalid Path specified for '" + fileName + "' : " + parent);
            }
        }
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
    protected static void createPath(final String newPath) {
        final Path absolutePath = FileSystems.getDefault().getPath(newPath).toAbsolutePath();
        try {
            Files.createDirectories(absolutePath,
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
        } catch (final IOException ex) {
            log.error("caught IOException :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>delete the file if it exists</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param jSONFileName
     */
    protected static void deleteJson(final String jSONFileName) {
        final Path absolutePath = FileSystems.getDefault().getPath(jSONFileName).toAbsolutePath();
        final Path fileName = absolutePath.getFileName();
        final Path parent = absolutePath.getParent();
        if (Files.exists(parent)) {
            try {
                if (Files.exists(absolutePath)) {
                    Files.delete(absolutePath);
                }
            } catch (final IOException ex) {
                log.error("caught IOException :", ex); //$NON-NLS-1$
            }
        } else {
            fail("Invalid Path specified for '" + fileName + "' : " + parent);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the datadir property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of datadir field
     */
    public static String getDatadir() {
        return dataDir;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the datadir property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of datadir field
     */
    protected static String getTestsettingsdatadir() {
        return testSettingsdataDir;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add setUpBeforeClass description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        createPath(getTestsettingsdatadir());
        createPath(getDatadir());
    }

    protected PSGProcessingCode psgParent;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>BasePDETestingAncestor Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    public BasePDETestingAncestor() {
        super();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the psgParent property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of psgParent field
     */
    public PSGProcessingCode getPsgParent() {
        if (psgParent == null) {
            psgParent = new PSGProcessingCode();
        }
        return psgParent;
    }

}
