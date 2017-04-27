package com.xologood.q8pad.listener;

/**
 * Created by Administrator on 17-3-10.
 */

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
