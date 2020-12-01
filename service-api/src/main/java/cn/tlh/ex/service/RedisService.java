package cn.tlh.ex.service;

import java.util.List;

public interface RedisService {

    boolean hasKey(String key);

    String increment(String k);

    boolean  setString(String key, Object value);

    boolean  setString(String key, Object value, int seconds);

    Object getString(String key);

    void deleteKey(String key);

    void setHash(String key, String filedKey, String value);

    String getHash(String key, String filedkey);

    long setList2LeftPush(String key, String value);

    long setList2RightPush(String key, String value);

    List<String> getList(String key, long start, long end);

    void expireMinutes(String key, int minutes);

    void expireSeconds(String key, int seconds);

    Boolean setNX(byte[] key, byte[] value);

    int verCodeCheck(String phoneNumbeKey, String phoneKey, String ipNumberKey, String verCode);
}