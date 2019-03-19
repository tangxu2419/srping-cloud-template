package com.demo.test;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/613:56
 */
public class EventTestDemo {

    public static void main(String[] args) {
        EventSource source = new EventSource();
        MyEventListener listener = event -> {
            EventSource eventSource = (EventSource) event.getSource();
            if (eventSource.isFlag()) {
                System.out.println("关闭");
                eventSource.setFlag(false);
            } else {
                System.out.println("开启");
                eventSource.setFlag(true);
            }
        };
        source.addListener(listener);
        source.actionPerformed();
        source.actionPerformed();
        source.removeListener(listener);
        source.actionPerformed();
    }

}

/**
 * 事件源
 */
class EventSource {

    private final AtomicBoolean flag = new AtomicBoolean(false);

    private final List<MyEventListener> listeners = new ArrayList<>();

    void addListener(MyEventListener listener) {
        listeners.add(listener);
    }

    void removeListener(MyEventListener listener) {
        if (!listeners.isEmpty()) {
            listeners.remove(listener);
        }
    }

    void actionPerformed() {
        if (listeners.isEmpty()) {
            System.out.println("当前事件源未绑定监听事件");
        } else {
            MyEventObject myEventObject = new MyEventObject(this);
            for (MyEventListener listener : listeners) {
                listener.handlerEvent(myEventObject);
            }
        }
    }

    boolean isFlag() {
        return flag.get();
    }

    void setFlag(boolean flag) {
        this.flag.set(flag);
    }
}

/**
 * 事件对象
 */
class MyEventObject extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    MyEventObject(EventSource source) {
        super(source);
    }
}

/**
 * 事件监听器
 */
interface MyEventListener extends EventListener {

    /**
     * 事件触发执行函数
     *
     * @param event 事件对象
     */
    void handlerEvent(MyEventObject event);

}
