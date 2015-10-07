# MailCard's Schannel Implementation
MailCard will not be using the GP's standardize SCP Secure Channel Protocol but will use it's own protocol in anticipation of JavaCards that do not implement the SCP mechanism.

### Schannel Features
* Diffie-Hellman Ephemeral.
* 2048 bit Private Keys stored in smart card's transient secure keystore.
* Well defined functionality and protocol flows.
* Negotiates AES-256 symmetric keys for encryption and HMAC-SHA1 (64 bit key) for signing of APDU packets.
* Encryption in AES-CBC-HMAC_SHA1 mode where the plaintext is first encrypted before signed.

### Schannel FLow
1.) Host gets to set P and G parameters anytime in the initial stage of the Schannel negotiation in any order.
2.) Card response with SW:9000(OK).
3.) Host may simply not set P and G to default to the default P and G settings. This step is irreversible.
4.) Once Host decides to move beyond setting P and G parameters, Host calls init() on Schannel for the card to generate it's own private key and calculate Y = G ^ Secret_Key Mod P.
5.) Card will return SW:9000(OK) when it manages to generate it's private key and Y value.
6.) The Host can calculate it's own Y values before or while calling init() on the Schannel.
7.) Host will retrieve Card's Y value first before sending Card the Host's Y value. The Y values are the so-called "public key". 
8.) If the Host accepts the Card's Y value, it will send Host's Y value to Card as a sign of acceptable. This step is irreversible as well.
9.) Card will return SW:9000(OK) when it receives an authentic Host's Y value.
10.) Host calls final() on Schannel which causes the Card to generate the session secret (S) which will derive the session AES and HMAC keys.
11.) Host will also generate it's own session secret (S) and derive it's own session AES and HMAC keys before or while calling final() on Schannel.
12.) During step 9.), Host may send a 16 bit counter that has been encrypted and signed to the Card as a challenge and the Card unwraps and verifies thew 16 bit counter and increments the counter by 1 before wrapping and signing a response to proof a successful connection.

### Design Decisions
1.) Why use HMAC-SHA1 and not HMAC-SHA256 or a stronger MAC ? 
Answer: SHA1 is used for the short length of the session and is still considered secure enough not to be broken within a day (daily email usage). Most smart cards have support for SHA1 although a good growing number have support for up to SHA512. Speed of signing and verifying the messages are important to improve user experience.

2.) Why use a 64-bit key for HMAC-SHA1 ?
Answer: JavaCard only supports a 64 bit keyed HMAC-SHA1.

3.) Why not write your own HMAC-SHA1 ?
Answer: Space consumption for the card's on-board RAM and EEPROM/Flash storage.

4.) Why use AES-CBC instead of sopmething supposedly stronger like AES-GCM ?
Answer: JavaCard does not support GCM mode and to write your own GCM codes you risk consuming precious space on the smart card.

5.) Why not use Poly-1305-AES for HMAC.
Answer: Not directly supported on JavaCard and resource constrains on the card. Remember, this is a security embeddded project after all, not a desktop project with tonnes of space to spare.

6.) Using non-ECC Diffie-Hellman (e.g. ECDH types) on the smart card would meant re-inventing the wheel ?
Answer: Not true. The RSA modexp function (encrypt) is rather handy as both RSA and traditional DH uses modexp and re-usable.

7.) Why not use ECDH stuff ?
Answer: Not all cards support ECC crypto although more cards are coming to the market equipped with ECC crypto but it is best to prepare for a card not capable of ECC crypto.

### Cryptographic Default Values
* DH Group 14 as per RFC-3526 with generator 2. Generator value of 5 is supported but must be explicitly set by Host.


