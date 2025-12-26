- ### Creating keystore for ssh
```
keytool -genkeypair -alias pcidssdemo -keyalg RSA -keysize 2048 -validity 3650 -storetype PKCS12 -keystore keystore.p12 -storepass pcidssdemo -keypass pcidssdemo -ext "SAN=DNS:localhost,IP:127.0.0.1"
```

- ### Exporting keystore as crt to add as a trusted certificate
```
keytool  -export -alias pcidssdemo -keystore keystore.p12 -file springboot.crt
```

- ### Creating private key for JWT Signing
```
 openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
```

- ### Separating public key from private key
```
openssl rsa -in private_key.pem -pubout -out public_key.pem
```

### CardEncryptor

- HashiCorp vault, started with dev mode for demo purposes. This will disable sealing of vault which can cause security problems in LIVE environment. 


