package app.android.dmzj.request;

public class api {
    public static final String DMZJ_DOMAIN_NAME = "dmzj.com";
    public static final String IDMZJ_DOMAIN_NAME = "idmzj.com";
    public static final String MUWAI_DOMAIN_NAME = "muwai.com";

    /// V3接口，无加密
    public static final String BASE_URL_V3 = "https://nnv3api." + IDMZJ_DOMAIN_NAME;

    /// V4接口,返回加密数据的
    public static final String BASE_URL_V4 = "https://v4api." + IDMZJ_DOMAIN_NAME;

    /// V3 API
    public static final String BASE_URL_V3_API = "https://v3api." + IDMZJ_DOMAIN_NAME;

    /// V3 评论
    public static final String BASE_URL_V3_COMMENT = "http://v3comment." + IDMZJ_DOMAIN_NAME;

    /// 用户
    public static final String BASE_URL_USER = "http://nnuser." + IDMZJ_DOMAIN_NAME;

    /// Interface
    public static final String BASE_URL_INTERFACE = "http://nninterface." + IDMZJ_DOMAIN_NAME;

    /// V4 API的密钥
    public static final String V4_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK8nNR1lTnIfIes6oRWJNj3mB6OssDGx0uGMpgpbVCpf6+VwnuI2stmhZNoQcM417Iz7WqlPzbUmu9R4dEKmLGEEqOhOdVaeh9Xk2IPPjqIu5TbkLZRxkY3dJM1htbz57d/roesJLkZXqssfG5EJauNc+RcABTfLb4IiFjSMlTsnAgMBAAECgYEAiz/pi2hKOJKlvcTL4jpHJGjn8+lL3wZX+LeAHkXDoTjHa47g0knYYQteCbv+YwMeAGupBWiLy5RyyhXFoGNKbbnvftMYK56hH+iqxjtDLnjSDKWnhcB7089sNKaEM9Ilil6uxWMrMMBH9v2PLdYsqMBHqPutKu/SigeGPeiB7VECQQDizVlNv67go99QAIv2n/ga4e0wLizVuaNBXE88AdOnaZ0LOTeniVEqvPtgUk63zbjl0P/pzQzyjitwe6HoCAIpAkEAxbOtnCm1uKEp5HsNaXEJTwE7WQf7PrLD4+BpGtNKkgja6f6F4ld4QZ2TQ6qvsCizSGJrjOpNdjVGJ7bgYMcczwJBALvJWPLmDi7ToFfGTB0EsNHZVKE66kZ/8Stx+ezueke4S556XplqOflQBjbnj2PigwBN/0afT+QZUOBOjWzoDJkCQClzo+oDQMvGVs9GEajS/32mJ3hiWQZrWvEzgzYRqSf3XVcEe7PaXSd8z3y3lACeeACsShqQoc8wGlaHXIJOHTcCQQCZw5127ZGs8ZDTSrogrH73Kw/HvX55wGAeirKYcv28eauveCG7iyFR0PFB/P/EDZnyb+ifvyEFlucPUI0+Y87F";

    //V4解密算法
    //    public static Uint8List decryptV4(String text) {
//        try {
//            RSAKeypair rsaKeypair =
//            RSAKeypair(RSAPrivateKey.fromString(V4_PRIVATE_KEY));
//            var decrypted = rsaKeypair.privateKey.decryptData(base64.decode(text));
//            return decrypted;
//        } catch (e) {
//            throw AppError('返回数据解密失败');
//        }
//    }

    /// 签名
//    public static String sign(String content, String mode) {
//        var utf8Content = utf8.encode(mode + content);
//
//        return md5.convert(utf8Content).toString();
//    }

    /// 小说正文链接
    //小说解密算法
//    public static String getNovelContentUrl(
//    {required int volumeId, required int chapterId}) {
//        var path = "/lnovel/${volumeId}_$chapterId.txt";
//        var ts = (DateTime.now().millisecondsSinceEpoch / 1000).toStringAsFixed(0);
//        var key =
//            "IBAAKCAQEAsUAdKtXNt8cdrcTXLsaFKj9bSK1nEOAROGn2KJXlEVekcPssKUxSN8dsfba51kmHM";
//        key += path;
//        key += ts;
//        key = md5.convert(utf8.encode(key)).toString().toLowerCase();
//
//        return "http://jurisdiction.idmzj.com$path?t=$ts&k=$key";
//    }

}