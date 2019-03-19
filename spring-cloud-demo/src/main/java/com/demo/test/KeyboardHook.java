package com.demo.test;


import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/617:40
 */
@Slf4j
public class KeyboardHook implements Runnable {

    private static WinUser.HHOOK hhk;
    private final static User32 LIB = User32.INSTANCE;
    private boolean[] onOff;

    KeyboardHook(boolean[] onOff) {
        this.onOff = onOff;
    }

    @Override
    public void run() {
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        WinUser.LowLevelKeyboardProc keyboardHook = (nCode, wParam, info) -> {
            log.info("键入：{}", Keyboard.getExtendedKeyCodeForChar(info.vkCode));
            if (!onOff[0]) {
                System.exit(0);
            }
            return LIB.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
        };
        hhk = LIB.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = LIB.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            } else {
                System.err.println("got message");
                LIB.TranslateMessage(msg);
                LIB.DispatchMessage(msg);
            }
        }
        LIB.UnhookWindowsHookEx(hhk);
    }
}

class Monitor {

    private Monitor() {
        boolean[] onOff = {true};
        new Thread(new KeyboardHook(onOff)).start();
//        new Thread(new MouseHook(on_off)).start();

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            ActionListener exitListener = e -> {
                System.out.println("Exiting...");
                System.exit(0);
            };
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);
        }
    }

    public static void main(String[] args) {
        new Monitor();
    }
}
//
//@Slf4j
//public class Win32ManMachineInterface extends ManMachineInterface {
//    public User32 LIB;
//    private WinDef.HMODULE hMod;
//    private MouseHookListener mouseHook;
//    private WinUser.HHOOK mousehhk;
//    private WinUser.HHOOK keyboardhhk;
//
//
//    public Win32ManMachineInterface(ManMachineInterfaceHook manMachineInterfaceHook) {
//        super(manMachineInterfaceHook);
//        LIB = User32.INSTANCE;
//        hMod = Kernel32.INSTANCE.GetModuleHandle(null);
//    }
//
//    private long timeStamp;
//    private int wheelAmt;
//
//    @Override
//    public void start() {
//        this.mouseHook = new MouseHookListener() {
//            //回调监听
//            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, MouseHookStruct lParam) {
//                if (nCode >= 0) {
//                    long timeInterval = 0;
//                    long l = System.currentTimeMillis();
//                    if (timeStamp != 0) {
//                        timeInterval = l - timeStamp;
//                    } else {
//                        timeInterval = 0;
//                    }
//                    timeStamp = l;
////                    log.info(wParam.intValue());
//                    switch (wParam.intValue()) {
//                        case MouseHook.WM_MOUSEMOVE:
//                            manMachineInterfaceHook.mouseHook(ManMachineInterfaceHook.ACTION_MOUSEMOVE, lParam.pt.x, lParam.pt.y, timeInterval);
//                            break;
//                        case MouseHook.WM_LBUTTONDOWN:
//                            manMachineInterfaceHook.mouseHook(ManMachineInterfaceHook.ACTION_LBUTTONDOWN, lParam.pt.x, lParam.pt.y, timeInterval);
//                            break;
//                        case MouseHook.WM_LBUTTONUP:
//                            manMachineInterfaceHook.mouseHook(ManMachineInterfaceHook.ACTION_LBUTTONUP, lParam.pt.x, lParam.pt.y, timeInterval);
//                            break;
//                        case MouseHook.WM_RBUTTONDOWN:
//                            manMachineInterfaceHook.mouseHook(ManMachineInterfaceHook.ACTION_RBUTTONDOWN, lParam.pt.x, lParam.pt.y, timeInterval);
//                            break;
//                        case MouseHook.WM_RBUTTONUP:
//                            manMachineInterfaceHook.mouseHook(ManMachineInterfaceHook.ACTION_RBUTTONUP, lParam.pt.x, lParam.pt.y, timeInterval);
//                            break;
//                        case MouseHook.WM_MBUTTONDOWN:
//                            manMachineInterfaceHook.mouseHook(ManMachineInterfaceHook.ACTION_MBUTTONDOWN, lParam.pt.x, lParam.pt.y, timeInterval);
//                            break;
//                        case MouseHook.WM_MBUTTONUP:
//                            manMachineInterfaceHook.mouseHook(ManMachineInterfaceHook.ACTION_MBUTTONUP, lParam.pt.x, lParam.pt.y, timeInterval);
//                            break;
//                        case MouseHook.WM_WHEEL:
//                            int p = lParam.dwExtraInfo.intValue();
//                            int amt;
//                            if (wheelAmt == 0) {
//                                amt = 0;
//                            } else {
//                                amt = p - wheelAmt;
//                            }
//                            wheelAmt = p;
//                            log.info("intValue:" + lParam.dwExtraInfo.intValue()
//                                    + " longValue:" + lParam.dwExtraInfo.longValue()
//                                    + " byteValue:" + lParam.dwExtraInfo.byteValue()
//                                    + " floatValue:" + lParam.dwExtraInfo.floatValue()
//                                    + " doubleValue:" + lParam.dwExtraInfo.doubleValue()
//                                    + " shortValue:" + lParam.dwExtraInfo.shortValue());
//                            manMachineInterfaceHook.mouseWheelHook(ManMachineInterfaceHook.ACTION_WHEEL, amt, timeInterval);
//                            break;
//                    }
//                }
//                return LIB.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM());
//            }
//        };
//        this.mouseHook.LIB = this.LIB;
//        mousehhk = this.LIB.SetWindowsHookEx(WinUser.WH_MOUSE_LL, this.mouseHook, hMod, 0);
//
//
//        WinUser.LowLevelKeyboardProc keyboardHook = new WinUser.LowLevelKeyboardProc() {
//            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT info) {
//                long timeInterval = 0;
//                long l = System.currentTimeMillis();
//                if (timeStamp != 0) {
//                    timeInterval = l - timeStamp;
//                } else {
//                    timeInterval = 0;
//                }
//                timeStamp = l;
//                manMachineInterfaceHook.keyboardHook(info.vkCode, info.scanCode, info.flags, info.time, timeInterval);
//                return LIB.CallNextHookEx(keyboardhhk, nCode, wParam, new WinDef.LPARAM());
//            }
//        };
//        keyboardhhk = this.LIB.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
//
//        int result;
//        WinUser.MSG msg = new WinUser.MSG();
//        while ((result = LIB.GetMessage(msg, null, 0, 0)) != 0) {
//            if (result == -1) {
//                System.err.println("error in get message");
//                break;
//            } else {
//                System.err.println("got message");
//                LIB.TranslateMessage(msg);
//                LIB.DispatchMessage(msg);
//            }
//        }
//    }
//
//    @Override
//    public void stop() {
//        LIB.UnhookWindowsHookEx(mousehhk);
//        LIB.UnhookWindowsHookEx(keyboardhhk);
//    }
//
//    @Override
//    public boolean showWindow(String windowName) {
//        // 第一个参数是Windows窗体的窗体类，第二个参数是窗体的标题。不熟悉windows编程的需要先找一些Windows窗体数据结构的知识来看看，还有windows消息循环处理，其他的东西不用看太多。
//        WinDef.HWND hwnd = User32.INSTANCE.FindWindow (null, windowName);
//        if (hwnd == null) {
//            log.info(windowName + " is not running");
//            return false;
//        } else {
//            int SW_MAXIMIZE = 0x03;
//            User32.INSTANCE.ShowWindow(hwnd, SW_MAXIMIZE);
//            User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
//            return true;
//        }
//    }
//
//    @Override
//    public boolean hideWindow(String windowName){
//        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
//        if (hwnd == null) {
//            log.info(windowName + " is not running");
//            return false;
//        } else {
//            int SW_MINIMIZE = 0x06;
//            User32.INSTANCE.ShowWindow(hwnd, SW_MINIMIZE);
//            User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
//            return true;
//        }
//    }
//}

abstract class ManMachineInterface {
    protected ManMachineInterfaceHook manMachineInterfaceHook;

    protected ManMachineInterface(ManMachineInterfaceHook manMachineInterfaceHook) {
        this.manMachineInterfaceHook = manMachineInterfaceHook;
    }

    public abstract void start();

    public abstract void stop();

    public abstract boolean showWindow(String windowName);

    public abstract boolean hideWindow(String windowName);

}

abstract class ManMachineInterfaceHook {
    public static final int ACTION_MOUSEMOVE = 1;// 512;
    public static final int ACTION_LBUTTONDOWN = 2;// 513;
    public static final int ACTION_LBUTTONUP = 3;//514;
    public static final int ACTION_RBUTTONDOWN = 4;//516;
    public static final int ACTION_RBUTTONUP = 5;//517;
    public static final int ACTION_MBUTTONDOWN = 6;//519;
    public static final int ACTION_MBUTTONUP = 7;//520;
    public static final int ACTION_WHEEL = 8;//522;

    public abstract void mouseHook(int action, int x, int y, long timeInterval);

    public abstract void mouseWheelHook(int action, int p, long timeInterval);

    public abstract void keyboardHook(int vkCode, int scanCode, int flags, int time, long timeInterval);


}

/**
 * JNA出来的win平台的键盘vkCode与java JDK的vkcode是不一致
 * 转换映射
 */
class Keyboard {
    public static String getExtendedKeyCodeForChar(int vkCode) {
        return KeyEvent.getKeyText(vkCode);
    }

    public static int conversion(int vkCode) {
        if (vkCode == 27) {//Esc 27 VK_ESCAPE 27
            return KeyEvent.VK_ESCAPE;
        } else if (vkCode >= 112 && vkCode <= 123) { //F1-F12
            return vkCode;
        } else if (vkCode >= 48 && vkCode <= 57) { //0-9
            return vkCode;
        } else if (vkCode >= 65 && vkCode <= 90) { //A-Z
            return vkCode;
        } else if (vkCode == 144) {//NumLock 144 VK_NUM_LOCK 144
            return KeyEvent.VK_NUM_LOCK;
        } else if (vkCode >= 96 && vkCode <= 110) { //数字小键盘
            return vkCode;
        } else if (vkCode == 13) {//数字小键盘Enter 13 VK_ENTER 10
            return KeyEvent.VK_ENTER;
        } else if (vkCode == 45) {//Insert 45 VK_INSERT 155
            return KeyEvent.VK_INSERT;
        } else if (vkCode == 46) {//Delete 46 VK_DELETE 127
            return KeyEvent.VK_DELETE;
        } else if (vkCode == 33) {//PageUp 33 VK_PAGE_UP 33
            return KeyEvent.VK_PAGE_UP;
        } else if (vkCode == 34) {//PageDown 34 VK_PAGE_DOWN 34
            return KeyEvent.VK_PAGE_DOWN;
        } else if (vkCode == 35) {//End 35 VK_END 35
            return KeyEvent.VK_END;
        } else if (vkCode == 36) {//Home 36 VK_HOME 36
            return KeyEvent.VK_HOME;
        } else if (vkCode >= 37 && vkCode <= 40) { //方向键
            return vkCode;
        } else if (vkCode == 9) {//Tab 9 VK_TAB 9
            return KeyEvent.VK_TAB;
        } else if (vkCode == 20) {//Caps Lock 20 VK_CAPS_LOCK 20
            return KeyEvent.VK_CAPS_LOCK;
        } else if (vkCode == 160) {//Shift(Left) 160 VK_SHIFT 16
            return KeyEvent.VK_SHIFT;
        } else if (vkCode == 161) {//Shift(Right) 161 VK_SHIFT 16
            return KeyEvent.VK_SHIFT;
        } else if (vkCode == 162) {//Ctrl(Left) 162 VK_CONTROL 17
            return KeyEvent.VK_CONTROL;
        } else if (vkCode == 163) {//Ctrl(Right) 163 VK_CONTROL 17
            return KeyEvent.VK_CONTROL;
        } else if (vkCode == 91) {//Win(Left) 91 VK_WINDOWS 524
            return KeyEvent.VK_WINDOWS;
        } else if (vkCode == 92) {//Win(Right) 92 VK_WINDOWS 524
            return KeyEvent.VK_WINDOWS;
        } else if (vkCode == 164) {//Alt(Left) 164 VK_ALT 18
            return KeyEvent.VK_ALT;
        } else if (vkCode == 165) {//Alt(Right) 165 VK_ALT 18
            return KeyEvent.VK_ALT;
        } else if (vkCode == 189) {//- 189 VK_MINUS 45
            return KeyEvent.VK_MINUS;
        } else if (vkCode == 187) {//= 187 VK_EQUALS 61
            return KeyEvent.VK_EQUALS;
        } else if (vkCode == 8) {//Backspace 8 VK_BACK_SPACE 8
            return KeyEvent.VK_BACK_SPACE;
        } else if (vkCode == 219) {//[ 219 VK_OPEN_BRACKET 91
            return KeyEvent.VK_OPEN_BRACKET;
        } else if (vkCode == 221) {//] 221 VK_CLOSE_BRACKET 93
            return KeyEvent.VK_CLOSE_BRACKET;
        } else if (vkCode == 220) {//\ 220 VK_BACK_SLASH 92
            return KeyEvent.VK_BACK_SLASH;
        } else if (vkCode == 186) {//; 186 VK_SEMICOLON 59
            return KeyEvent.VK_SEMICOLON;
        } else if (vkCode == 222) {//‘ 222 VK_QUOTE 222
            return KeyEvent.VK_QUOTE;
        } else if (vkCode == 188) {//, 188 VK_COMMA 44
            return KeyEvent.VK_COMMA;
        } else if (vkCode == 190) {//. 190 VK_PERIOD 46
            return KeyEvent.VK_PERIOD;
        } else if (vkCode == 191) {/// 191 VK_SLASH 47
            return KeyEvent.VK_SLASH;
        } else if (vkCode == 13) {//Enter 13 VK_ENTER 10
            return KeyEvent.VK_ENTER;
        }
        return vkCode;
    }

    public static int[] toVK(char character) {
        switch (character) {
            case 'a':
                return doType(KeyEvent.VK_A);
            case 'b':
                return doType(KeyEvent.VK_B);
            case 'c':
                return doType(KeyEvent.VK_C);
            case 'd':
                return doType(KeyEvent.VK_D);
            case 'e':
                return doType(KeyEvent.VK_E);
            case 'f':
                return doType(KeyEvent.VK_F);
            case 'g':
                return doType(KeyEvent.VK_G);
            case 'h':
                return doType(KeyEvent.VK_H);
            case 'i':
                return doType(KeyEvent.VK_I);
            case 'j':
                return doType(KeyEvent.VK_J);
            case 'k':
                return doType(KeyEvent.VK_K);
            case 'l':
                return doType(KeyEvent.VK_L);
            case 'm':
                return doType(KeyEvent.VK_M);
            case 'n':
                return doType(KeyEvent.VK_N);
            case 'o':
                return doType(KeyEvent.VK_O);
            case 'p':
                return doType(KeyEvent.VK_P);
            case 'q':
                return doType(KeyEvent.VK_Q);
            case 'r':
                return doType(KeyEvent.VK_R);
            case 's':
                return doType(KeyEvent.VK_S);
            case 't':
                return doType(KeyEvent.VK_T);
            case 'u':
                return doType(KeyEvent.VK_U);
            case 'v':
                return doType(KeyEvent.VK_V);
            case 'w':
                return doType(KeyEvent.VK_W);
            case 'x':
                return doType(KeyEvent.VK_X);
            case 'y':
                return doType(KeyEvent.VK_Y);
            case 'z':
                return doType(KeyEvent.VK_Z);
            case 'A':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_A);
            case 'B':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_B);
            case 'C':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_C);
            case 'D':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_D);
            case 'E':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_E);
            case 'F':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_F);
            case 'G':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_G);
            case 'H':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_H);
            case 'I':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_I);
            case 'J':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_J);
            case 'K':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_K);
            case 'L':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_L);
            case 'M':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_M);
            case 'N':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_N);
            case 'O':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_O);
            case 'P':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_P);
            case 'Q':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Q);
            case 'R':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_R);
            case 'S':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_S);
            case 'T':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_T);
            case 'U':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_U);
            case 'V':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_V);
            case 'W':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_W);
            case 'X':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_X);
            case 'Y':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Y);
            case 'Z':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Z);
            case '`':
                return doType(KeyEvent.VK_BACK_QUOTE);
            case '0':
                return doType(KeyEvent.VK_0);
            case '1':
                return doType(KeyEvent.VK_1);
            case '2':
                return doType(KeyEvent.VK_2);
            case '3':
                return doType(KeyEvent.VK_3);
            case '4':
                return doType(KeyEvent.VK_4);
            case '5':
                return doType(KeyEvent.VK_5);
            case '6':
                return doType(KeyEvent.VK_6);
            case '7':
                return doType(KeyEvent.VK_7);
            case '8':
                return doType(KeyEvent.VK_8);
            case '9':
                return doType(KeyEvent.VK_9);
            case '-':
                return doType(KeyEvent.VK_MINUS);
            case '=':
                return doType(KeyEvent.VK_EQUALS);
            case '~':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE);
            case '!':
                return doType(KeyEvent.VK_EXCLAMATION_MARK);
            case '@':
                return doType(KeyEvent.VK_AT);
            case '#':
                return doType(KeyEvent.VK_NUMBER_SIGN);
            case '$':
                return doType(KeyEvent.VK_DOLLAR);
            case '%':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_5);
            case '^':
                return doType(KeyEvent.VK_CIRCUMFLEX);
            case '&':
                return doType(KeyEvent.VK_AMPERSAND);
            case '*':
                return doType(KeyEvent.VK_ASTERISK);
            case '(':
                return doType(KeyEvent.VK_LEFT_PARENTHESIS);
            case ')':
                return doType(KeyEvent.VK_RIGHT_PARENTHESIS);
            case '_':
                return doType(KeyEvent.VK_UNDERSCORE);
            case '+':
                return doType(KeyEvent.VK_PLUS);
            case '\t':
                return doType(KeyEvent.VK_TAB);
            case '\n':
                return doType(KeyEvent.VK_ENTER);
            case '[':
                return doType(KeyEvent.VK_OPEN_BRACKET);
            case ']':
                return doType(KeyEvent.VK_CLOSE_BRACKET);
            case '\\':
                return doType(KeyEvent.VK_BACK_SLASH);
            case '{':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET);
            case '}':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET);
            case '|':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH);
            case ';':
                return doType(KeyEvent.VK_SEMICOLON);
            case ':':
                return doType(KeyEvent.VK_COLON);
            case '\'':
                return doType(KeyEvent.VK_QUOTE);
            case '"':
                return doType(KeyEvent.VK_QUOTEDBL);
            case ',':
                return doType(KeyEvent.VK_COMMA);
            case '<':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_COMMA);
            case '.':
                return doType(KeyEvent.VK_PERIOD);
            case '>':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_PERIOD);
                break;
            case '/':
                return doType(KeyEvent.VK_SLASH);
            case '?':
                return doType(KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH);
            case ' ':
                return doType(KeyEvent.VK_SPACE);
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
        return null;
    }

    private static int[] doType(int... keyCodes) {
        return keyCodes;
    }
}
