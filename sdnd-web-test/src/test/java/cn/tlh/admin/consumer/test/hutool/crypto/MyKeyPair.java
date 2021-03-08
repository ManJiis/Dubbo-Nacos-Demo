package cn.tlh.admin.consumer.test.hutool.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TANG
 * @date 2021-02-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyKeyPair {
    private byte[] publicKey;
    private byte[] privateKey;
}
