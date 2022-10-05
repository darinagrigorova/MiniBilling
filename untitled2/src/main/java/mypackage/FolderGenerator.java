package mypackage;

import mypackage.model.User;

import java.io.File;

public class FolderGenerator {

    private final User user;
    private final String folderPath;

    public FolderGenerator(final User user, final String folderPath) {
        this.user = user;
        this.folderPath = folderPath;
    }

    public String folderGenerate() {
        final String name = user.getName();
        final String refNum = user.getRef();
        final String nameForFolder = folderPath + name + "-" + refNum;
        final File newFolder = new File(nameForFolder);
        final boolean bool2 = newFolder.mkdirs();
        return nameForFolder;
    }
}
