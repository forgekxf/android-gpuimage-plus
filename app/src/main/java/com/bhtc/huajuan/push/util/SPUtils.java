package com.bhtc.huajuan.push.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * SharePreferences操作工具类
 */
public class SPUtils {
    private static String tag = SPUtils.class.getSimpleName();

    private static SharedPreferences sp;
    private static Context App = com.bhtc.huajuan.push.App.App;


    /**
     * 保存布尔值
     *
     * @param key
     * @param value
     */
    public static void saveBoolean(String spName, String key, boolean value) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存字符串
     *
     * @param key
     * @param value
     */
    public static void saveString(String spName, String key, String value) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().putString(key, value).commit();
    }
    /**
     * 获取set字符值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static void saveStringSet(String spName, String key, Set<String> defValue) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().putStringSet(key, defValue).commit();
    }
    /**
     * 删除数据
     * @param spName
     * @param key
     */
    public static void removeData(String spName, String key) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().remove(key).commit();
    }

    public static void clear(String spName) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().clear().commit();
    }

    /**
     * 保存long型
     *
     * @param key
     * @param value
     */
    public static void saveLong(String spName, String key, long value) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 保存int型
     *
     * @param key
     * @param value
     */
    public static void saveInt(String spName, String key, int value) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 保存float型
     *
     * @param key
     * @param value
     */
    public static void saveFloat(String spName, String key, float value) {
        sp = App.getSharedPreferences(spName, 0);
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 获取字符值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String spName, String key, String defValue) {
        sp = App.getSharedPreferences(spName, 0);
        return sp.getString(key, defValue);
    }
    /**
     * 获取set字符值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Set<String> getStringSet(String spName, String key, Set<String> defValue) {
        sp = App.getSharedPreferences(spName, 0);
        return sp.getStringSet(key, defValue);
    }
    /**
     * 获取int值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String spName, String key, int defValue) {
        sp = App.getSharedPreferences(spName, 0);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取long值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(String spName, String key, long defValue) {
        sp = App.getSharedPreferences(spName, 0);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取float值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(String spName, String key, float defValue) {
        sp = App.getSharedPreferences(spName, 0);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取布尔值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String spName, String key,
                                     boolean defValue) {
        sp = App.getSharedPreferences(spName, 0);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 将对象进行base64编码后保存到SharePref中
     *
     * @param context
     * @param key
     * @param object
     */
//	public static void saveObj(Context context, String key, Object object) {
//		if (sp == null)
//			sp = context.getSharedPreferences(SpName, 0);
//
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ObjectOutputStream oos = null;
//		try {
//			oos = new ObjectOutputStream(baos);
//			oos.writeObject(object);
//			// 将对象的转为base64码
//			String objBase64 = new String(Base64.encodeBase64(baos
//					.toByteArray()));
//
//			sp.edit().putString(key, objBase64).commit();
//			oos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

    /**
     * 将SharePref中经过base64编码的对象读取出来
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
//	public static Object getObj(Context context, String key) {
//		if (sp == null)
//			sp = context.getSharedPreferences(SpName, 0);
//		String objBase64 = sp.getString(key, null);
//		if (TextUtils.isEmpty(objBase64))
//			return null;
//
//		// 对Base64格式的字符串进行解码
//		byte[] base64Bytes = Base64.decodeBase64(objBase64.getBytes());
//		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
//
//		ObjectInputStream ois;
//		Object obj = null;
//		try {
//			ois = new ObjectInputStream(bais);
//			obj = (Object) ois.readObject();
//			ois.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return obj;
//	}
}
