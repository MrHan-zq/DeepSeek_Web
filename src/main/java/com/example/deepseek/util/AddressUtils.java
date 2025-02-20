package com.example.deepseek.util;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressUtils {
    public String getInnetIp() throws SocketException {
        String localip = null;
        String netip = null;
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;

        while(netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
            Enumeration address = ni.getInetAddresses();

            while(address.hasMoreElements()) {
                ip = (InetAddress)address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                }

                if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    localip = ip.getHostAddress();
                }
            }
        }

        return netip != null && !"".equals(netip) ? netip : localip;
    }

    public static String getV4IP() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com";
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;

        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            while((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
        } catch (MalformedURLException var18) {
            var18.printStackTrace();
        } catch (IOException var19) {
            var19.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

        }

        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(1);
            ip = ipstr;
        }

        return ip;
    }

    public String getAddresses(String content, String encoding) throws UnsupportedEncodingException {
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        String returnStr = this.getResult(urlStr, content, encoding);
        if (returnStr != null) {
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                return "0";
            } else {
                String country = "";
                String area = "";
                String region = "";
                String city = "";
                String county = "";
                String isp = "";

                for(int i = 0; i < temp.length; ++i) {
                    switch(i) {
                        case 2:
                            country = temp[i].split(":")[1].replaceAll("\"", "");
                            country = URLDecoder.decode(country, encoding);
                            break;
                        case 3:
                            area = temp[i].split(":")[1].replaceAll("\"", "");
                            URLDecoder.decode(area, encoding);
                            break;
                        case 4:
                            region = temp[i].split(":")[1].replaceAll("\"", "");
                            region = URLDecoder.decode(region, encoding);
                            break;
                        case 5:
                            city = temp[i].split(":")[1].replaceAll("\"", "");
                            city = URLDecoder.decode(city, encoding);
                            if ("内网IP".equals(city)) {
                                return "地址为：内网IP";
                            }
                            break;
                        case 6:
                            county = temp[i].split(":")[1].replaceAll("\"", "");
                            county = URLDecoder.decode(county, encoding);
                            break;
                        case 7:
                            isp = temp[i].split(":")[1].replaceAll("\"", "");
                            isp = URLDecoder.decode(isp, encoding);
                    }
                }

                return "地址为：" + country + "," + region + "省," + city + "市," + county + "," + "ISP公司：" + isp;
            }
        } else {
            return null;
        }
    }

    private String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(33000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            String var10 = buffer.toString();
            return var10;
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

        }

        return null;
    }

    public static String getPublicIp() {
        try {
            String path = "http://iframe.ip138.com/ic.asp";
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("contentType", "GBK");
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "GBK"));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = in.readLine()) != null) {
                buffer.append(line);
            }

            String str = buffer.toString();
            String ipString1 = str.substring(str.indexOf("["));
            String ipsString2 = ipString1.substring(ipString1.indexOf("[") + 1, ipString1.lastIndexOf("]"));
            return ipsString2;
        } catch (Exception var10) {
            System.out.println("获取公网IP连接超时");
            return "连接超时";
        }
    }

    public static void main(String[] args) {
        AddressUtils addressUtils = new AddressUtils();
        String ip1 = "";

        try {
            ip1 = addressUtils.getInnetIp();
        } catch (SocketException var10) {
            var10.printStackTrace();
        }

        System.out.println("内网ip:" + ip1);
        String ip2 = getV4IP();
        System.out.println("外网ip:" + ip2);
        String address = "";

        try {
            address = addressUtils.getAddresses("ip=" + ip2, "utf-8");
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        }

        System.out.println("您的" + address);
        System.out.println("******************************");
        System.out.println("请输入想要查询的ip地址(输入exit退出)：");
        Scanner scan = new Scanner(System.in);
        String ip = "";

        while(!"exit".equals(ip = scan.next())) {
            try {
                address = addressUtils.getAddresses("ip=" + ip, "utf-8");
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
            }

            System.out.println(ip + "的" + address);
            System.out.println("******************************");
            System.out.println("请输入想要查询的ip地址(输入exit退出)：");
        }

        scan.close();
        System.out.println("再见");
    }
}
