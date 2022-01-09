package org.sadness.message;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/9 20:56
 */
public class ThreadPoolHelper {

    public static final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 7, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

}
