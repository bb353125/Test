package com.keeko.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DpcCalculatorUtils {

    /**
     * 1: 双色球奖金计算器-复式
     *
     * @param r 投注个数-红球
     * @param l 投注个数-蓝球
     * @param i 命中个数-红球
     * @param a 命中个数-篮球
     */
    public static int[] calcSsqFs(int r, int l, int i, int a) {
        int[] o = new int[6];
        if (i <= 0 && a <= 0 || i <= 3 && a <= 0) {
            return o;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
                o[5] += v(r, 6);
                break;
            case 3:
                o[5] += v(r - i, 4) * v(i, 2);
                o[5] += v(r - i, 5) * v(i, 1);
                o[5] += v(r - i, 6);
                o[4] += v(r - i, 3);
                break;
            case 4:
                if (a > 0) {
                    o[5] += v(r - i, 6);
                    o[5] += v(r - i, 5) * v(i, 1);
                    o[5] += v(r - i, 4) * v(i, 2);
                    o[4] += v(r - i, 3) * v(i, 3);
                    o[4] += v(r - i, 2) * v(i, 4) * (l - 1);
                    o[3] += v(r - i, 2);
                } else {
                    o[4] += v(r - i, 2) * l;
                }
                break;
            case 5:
                if (a > 0) {
                    o[5] += v(r - i, 6);
                    o[5] += v(r - i, 5) * v(i, 1);
                    o[5] += v(r - i, 4) * v(i, 2);
                    o[4] += v(r - i, 3) * v(i, 3);
                    o[4] += v(r - i, 2) * v(i, 4) * (l - 1);
                    o[3] += v(r - i, 2) * v(i, 4);
                    o[3] += v(r - i, 1) * v(i, 5) * (l - 1);
                    o[2] += v(r - i, 1);
                } else {
                    o[4] += v(r - i, 2) * v(i, 4) * l;
                    o[3] += v(r - i, 1) * v(i, 5) * l;
                }
                break;
            case 6:
                if (a > 0) {
                    o[5] += v(r - i, 6);
                    o[5] += v(r - i, 5) * v(i, 1);
                    o[5] += v(r - i, 4) * v(i, 2);
                    o[4] += v(r - i, 3) * v(i, 3);
                    o[4] += v(r - i, 2) * v(i, 4) * (l - 1);
                    o[3] += v(r - i, 2) * v(i, 4);
                    o[3] += v(r - i, 1) * v(i, 5) * (l - 1);
                    o[2] += v(r - i, 1) * v(i, 5);
                    o[1] += l - 1;
                    o[0]++;

                } else {
                    o[4] += v(r - i, 2) * v(i, 4) * l;
                    o[3] += v(r - i, 1) * v(i, 5) * l;
                    o[1] += l;
                }
        }
        return o;
        //System.out.println(JSONObject.toJSONString(o));
    }

    public static int v(int t, int e) {
        if (e > t / 2) {
            e = t - e;
        }
        if (e > t || 0 > e)
            return 0;
        if (t >= 0 && 0 == e)
            return 1;
        int n = 1, r = t;
        for (int l = 1; e >= l; l++) {
            n *= l;
            if (e > l) {
                r *= t - l;
            }
        }
        return r / n;
    }

    /**
     * 双色球奖金计算器- 计算多少注
     *
     * @param n 投注个数-红球
     */
    public static int getSsqNum(int n, int blue) {
        int num1 = 0;
        if (n > 10) {
            num1 = n - 8;
        }
        long n1 = n;
        for (int i = n - 1; i > 1 + num1; i--) {
            n1 = n1 * i;
        }
        int x = n - 6;
        long n2 = x;
        for (int i = x - 1; i > 1 + num1; i--) {
            n2 = n2 * i;
        }
        if (n2 <= 0) {
            return 1;
        }
        long l = n1 / (n2 * 720)* blue;
        return  (int) l ;
    }

    /**
     * 计算总注数 双色球胆拖：[X!/(X-6+Y)!*(6-Y)!] *K
     *
     * @param redD  投注个数-红拖
     * @param redT  投注个数-红胆
     * @param blue  投注个数-蓝球
     * @return 总注数
     */
    public static int getSsqDtNum(int redT, int redD, int blue) {
        //[X!/(X-6+Y)!*(6-Y)!] *K
        BigInteger n = new BigInteger(redT + "");
        for (int i = redT - 1; i > 1; i--) {
            n = n.multiply(new BigInteger(i + ""));
        }
        int t = redT - 6 + redD;
        BigInteger n1 = new BigInteger(t + "");
        for (int i = t - 1; i > 1; i--) {
            n1 = n1.multiply(new BigInteger(i + ""));
        }
        int t1 = 6 - redD;
        BigInteger n2 = new BigInteger(t1 + "");
        for (int i = t1 - 1; i > 1; i--) {
            n2 = n2.multiply(new BigInteger(i + ""));
        }
        if (t == 0){
            n1 = new BigInteger("1");
        }
        n = n.divide(n1.multiply(n2));
        Integer num = Integer.valueOf(n.toString());
        return num * blue;
    }

    /**
     * 计算总注数 大乐透复式：[N!/(N-5)!*5!] * [K!/(K-2)!*2!]
     *
     * @param red  投注个数-红球
     * @param blue 投注个数-蓝球
     * @return 总注数
     */
    public static int getDltFsNum(int red, int blue) {
        BigInteger n = new BigInteger(red + "");
        for (int i = red - 1; i > 1; i--) {
            n = n.multiply(new BigInteger(i + ""));
        }
        int x = red - 5;
        BigInteger n1 = new BigInteger(x + "");
        for (int i = x - 1; i > 1; i--) {
            n1 = n1.multiply(new BigInteger(i + ""));
        }
        n = n.divide(n1.multiply(new BigInteger(120 + "")));

        BigInteger k = new BigInteger(blue + "");
        for (int i = blue - 1; i > 1; i--) {
            k = k.multiply(new BigInteger(i + ""));
        }
        int x1 = blue - 2;
        BigInteger k1 = new BigInteger(x1 + "");
        for (int i = x1 - 1; i > 1; i--) {
            k1 = k1.multiply(new BigInteger(i + ""));
        }
        if (x1 == 0) {
            k1 = new BigInteger("1");
        }
        k = k.divide(k1.multiply(new BigInteger(2 + "")));
        String str = n.multiply(k).toString();
        return Integer.valueOf(str);
    }

    /**
     * 计算总注数 大乐透胆拖：[X!/(X-5+Y)!*(5-Y)!] * [W!/(W-2+Z)!*(2-Z)!]
     *
     * @param redT  投注个数-蓝球
     * @param redD  投注个数-红球
     * @param blueT 投注个数-蓝球
     * @param blueD 投注个数-蓝球
     * @return 总注数
     */
    public static int getDltDtNum(int redT, int redD, int blueT, int blueD) {
        //[X!/(X-5+Y)!*(5-Y)!]
        BigInteger n = new BigInteger(redT + "");
        for (int i = redT - 1; i > 1; i--) {
            n = n.multiply(new BigInteger(i + ""));
        }
        int t = redT - 5 + redD;
        BigInteger n1 = new BigInteger(t + "");
        for (int i = t - 1; i > 1; i--) {
            n1 = n1.multiply(new BigInteger(i + ""));
        }
        int t1 = 5 - redD;
        BigInteger n2 = new BigInteger(t1 + "");
        for (int i = t1 - 1; i > 1; i--) {
            n2 = n2.multiply(new BigInteger(i + ""));
        }
        n = n.divide(n1.multiply(n2));

        //[W!/(W-2+Z)!*(2-Z)!]
        BigInteger k = new BigInteger(blueT + "");
        for (int i = blueT - 1; i > 1; i--) {
            k = k.multiply(new BigInteger(i + ""));
        }
        int x1 = blueT - 2 + blueD;
        BigInteger k1 = new BigInteger(x1 + "");
        for (int i = x1 - 1; i > 1; i--) {
            k1 = k1.multiply(new BigInteger(i + ""));
        }
        int x2 = 2 - blueD;
        BigInteger k2 = new BigInteger(x2 + "");
        for (int i = x2 - 1; i > 1; i--) {
            k2 = k2.multiply(new BigInteger(i + ""));
        }
        if (x1 == 0){
            k1 = new BigInteger("1");
        }
        k = k.divide(k1.multiply(k2));
        String str = n.multiply(k).toString();
        return Integer.valueOf(str);
    }

    /**
     * 计算总注数 七乐彩复式：[N!/(N-7)!*7!]
     *
     * @param red  投注个数-红球
     * @return 总注数
     */
    public static int getQlcFsNum(int red) {
        BigInteger n = new BigInteger(red + "");
        for (int i = red - 1; i > 1; i--) {
            n = n.multiply(new BigInteger(i + ""));
        }
        int x = red - 7;
        BigInteger n1 = new BigInteger(x + "");
        for (int i = x - 1; i > 1; i--) {
            n1 = n1.multiply(new BigInteger(i + ""));
        }
        if (x == 0){
            n1 = new BigInteger("1");
        }
        n = n.divide(n1.multiply(new BigInteger(5040 + "")));
        return Integer.valueOf(n.toString());
    }

    /**
     * 计算总注数 七乐彩胆拖：[X!/(X-7+Y)!*(7-Y)!]    http://zx.500.com/calculator/qlc.php?type=jjjs
     *
     * @param redD  投注个数-红胆
     * @param redT  投注个数-红拖
     * @return 总注数
     */
    public static int getQlcDtNum(int redD, int redT) {
        //[X!/(X-7+Y)!*(7-Y)!]
        BigInteger n = new BigInteger(redD + "");
        for (int i = redD - 1; i > 1; i--) {
            n = n.multiply(new BigInteger(i + ""));
        }
        int t = redD - 7 + redT;
        BigInteger n1 = new BigInteger(t + "");
        for (int i = t - 1; i > 1; i--) {
            n1 = n1.multiply(new BigInteger(i + ""));
        }
        int t1 = 7 - redT;
        BigInteger n2 = new BigInteger(t1 + "");
        for (int i = t1 - 1; i > 1; i--) {
            n2 = n2.multiply(new BigInteger(i + ""));
        }
        n = n.divide(n1.multiply(n2));
        return Integer.valueOf(n.toString());
    }

    /**
     * 2: 双色球奖金计算器-胆拖
     *
     * @param t 投注个数-红胆
     * @param e 投注个数-红拖
     * @param n 投注个数-蓝球
     * @param r 命中个数-红胆
     * @param l 命中个数-红拖
     * @param i 命中个数-篮球
     */
    public static int[] calcSsqDt(int t, int e, int n, int r, int l, int i) {
        int a;
        int o = 6 - t >= l ? l : 6 - t;
        int[] s = new int[6];
        for (a = 0; o >= a; a++)
            s[a] = r + a;
        int[] c = new int[6];
        int d = 0;
        if (1 == i)
            for (a = 0; o >= a; a++) {
                d = p(s[a], 0);
                if (-1 != d) {
                    c[d] += g(e - l, 6 - t - a) * (n - 1) * g(l, a);
                }
                d = p(s[a], 1);
                c[d] += g(e - l, 6 - t - a) * g(l, a);
            }
        if (0 == i)
            for (a = 0; o >= a; a++) {
                d = p(s[a], 0);
                if (-1 != d) {
                    c[d] += g(e - l, 6 - t - a) * n * g(l, a);
                }
            }
        return c;
    }

    public static int p(int t, int e) {
        int n = 1 * t + 1 * e;
        switch (n) {
            case 7:
                return 0;
            case 6:
                return 1 == e ? 2 : 1;
            case 5:
                return 3;
            case 4:
                return 4;
            default:
                return 1 == e ? 5 : -1;
        }
    }

    public static long g(int t, int e) {
        if (t >= e) {
            BigInteger n = b(t - e).multiply(b(e));
            n = b(t).divide(n);
            return n.longValue();
        }
        return 0;
    }

    public static BigInteger b(int t) {
        if (t == 0) {
            return new BigInteger("1");
        }
        BigInteger r = b(t - 1).multiply(new BigInteger(t + ""));
        return r;
    }

    /**
     * 3: 大乐透奖金计算器-复式
     *
     * @param t_red  投注个数-红球
     * @param t_blue 投注个数-蓝球
     * @param m_red  命中个数-红球
     * @param m_blue 命中个数-蓝球
     */
    public static int[] calcDltFs(int t_red, int t_blue, int m_red, int m_blue) {
        int[] o = new int[9];
        String[] z = {"5+2", "5+1", "5+0", "4+2", "4+1", "3+2", "4+0", "3+1,2+2", "3+0,2+1,1+2,0+2"};
        for (int i = 0; i < z.length; i++) {
            String[] arr = z[i].split(",");
            int zs = 0;
            for (int j = 0; j < arr.length; j++) {
                String[] qh = arr[j].split("\\+");
                int z_red = Integer.parseInt(qh[0]);
                int z_blue = Integer.parseInt(qh[1]);
                zs += getPtCon(t_red, t_blue, m_red, m_blue, z_red, z_blue);
            }
            o[i] = zs;
        }
        return o;
    }

    //普通投注计算
    public static int getPtCon(int qt, int ht, int qm, int hm, int qq, int hq) {
        int res = c(qm, qq) * c(qt - qm, 5 - qq) * c(hm, hq) * c(ht - hm, 2 - hq);
        return res;
    }

    /**
     * 4: 大乐透奖金计算器-胆拖
     *
     * @param t_red_d  投注个数-前区胆码
     * @param t_red_t  投注个数-前区拖码
     * @param t_blue_d 投注个数-后区胆码
     * @param t_blue_t 投注个数-后区拖码
     * @param m_red_d  命中个数-前区胆码
     * @param m_red_t  命中个数-前区拖码
     * @param m_blue_d 命中个数-后区胆码
     * @param m_blue_t 投注个数-后区拖码
     */
    public static int[] calcDltDt(int t_red_d, int t_red_t, int t_blue_d, int t_blue_t, int m_red_d, int m_red_t, int m_blue_d, int m_blue_t) {
        int[] o = new int[9];
        String[] z = {"5+2", "5+1", "5+0", "4+2", "4+1", "3+2", "4+0", "3+1,2+2", "3+0,2+1,1+2,0+2"};
        for (int i = 0; i < z.length; i++) {
            String[] arr = z[i].split(",");
            int zs = 0;
            for (int j = 0; j < arr.length; j++) {
                String[] qh = arr[j].split("\\+");
                int z_red = Integer.parseInt(qh[0]);
                int z_blue = Integer.parseInt(qh[1]);
                zs += getDtCon(t_red_d, t_red_t, t_blue_d, t_blue_t, m_red_d, m_red_t, m_blue_d, m_blue_t, z_red, z_blue);
            }
            o[i] = zs;
        }
        return o;
    }

    //胆拖投注计算
    public static int getDtCon(int qdt, int qtt, int hdt, int htt, int qdm, int qtm, int hdm, int htm, int qq, int hq) {
        int qfmt = 5 - qq - (qdt - qdm);
        int hfmt = 2 - hq - (hdt - hdm);
        int res = c(qtm, qq - qdm) * c(qtt - qtm, qfmt) * c(htm, hq - hdm) * c(htt - htm, hfmt);
        return res;
    }

    public static int c(int m, int n) {
        int result = 0;
        if (m < n || m < 0 || n < 0) {
            result = 0;
        } else if (m == n) {
            result = 1;
        } else {
            int mn = m - n;
            result = n == 0 ? 1 : jc(m, n, mn);
        }
        return result;
    }

    public static int jc(int m, int n, int mn) {
        int fz = 1;
        for (int i = m; i > mn; i--) {
            fz *= i;
        }
        int fm = n;
        for (int j = 1; j < n; j++) {
            fm *= j;
        }
        return fz / fm;
    }


    /**
     * 5-6: 七乐彩-奖级奖金计算   http://zx.500.com/calculator/qlc.php?type=jjjs
     * max:选号个数,	hit:命中个数(不包含特别码),
     * dan:胆码,		hd:命中胆码,	sd:胆码为特别码		sp:特别码(1:命中,0:未命中),
     * return:	各奖级注数和奖金
     */
    public static int[] calcQlcFsOrDt(int max, int dan, int hit, int hd, int sp, int sd) {
        int[] o = new int[7];

        int nh = max - dan - hit;
        int snum1;
        int snum2;
        List list = getList();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            //应该命中
            int must = (int) map.get("hit1");
            //须命中
            int h = combine(hit, must - hd);
            //须未命中
            int nhx = 7 - must - dan + hd;
            if (sp == 1) {
                if (sd == 1) {
                    snum1 = combine(nh, nhx);
                    snum2 = 0;
                } else {
                    snum1 = combine(nh - 1, nhx - 1);
                    snum2 = combine(nh - 1, nhx);
                }
            } else {
                snum1 = 0;
                snum2 = combine(nh, nhx);
            }
            if ((int) map.get("hit2") == 1) {
                o[i] = h * snum1;
            } else {
                o[i] = h * snum2;
            }
        }
        return o;

    }

    // 排列组合
    private static int combine(int m, int n) {
        if (m < n) {
            return 0;
        }
        return factorial(m, m - n + 1) / factorial(n, 1);
    }

    // 阶乘
    private static int factorial(int max, int min) {
        if (max >= min && max > 1) {
            return max * factorial(max - 1, min);
        } else {
            return 1;
        }
    }

    private static List getList() {
        List<Map<String, Integer>> list = new ArrayList<>();
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("hit1", 7);
        map1.put("hit2", 0);
        list.add(map1);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("hit1", 6);
        map2.put("hit2", 1);
        list.add(map2);
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("hit1", 6);
        map3.put("hit2", 0);
        list.add(map3);
        Map<String, Integer> map4 = new HashMap<>();
        map4.put("hit1", 5);
        map4.put("hit2", 1);
        list.add(map4);
        Map<String, Integer> map5 = new HashMap<>();
        map5.put("hit1", 5);
        map5.put("hit2", 0);
        list.add(map5);
        Map<String, Integer> map6 = new HashMap<>();
        map6.put("hit1", 4);
        map6.put("hit2", 1);
        list.add(map6);
        Map<String, Integer> map7 = new HashMap<>();
        map7.put("hit1", 4);
        map7.put("hit2", 0);
        list.add(map7);
        return list;
    }

    /**
     * 7-8: 七星彩-奖级奖金计算   https://www.lottery.gov.cn/qxc/qxcjsq/
     * F12->static.sporttery.cn->res_1_0->tcw->default->qxc->jsq.js
     *
     * @param arr 数组如下:
     *  {"ones": [1, 2,3], "twos": [1,2,3], "threes": [1,2,3,7], "fours": [1,2,3,9], "fives": [1,2,3,8],
     *   "sixs": [1,2,3,4], "sevens": [1,2,3,9], "openCode": [1,2,7,9,8,4,9]}
     */
    public static List<List<Integer>> serialArray(List<int[]> arr) {
        List<Integer> lengthArr = new ArrayList<>();
        List<Integer> productArr = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        int length = 1;
        for (int i = 0; i < arr.size(); i++) {
            int len = arr.get(i).length;
            lengthArr.add(len);
            int product = i == 0 ? 1 : arr.get(i - 1).length * productArr.get(i - 1);
            productArr.add(product);
            length *= len;
        }
        for (int i = 0; i < length; i++) {
            List<Integer> resultItem = new ArrayList<>();
            for (int j = 0; j < arr.size(); j++) {
                double v = i / productArr.get(j) % lengthArr.get(j);
                resultItem.add(arr.get(j)[new Double(v).intValue()]);
            }
            result.add(resultItem);
        }
        return result;
    }

    public static void getLevel(int[] arr, int[] openCode,  int[] levelCount) {
        if (Arrays.equals(arr, openCode)) {
            levelCount[0] += 1;
        } else if (twoLevel(arr, openCode)) {
            levelCount[1] += 1;
        } else if (threeLevel(arr, openCode)) {
            levelCount[2] += 1;
        } else if (fourLevel(arr, openCode)) {
            levelCount[3] += 1;
        } else if (FiveLevel(arr, openCode)) {
            levelCount[4] += 1;
        } else if (sixLevel(arr, openCode)) {
            levelCount[5] += 1;
        }
    }

    private static boolean twoLevel(int[] arr, int[] openCode) {
        openCode = truncate(openCode);
        int[] newArr = truncate(arr);
        return Arrays.equals(newArr, openCode);
    }

    private static int[] truncate(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr.length -1 != i){
                list.add(arr[i]);
            }
        }
        /*return arr.filter(function(ele, idx, arr) {
            return arr.length - 1 != = idx;
        });*/
        //转成int数组
        return list.stream().mapToInt(i -> i).toArray();
    }

    private static boolean threeLevel(int[] arr, int[] openCode) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (arr[i] == openCode[i]) {
                count += 1;
            }
        }
        return count >= 5 && arr[arr.length - 1] == openCode[openCode.length - 1];
    }

    private static boolean fourLevel(int[] arr, int[] openCode) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (arr[i] == openCode[i]) {
                count += 1;
            }
        }
        return count == 5 || (count == 4 && arr[arr.length - 1] == openCode[openCode.length - 1]);
    }

    private static boolean FiveLevel(int[] arr, int[] openCode) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (arr[i] == openCode[i]) {
                count += 1;
            }
        }
        return count == 4 || (count == 3 && arr[arr.length - 1] == openCode[openCode.length - 1]);
    }

    private static boolean sixLevel(int[] arr, int[] openCode) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (arr[i] == openCode[i]) {
                count += 1;
            }
        }
        int lastNum = arr[arr.length - 1];
        return count == 3 || lastNum == openCode[openCode.length - 1];
    }

   /* public static void main(String[] args) {
        //胆拖
        //m(1, 32, 7, 1, 1, 1);
        //复式
        //calc(6,10,2,2);
        //大乐透 复式
        //calcDltFs(35, 12, 5, 2);
        //大乐透 胆拖
        //int[] ints = calcDltDt(4, 3, 0, 12, 2, 2, 0, 2);
        //System.out.println(ints);

        //七乐彩 复式 1: max-投注红球 2: hit-命中红球 3: sp:特别码(1:命中,0:未命中)
        //int[] ints = calcQlcFs(10, 0, 5, 0, 1, 0);
        //System.out.println(ints);
        //七乐彩 胆拖  1: max-投注红胆 2: hit-命中红胆 3: sp:红拖)   4:dan-投注红拖
        int[] ints1 = calcQlcFsOrDt(11, 5, 4, 3, 1, 1);
        System.out.println(ints1);
    }*/

}
