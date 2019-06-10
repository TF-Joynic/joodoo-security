# gen keystore file

keytool -genkeypair -alias joodoo-sec-demo -keyalg RSA -keystore D:/misc/joodoo-sec.keystore

# export cert

keytool -export -alias joodoo-sec-demo -keystore D:/misc/joodoo-sec.keystore -rfc -file D:/misc/joodoo-sec.cer