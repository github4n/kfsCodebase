package com.newcore.bmp.authority.util;

import java.util.List;

public class DesUtils {

    private DesUtils() {

    }

    private static final String FIRST_KEY = "1";
    private static final String SECOND_KEY = "A";
    private static final String THIRD_KEY = "E";

    private static boolean strIsNotEmpty(String str) {
        return null != str && !"".equals(str);
    }

    /**
     * DES加密/解密
     * 
     * @author chenyong
     */
    public static String strEnc(String data, String[] keys) {

        String firstKey = FIRST_KEY;
        String secondKey = SECOND_KEY;
        String thirdKey = THIRD_KEY;

        if (keys != null && keys.length == 3) {
            firstKey = keys[0];
            secondKey = keys[1];
            thirdKey = keys[2];
        }

        int leng = data.length();
        String encData = "";

        if (leng > 0) {
            if (leng < 4) {
                int[] bt = DesChildUtils.strToBt(data);
                encData = DesChildUtils.bt64ToHex(getEncByte(bt, firstKey, secondKey, thirdKey));
            } else {
                int iterator = leng / 4;
                int remainder = leng % 4;
                int i;
                for (i = 0; i < iterator; i++) {
                    String tempData = data.substring(i * 4 + 0, i * 4 + 4);
                    int[] tempByte = DesChildUtils.strToBt(tempData);
                    encData += DesChildUtils.bt64ToHex(getEncByte(tempByte, firstKey, secondKey, thirdKey));
                }
                if (remainder > 0) {
                    String remainderData = data.substring(iterator * 4 + 0, leng);
                    int[] tempByte = DesChildUtils.strToBt(remainderData);
                    encData += DesChildUtils.bt64ToHex(getEncByte(tempByte, firstKey, secondKey, thirdKey));
                }
            }
        }
        return encData;
    }

    private static int[] getEncByte(int[] tempByte, String firstKey, String secondKey, String thirdKey) {

        List<int[]> firstKeyBt = null, secondKeyBt = null, thirdKeyBt = null;
        int firstLength = 0, secondLength = 0, thirdLength = 0;
        if (strIsNotEmpty(firstKey)) {
            firstKeyBt = DesChildUtils.getKeyBytes(firstKey);
            firstLength = firstKeyBt.size();
        }
        if (strIsNotEmpty(secondKey)) {
            secondKeyBt = DesChildUtils.getKeyBytes(secondKey);
            secondLength = secondKeyBt.size();
        }
        if (strIsNotEmpty(thirdKey)) {
            thirdKeyBt = DesChildUtils.getKeyBytes(thirdKey);
            thirdLength = thirdKeyBt.size();
        }
        if (strIsNotEmpty(secondKey) && strIsNotEmpty(firstKey) && strIsNotEmpty(thirdKey)) {
            return getEncByteIf(tempByte, firstLength, firstKeyBt, secondLength, secondKeyBt, thirdLength, thirdKeyBt);
        } else {
            return getEncByteElse(tempByte, firstKey, firstLength, firstKeyBt, secondKey, secondLength, secondKeyBt);
        }
    }

    private static int[] getEncByteIf(int[] tempByte, int firstLength, List<int[]> firstKeyBt, int secondLength,
            List<int[]> secondKeyBt, int thirdLength, List<int[]> thirdKeyBt) {
        int[] tempBt = tempByte;
        for (int x = 0; x < firstLength; x++) {
            tempBt = DesChildUtils.enc(tempBt, (int[]) firstKeyBt.get(x));
        }
        for (int y = 0; y < secondLength; y++) {
            tempBt = DesChildUtils.enc(tempBt, (int[]) secondKeyBt.get(y));
        }
        for (int z = 0; z < thirdLength; z++) {
            tempBt = DesChildUtils.enc(tempBt, (int[]) thirdKeyBt.get(z));
        }
        return tempBt;
    }

    private static int[] getEncByteElse(int[] tempByte, String firstKey, int firstLength, List<int[]> firstKeyBt,
            String secondKey, int secondLength, List<int[]> secondKeyBt) {
        int[] tempBt = tempByte;
        if (strIsNotEmpty(secondKey) && strIsNotEmpty(firstKey)) {

            for (int x = 0; x < firstLength; x++) {
                tempBt = DesChildUtils.enc(tempBt, (int[]) firstKeyBt.get(x));
            }
            for (int y = 0; y < secondLength; y++) {
                tempBt = DesChildUtils.enc(tempBt, (int[]) secondKeyBt.get(y));
            }
        } else {
            if (strIsNotEmpty(firstKey)) {
                for (int x = 0; x < firstLength; x++) {
                    tempBt = DesChildUtils.enc(tempBt, (int[]) firstKeyBt.get(x));
                }
            }
        }

        return tempBt;
    }

    /**
     * 
     * @param data
     * @param keys
     * @return
     */
    public static String strDec(String data, String[] keys) {

        String firstKey = FIRST_KEY;
        String secondKey = SECOND_KEY;
        String thirdKey = THIRD_KEY;

        if (keys != null && keys.length == 3) {
            firstKey = keys[0];
            secondKey = keys[1];
            thirdKey = keys[2];
        }
        int leng = data.length();
        String decStr = "";
        List<int[]> firstKeyBt = null, secondKeyBt = null, thirdKeyBt = null;
        int firstLength = 0, secondLength = 0, thirdLength = 0;
        if (strIsNotEmpty(firstKey)) {
            firstKeyBt = DesChildUtils.getKeyBytes(firstKey);
            firstLength = firstKeyBt.size();
        }
        if (strIsNotEmpty(secondKey)) {
            secondKeyBt = DesChildUtils.getKeyBytes(secondKey);
            secondLength = secondKeyBt.size();
        }
        if (strIsNotEmpty(thirdKey)) {
            thirdKeyBt = DesChildUtils.getKeyBytes(thirdKey);
            thirdLength = thirdKeyBt.size();
        }

        int iterator = leng / 16;
        int i = 0;
        for (; i < iterator; i++) {
            String tempData = data.substring(i * 16 + 0, i * 16 + 16);
            String strByte = DesChildUtils.hexToBt64(tempData);
            int[] intByte = new int[64];
            for (int j = 0; j < 64; j++) {
                intByte[j] = Integer.parseInt(strByte.substring(j, j + 1));
            }
            if (strIsNotEmpty(secondKey) && strIsNotEmpty(firstKey) && strIsNotEmpty(thirdKey)) {
                int[] tempBt = intByte;
                for (int x = thirdLength - 1; x >= 0; x--) {
                    tempBt = DesChildUtils.dec(tempBt, (int[]) thirdKeyBt.get(x));
                }
                for (int y = secondLength - 1; y >= 0; y--) {
                    tempBt = DesChildUtils.dec(tempBt, (int[]) secondKeyBt.get(y));
                }
                for (int z = firstLength - 1; z >= 0; z--) {
                    tempBt = DesChildUtils.dec(tempBt, (int[]) firstKeyBt.get(z));
                }
                decStr += DesChildUtils.byteToString(tempBt);
            } else {
                decStr += DesChildUtils.byteToString(getDecByteElse(intByte, firstKey, firstLength, firstKeyBt,
                        secondKey, secondLength, secondKeyBt));
            }
        }
        return decStr;
    }

    private static int[] getDecByteElse(int[] intByte, String firstKey, int firstLength, List<int[]> firstKeyBt,
            String secondKey, int secondLength, List<int[]> secondKeyBt) {
        int[] tempBt = intByte;
        if (strIsNotEmpty(secondKey) && strIsNotEmpty(firstKey)) {
            for (int x = secondLength - 1; x >= 0; x--) {
                tempBt = DesChildUtils.dec(tempBt, (int[]) secondKeyBt.get(x));
            }
            for (int y = firstLength - 1; y >= 0; y--) {
                tempBt = DesChildUtils.dec(tempBt, (int[]) firstKeyBt.get(y));
            }
        } else {
            if (strIsNotEmpty(firstKey)) {
                for (int x = firstLength - 1; x >= 0; x--) {
                    tempBt = DesChildUtils.dec(tempBt, (int[]) firstKeyBt.get(x));
                }
            }
        }

        return tempBt;
    }

}
