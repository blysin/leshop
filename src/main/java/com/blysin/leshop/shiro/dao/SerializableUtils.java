package com.blysin.leshop.shiro.dao;

import java.io.*;

/**
 * @author Blysin
 * @since 2017/10/29
 */
public class SerializableUtils {
    public static String serialize(Object obj) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(os);) {
            oos.writeObject(obj);
            return new String(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deSerialize(String str) {
        try (ByteArrayInputStream os = new ByteArrayInputStream(str.getBytes()); ObjectInputStream oos = new ObjectInputStream(os);) {
            return oos.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
