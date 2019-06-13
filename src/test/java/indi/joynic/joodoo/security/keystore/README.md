# gen keystore file

keytool -genkeypair -alias joodoo-sec-demo -keyalg RSA -keysize 2048 -keystore D:/misc/joodoo-sec.keystore

> password: 123456

# export cert

keytool -export -alias joodoo-sec-demo -keystore D:/misc/joodoo-sec.keystore -rfc -file D:/misc/joodoo-sec.cer