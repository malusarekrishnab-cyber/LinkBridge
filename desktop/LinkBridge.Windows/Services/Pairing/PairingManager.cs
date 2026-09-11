using System;
using System.Security.Cryptography;
using System.Text;

namespace LinkBridge.Windows.Services.Pairing
{
    public class PairingManager
    {
        private RSA _rsa;

        public PairingManager()
        {
            _rsa = RSA.Create(2048);
        }

        public string GetPublicKeyBase64()
        {
            byte[] pubKey = _rsa.ExportSubjectPublicKeyInfo();
            return Convert.ToBase64String(pubKey);
        }

        public RSA LoadPublicKeyFromBase64(string base64Str)
        {
            byte[] pubKey = Convert.FromBase64String(base64Str);
            RSA rsa = RSA.Create();
            rsa.ImportSubjectPublicKeyInfo(pubKey, out _);
            return rsa;
        }

        public string SignData(string data)
        {
            byte[] dataBytes = Encoding.UTF8.GetBytes(data);
            byte[] signature = _rsa.SignData(dataBytes, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);
            return Convert.ToBase64String(signature);
        }

        public bool VerifySignature(string data, string signatureBase64, RSA foreignPublicKey)
        {
            byte[] dataBytes = Encoding.UTF8.GetBytes(data);
            byte[] sigBytes = Convert.FromBase64String(signatureBase64);
            return foreignPublicKey.VerifyData(dataBytes, sigBytes, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);
        }
    }
}
