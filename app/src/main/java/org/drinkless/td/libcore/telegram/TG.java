/*
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * TD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TD.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2014-2015 Arseny Smirnov
 *           2014-2015 Aliaksei Levin
 */

package org.drinkless.td.libcore.telegram;

/**
 * This class is used for managing singleton-instance of class Client.
 */
public final class TG {
    private static volatile Client.ResultHandler updatesHandler;
    private static volatile Client instance;
    private static volatile String dir;
    private static volatile String filesDir;
    private static volatile boolean enableFileLog = false;
    private static volatile boolean useTestDc = false;

    /**
     * Sets handler which will be invoked for every incoming update from TDLib of type TdApi.Update.
     * Must be called before getClientInstance().
     *
     * @param updatesHandler Handler to be invoked on updates.
     */
    public static void setUpdatesHandler(Client.ResultHandler updatesHandler) {
        synchronized (TG.class) {
            TG.updatesHandler = updatesHandler;
            if (instance != null) {
                instance.setUpdatesHandler(TG.updatesHandler);
            }
        }
    }

    /**
     * Sets directory for storing persistent data of TDLib. Defaults to the current working directory.
     * Must be called before getClientInstance().
     *
     * @param dir Directory to store persistent data.
     */
    public static void setDir(String dir) {
        synchronized (TG.class) {
            TG.dir = dir;
        }
    }

    /**
     * Sets directory for storing files of TDLib. Defaults to directory set via {@link #setDir(String)}
     * Must be called before getClientInstance().
     *
     * @param dir Directory to store files.
     */
    public static void setFilesDir(String dir) {
        synchronized (TG.class) {
            TG.filesDir = dir;
        }
    }

    /**
     * Enables/disables logging to a file in addition to logging to Android Log.
     * By default logging to the file is disabled.
     * Must be called before getClientInstance().
     *
     * @param is_enabled Is file log should be enabled.
     */
    public static void setFileLogEnabled(boolean is_enabled) {
        synchronized (TG.class) {
            TG.enableFileLog = is_enabled;
        }
    }

    /**
     * Enables/disables using of test Telegram environment.
     * By default production environment is used.
     * Must be called before getClientInstance().
     *
     * @param use_test_dc Is test environment should be used.
     */
    public static void setUseTestDc(boolean use_test_dc) {
        synchronized (TG.class) {
            TG.useTestDc = use_test_dc;
        }
    }

    /**
     * This function stops and destroys the Client.
     * No queries are possible after this call, but completely new instance
     * of a Client with a different settings can be obtained through
     * getClientInstance()
     */
    public static void stopClient() {
        if (instance != null) {
            synchronized (TG.class) {
                if (instance != null) {
                    Client local = instance;
                    instance = null;
                    updatesHandler = null;
                    local.stop();
                }
            }
        }
    }

    /**
     * Changes TDLib log verbosity.
     *
     * @param newLogVerbosity New value of log verbosity. Must be positive.
     *                        Value 0 corresponds to android.util.Log.ASSERT,
     *                        value 1 corresponds to android.util.Log.ERROR,
     *                        value 2 corresponds to android.util.Log.WARNING,
     *                        value 3 corresponds to android.util.Log.INFO,
     *                        value 4 corresponds to android.util.Log.DEBUG,
     *                        value 5 corresponds to android.util.Log.VERBOSE,
     *                        value greater than 5 can be used to enable even more logging.
     *                        Default value of the log verbosity is 5.
     * @throws IllegalArgumentException if newLogVerbosity is negative.
     */
    public static void setLogVerbosity(int newLogVerbosity) {
        if (newLogVerbosity < 0) {
            throw new IllegalArgumentException();
        }
        synchronized (TG.class) {
            NativeClient.setLogVerbosity(newLogVerbosity);
        }
    }

    /**
     * This function returns a singleton object of the class Client which can be used for querying TDLib.
     * setUpdatesHandler(), setDir(), setFilesDir(), setFileLogEnabled() and setUseTestDc() must be called before this function.
     *
     * @return Client instance
     */
    public static Client getClientInstance() {
        if (instance == null) {
            synchronized (TG.class) {
                if (instance == null) {
                    if (dir == null) {
                        return null;
                    }
                    if (filesDir == null) {
                        filesDir = dir;
                    }
                    Client local = Client.create(updatesHandler, dir, filesDir, enableFileLog, useTestDc);
                    new Thread(local).start();
                    instance = local;
                }
            }
        }
        return instance;
    }
}
