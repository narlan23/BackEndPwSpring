package com.backend.backpw.pwapi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Gamed {
    public String ip;
    public String version;
    public boolean cycle = false;

    public Gamed() {
        this.cycle = false;
    }

    public byte[] createHeader(int opcode, byte[] data) {
        ByteBuffer buffer = ByteBuffer.allocate(cuint(opcode).length + cuint(data.length).length + data.length);
        buffer.put(cuint(opcode));
        buffer.put(cuint(data.length));
        buffer.put(data);
        return buffer.array();
    }

    public byte[] packString(String data) {
        byte[] utf16Data = data.getBytes(StandardCharsets.UTF_16LE);
        ByteBuffer buffer = ByteBuffer.allocate(cuint(utf16Data.length).length + utf16Data.length);
        buffer.put(cuint(utf16Data.length));
        buffer.put(utf16Data);
        return buffer.array();
    }

    public byte[] packLongOctet(byte[] data) {
        ByteBuffer buffer = ByteBuffer.allocate(2 + data.length);
        buffer.putShort((short) (data.length + 32768));
        buffer.put(data);
        return buffer.array();
    }

    public byte[] packOctet(String hexData) {
        byte[] data = hexStringToByteArray(hexData);
        ByteBuffer buffer = ByteBuffer.allocate(cuint(data.length).length + data.length);
        buffer.put(cuint(data.length));
        buffer.put(data);
        return buffer.array();
    }

    public byte[] packInt(int data) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(data);
        return buffer.array();
    }

    public byte[] packByte(byte data) {
        return new byte[]{data};
    }

    public byte[] packFloat(float data) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putFloat(data);
        byte[] array = buffer.array();
        reverseArray(array);
        return array;
    }

    public byte[] packShort(short data) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(data);
        return buffer.array();
    }

    public byte[] packLong(long data) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt((int) (data >> 32));
        buffer.putInt((int) data);
        return buffer.array();
    }

    public String hex2octet(String tmp) {
        while (tmp.length() < 8) {
            tmp = '0' + tmp;
        }
        return tmp;
    }

    public String reverseOctet(String str) {
        StringBuilder octet = new StringBuilder();
        int length = str.length() / 2;
        for (int i = 0; i < length; i++) {
            String tmp = str.substring(str.length() - 2);
            octet.append(tmp);
            str = str.substring(0, str.length() - 2);
        }
        return octet.toString();
    }

    public int hex2int(String value) {
        StringBuilder reversed = new StringBuilder();
        for (int i = value.length(); i > 0; i -= 2) {
            reversed.append(value.substring(i - 2, i));
        }
        return Integer.parseUnsignedInt(reversed.toString(), 16);
    }

    public long getTime(String str) {
        return Long.parseLong(str, 16);
    }

    public String getIp(String str) {
        long ipLong = Long.parseLong(str, 16);
        return ((ipLong >> 24) & 0xFF) + "." +
                ((ipLong >> 16) & 0xFF) + "." +
                ((ipLong >> 8) & 0xFF) + "." +
                (ipLong & 0xFF);
    }

    public long putIp(String str) {
        long ip = Arrays.stream(str.split("\\."))
                .mapToLong(Long::parseLong)
                .reduce(0, (a, b) -> (a << 8) + b);
        String hex = Long.toHexString(ip);
        hex = reverseOctet(hex);
        return Long.parseLong(hex, 16);
    }

    public byte[] cuint(int data) {
        ByteBuffer buffer;
        if (data < 64) {
            buffer = ByteBuffer.allocate(1);
            buffer.put((byte) data);
        } else if (data < 16384) {
            buffer = ByteBuffer.allocate(2);
            buffer.putShort((short) (data | 0x8000));
        } else if (data < 536870912) {
            buffer = ByteBuffer.allocate(4);
            buffer.putInt(data | 0xC0000000);
        } else {
            buffer = ByteBuffer.allocate(5);
            buffer.put((byte) -32);
            buffer.putInt(data);
        }
        byte[] array = buffer.array();
        reverseArray(array);
        return array;
    }

    public long unpackLong(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.BIG_ENDIAN);
        long high = buffer.getInt() & 0xFFFFFFFFL;
        long low = buffer.getInt() & 0xFFFFFFFFL;
        return (high << 32) | low;
    }

    public String unpackOctet(byte[] data, int[] tmp) {
        int p = tmp[0];
        int size = unpackCuint(data, p);
        String octet = byteArrayToHex(Arrays.copyOfRange(data, p, p + size));
        tmp[0] += size;
        return octet;
    }

    public String unpackString(byte[] data, int[] tmp) {
        int size = (Byte.toUnsignedInt(data[tmp[0]]) >= 128) ? 2 : 1;
        int octetlen = (Byte.toUnsignedInt(data[tmp[0]]) >= 128) ?
                ByteBuffer.wrap(data, tmp[0], size).order(ByteOrder.LITTLE_ENDIAN).getShort() - 32768 :
                ByteBuffer.wrap(data, tmp[0], size).order(ByteOrder.LITTLE_ENDIAN).getShort();
        int pp = tmp[0];
        tmp[0] += size + octetlen;
        return new String(Arrays.copyOfRange(data, pp + size, pp + size + octetlen), StandardCharsets.UTF_16LE);
    }

    public int unpackCuint(byte[] data, int p) {
        int hex = Byte.toUnsignedInt(data[p]);
        int min = 0;
        int size;
        if (hex < 0x80) {
            size = 1;
        } else if (hex < 0xC0) {
            size = 2;
            min = 0x8000;
        } else if (hex < 0xE0) {
            size = 4;
            min = 0xC0000000;
        } else {
            p++;
            size = 4;
        }
        ByteBuffer buffer = ByteBuffer.wrap(data, p, size);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int unpackCuint = buffer.getInt() - min;
        p += size;
        return unpackCuint;
    }

    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static void reverseArray(byte[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            byte temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }
    
    

}
