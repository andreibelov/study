package com.example.chat.listener;

import java.io.IOException;
import java.util.EventListener;

/**
 * Created by john on 7/24/2016.
 * @author andrei.belov aka john
 * based on @link https://www.youtube.com/watch?v=HcBDdm3k9Oo
 */
public interface ReadListener extends EventListener {

    public void onDataAvailable() throws IOException;
    public void onAllDataRead() throws IOException;
    public void onError(Throwable t);

}
