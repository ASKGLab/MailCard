# MailCard
An attempt to host a tiny secure email client inside a JavaCard applet.

### MailCard Features
* Securely store email password inside the smartcard environment (AES-256 encrypted as well).
* Limited TLS features that are strictly selected (no cipher suite downgrades allowed).
* Securely store 1 set of PGP keypair inside smartcard environment for email encryption and signing.
* HOTP 2FA to authorize smartcard to send/receive emails.
* Self-Destruct features include 1x Self-Destruct PIN (specified at profile initialization) and self-destruct command.
* No-Export feature on the entire email profile preventing leaking of passwords, PINs and cryptographic keys outside of the card.
* Card will self-destruct of profile data upon too many wrong PIN tries.

### Concepts of MailCard Usage
MailCard is a smartcard email client that would do the cryptographic processing and handle the email protocols (TLS/SMTP and TLS/POP3) in a secure manner (limited feature TLS). A frontend client is necessary with Internet connections to display email contents and allow user interaction and to act as an Internet gateway for the encrypted packets between the MailCard and the email server to communicate.

A MailCard user would need to create a profile (1 set of PGP keypair either imported or generated on the card, the email server's TLS certificate to do certificate pinning, the email login credentials and an optional OTP setup).

The HOTP-based OTP setup is used at the moment a user were to intent the MailCard to send an email and the OTP option is enabled so that even if the card's PIN were to be stolen by a keylogger, a OTP code is required to authorize the MailCard applet to send an email. The OTP application can be installed onto a smartphone or even a separate OTP display token to prevent a possibly infected frontend computer from predicting the OTP codes.

### TLS Cipher Suites
* TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 (Definite)
* TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 (Definite)

Note: The usage of cryptographic algorithms are heavily dependent on the capability of the card's crypto-processor. MailCard would run a check and adjust itself during applet installation to use the most suitable cipher suites the card's crypto-processor can handle. The reqiored cryptographic operations listed below are the bare minimum the card requires. Diffie-Hellman Ephemeral prime sizes are set to 2048 bit prime and uses RFC-3526 2048 bit safe prime and g=2 as default but can be adjusted as needed.

### Required Ciphers
* RSA-2048 (Hardware Support)
* AES-128/256 (Hardware Support)
* DHE-2048 (Hardware & Software Support)

### Required Hash
* SHA-256 (Hardware Support)

### Cipher Modes
* RSA PKCS1 (Hardware Support)
* CBC (Hardware &/OR Software Support)

### MAC Modes
* HMAC-SHA-256 (Hardware & Software Support)

### OTP Mode
* RFC4226 HMAC-based OTP (only supports 6 digit code)

### Removed Cryptographic Functions
* GCM mode is removed to make codes lean.
* SHA1 hash function removed due to recent vulnerabilities demonstrating attacks on SHA1 attached. (https://sites.google.com/site/itstheshappening/)
* RCF4226 (HOTP) will be the only function using HMAC-SHA1 for compatibility until a new international standard arrives for HOTP authentication.
* Support for DHE-1024 is removed for the sake of security.
* RSA-OAEP are not widely available and will not be supported.
