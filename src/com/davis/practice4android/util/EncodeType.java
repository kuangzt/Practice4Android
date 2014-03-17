package com.davis.practice4android.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EncodeType {
    
    public static String getEncode(String filepath) {
        byte[] content = null;
        int size = 0;
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filepath));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
            content = out.toByteArray();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return encodeTest(content, content.length);
    }
    
    public static String encodeTest(byte[] p,int len) {
        int gb2312=-1;
        int gbk=-1;
        int big5=0;
        int type=-1;

        if(asciiAll(p,len))
            return "ASCII";

        if(IsUTF8String(p,len))
            return "UTF8";

        gb2312 = gb2312All_l(p,len);
        gbk = gbkAll_l(p,len);
//      big5 = big5All(p,len);
        type = gb2312 <<2 | gbk << 1 | big5;
        switch(type) {
        case 0:
            return "UNICODE";
        case 1:
            return "BIG5";
        case 2:
            return "GBK";
        case 3:
            return "BIG5";
        case 4:
            return "GBK";
        case 5:
            return "BIG5";
        case 6:
            return "GBK";
        case 7:
            return "GBK";
        default:
            return "UNICODE";
        }
//      return UNICODE;
    }
    
    private static boolean asciiAll(byte[] p,int len) {
        int i=0;
        for(i=0;i<len;i++) {
            int b1 = p[i] & 0x80;
            if (!(b1 < 1))
                return false;
            }
        return true;
    }
    
    private static boolean IsUTF8String(byte[] str, long  length) {
        int i = 0;
        long nBytes = 0;
        byte chr = 0;
        boolean  allAscii = true;
        int len=-1;
        for(i = 0; i < length; i ++) {
            int ff = str[i] & 0xFF;
            chr = (byte) (str[i] & 0x80);
            if( chr != 0 ) {
                allAscii = false;
            } else {
                allAscii = true;
                continue;
            }
            
            if(nBytes == 0) {
                if(ff >= 0x80) {
                    if(ff >= 0xFC && ff <= 0xFD)
                        nBytes = 6;
                    else if(ff >= 0xF8)
                        nBytes = 5;
                    else if(ff >= 0xF0)
                        nBytes = 4;
                    else if(ff >= 0xE0)
                        nBytes = 3;
                    else if(ff >= 0xC0)
                        nBytes = 2;
                    else {
                        return false;
                    }
                    len = (int) nBytes;
                    if(i + nBytes > length)
                        return false;
                    nBytes--;
                }
            } else {
                if( (ff >= 0x80 && ff <= 0xBF)) {
                    nBytes--;
                    if(nBytes==0){
                        if(len>2) {
                            len=-1;
                            nBytes=0;
                            return true;
                        }
                        if(len==2) {
                            len=-1;
                            nBytes=0;
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return !allAscii;
    }
    
    private static int gb2312All_l(byte[] p,int len) {
        int i;
        short sh;
        int tc;
        int[] ch = new int[2];
        
        for (i = 0; i < len; i += 2) {
            tc = p[i] & 0xFF;
            ch[0]=tc;
            if(((tc & 0x80) == 0)) {
                i--;
                continue;
            }
            ch[1] = p[i+1] & 0xFF;
            tc = (((ch[0] << 8) & 0xff00) | (ch[1]& 0xff));
            if(tc <= 0xfefe && tc >= 0xa1a1)
                continue ;
            else
            return 0;
        }
        return 1;
    }
    
    private static int gbkAll_l(byte[] p,int len) {
        int i;
        int tc;
        int[] ch = new int[2];
     
        for(i=0;i<len;i+=2) {
            tc = p[i] & 0xFF;
            ch[0]=tc;
            if(!(tc >= 0x80)) {
                i--;
                continue;
            }
            ch[1] = p[i+1] & 0xFF;
            tc = (((ch[0] << 8) & 0xff00) | (ch[1]& 0xff));
            if(tc <= 0xfefe && tc >= 0x8140) {
                if((tc <= 0XAFFE && tc >= 0xaaa1)||(tc <= 0xfefe && tc >= 0xf8a1)||(tc <= 0xa7a0 && tc >= 0xa140)) { 
                    p[i] = ' ';
                    p[i+1] = ' ';
                }
                continue ;
            } else {
                return 0;
            }
        }
        return 1;
    }
    
    private static int big5All(byte[] p,int len) {
        int i;
        int sh;
        for(i=0;i<len/2;i++) {
            sh = p[i] & 0xFF;
            if((sh<=0xf97e&&sh>=0xa140)||(sh<=0xf9fe&&sh>=0xa1a1))
                continue ;
            else
                return 0;
        }
        return 1;
    }
    
}