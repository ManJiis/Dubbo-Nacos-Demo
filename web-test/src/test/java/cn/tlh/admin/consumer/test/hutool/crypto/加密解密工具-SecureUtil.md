### 介绍

SecureUtil主要针对常用加密算法构建快捷方式，还有提供一些密钥生成的快捷工具方法。

### 方法介绍

- 对称加密
  - SecureUtil.aes
  - SecureUtil.des
- 摘要算法
  - SecureUtil.md5
  - SecureUtil.sha1
  - SecureUtil.hmac
  - SecureUtil.hmacMd5
  - SecureUtil.hmacSha1
- 非对称加密
  - SecureUtil.rsa
  - SecureUtil.dsa
- UUID
  - SecureUtil.simpleUUID 方法提供无“-”的UUID
- 密钥生成
  - SecureUtil.generateKey 针对对称加密生成密钥
  - SecureUtil.generateKeyPair 生成密钥对（用于非对称加密）
  - SecureUtil.generateSignature 生成签名（用于非对称加密）

对称加密

```text
加解密使用的是同一个秘钥

基于“对称密钥”的加密算法主要有DES、3DES（TripleDES）、AES、RC2、RC4、RC5和Blowfish等。

最常用的对称加密算法有DES、3DES（TripleDES）和AES。
```

非对称加密

```text
加解密使用的是不同的秘钥

基于“非对称密钥”的加密算法主要有RSA、Elgamal、背包算法、Rabin、D-H、ECC（椭圆曲线加密算法）。

使用最广泛的是RSA算法，Elgamal是另一种常用的非对称加密算法。
```

使用场景

```text
在互联网后端技术中非对称加密技术主要用于登录、数字签名、数字证书认证等场景。

既然堆成加密存在安全问题, 为什么还使用对称加密呢? ( 当当前功能不开放到外网时, 使用对称加密即可 )
```
