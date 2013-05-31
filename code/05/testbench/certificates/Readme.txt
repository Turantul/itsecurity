Generate new key:
openssl genrsa -out *.key 2048

Create new request:
openssl req -new -key *.key -out *.csr

Self-sign certificate with the root:
openssl x509 -req -days 365 -in *.csr -CA ca.crt -CAkey ca.key -out *.crt -set_serial xx

Create .p12:
openssl pkcs12 -export -out *.p12 -inkey *.key -in *.crt -chain -CAfile ca.crt

Load public key into truststore:
keytool -import -keystore *.jks -file *.crt