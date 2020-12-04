package com.cytxcn.netty.util;

public class NetworkUtil {
    public static int reverseEndian(int value){
        int tmp;
        tmp = (value << 24) | ((value << 8) & 0xFF0000) | ((value >> 8) & 0xFF00) | ((value >> 24) & 0xFF);
        return tmp;
    }

    public static short reverseEndian(short value){
        short tmp;
        tmp = (short) (((value << 8) & 0xFF00) | ((value >> 8) & 0x00FF));
        return tmp;
    }
}
