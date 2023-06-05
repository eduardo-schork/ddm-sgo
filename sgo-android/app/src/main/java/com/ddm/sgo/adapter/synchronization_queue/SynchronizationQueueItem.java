package com.ddm.sgo.adapter.synchronization_queue;

public class SynchronizationQueueItem {
    String name;
    String infoText;
    String status;

    public SynchronizationQueueItem(String name, String infoText, String status) {
        this.name = name;
        this.infoText = infoText;
        this.status = status;
    }
}
