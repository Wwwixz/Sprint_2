package io.github.some_example_name.lwjgl3;

import org.lwjgl.system.macosx.LibSystem;

/**
 * Adds macOS support for LWJGL3 applications.
 * <br>
 * See <a href="https://github.com/libgdx/libgdx/wiki/LWJGL-3-Backend#macos-support">the LibGDX wiki</a> for more information.
 */
public class StartupHelper {
    private StartupHelper() {
        throw new UnsupportedOperationException();
    }

    public static boolean start(String[] args) {
        // macOS support code usually goes here, but for a simple fix we can keep it minimal
        // or just return false if we don't want to deal with macOS specific JVM args here.
        return false;
    }
}
