package com.chars.im.server.banner;


import com.chars.im.server.ImConst;
import com.chars.im.server.JimVersion;

import java.io.PrintStream;

public class JimBanner implements Banner, ImConst {

    private static final String BANNER =
            "    _____      _____  ____    ____  \n" +
            "   |_   _|    |_   _||_   \\  /   _| \n" +
            "     | | ______ | |    |   \\/   |   \n" +
            " _   | ||______|| |    | |\\  /| |   \n" +
            "| |__' |       _| |_  _| |_\\/_| |_  \n" +
            "`.____.'      |_____||_____||_____| \n" +
            " ";

    private static final String JIM = " :: "+ImConst.JIM+" :: ";

    @Override
    public void printBanner(PrintStream printStream) {
        printStream.println(BANNER);
        String version  = " (" + JimVersion.version + ")";
        printStream.println(JIM+version+"\n");
    }

}
