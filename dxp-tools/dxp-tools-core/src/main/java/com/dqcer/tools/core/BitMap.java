package com.dqcer.tools.core;

/**
 * @author dongqin
 * @description bit Map 查找数据是否存在
 * @date 2021/12/03 23:12:40
 */
public class BitMap {

    private long[] bits;

    public BitMap(int max) {
        bits = new long[(max + 64) >> 6];
    }

    public void add(int num) {
        bits[num >> 6] |= (1L << (num & 63));
    }

    public void delete(int num) {
        bits[num >> 6] &= ~(1L << (num & 63));
    }

    public boolean contains(int num) {
        return (bits[num >> 6] & (1L << (num & 63))) != 0;
    }

    public static void main(String[] args) {
        BitMap bitMap = new BitMap(2000000000);
        bitMap.add(1);
        boolean contains = bitMap.contains(1);
        System.out.println(contains);

    }
}
