
#include <PN532_HSU.h>
#include <PN532.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <Arduino_JSON.h>
#include <Keypad.h>


//Constants
#define ROWS 4
#define COLS 4

//
//const char* ssid = "Livebox-53AC";
//const char* password =  "hCtkvwtn7GsuxASSjQ";
//String serverName = "http://192.168.1.65:8080";
const char* ssid = "JadTr";
const char* password =  "Sha7aad123";
String serverName = "http://172.20.10.8:8080";

WiFiClient client;



const char kp4x4Keys[ROWS][COLS]  = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

// A -> LEFT
// B -> RIGHT
// C -> CANCEL
// D -> OK

byte pins[8] = {12, 14, 27, 26, 25, 33, 32, 19};
byte rowKp4x4Pin [4] = {pins[0], pins[1], pins[2], pins[3]};
byte colKp4x4Pin [4] = {pins[4], pins[5], pins[6], pins[7]};
//Variables
Keypad kp4x4  = Keypad(makeKeymap(kp4x4Keys), rowKp4x4Pin, colKp4x4Pin, ROWS, COLS);

int lcdColumns = 16;
int lcdRows = 2;
LiquidCrystal_I2C lcd(0x27, lcdColumns, lcdRows); // set the LCD address to 0x27 for a 16 chars and 2 line display

PN532_HSU pn532hsu(Serial2);
PN532 nfc(pn532hsu);

int shortDelay = 700;
int normalDelay = 1000;

int greenPin = 4;
int redPin = 0;

byte arrowRight[] = {
  B01000,
  B00100,
  B00010,
  B00001,
  B00010,
  B00100,
  B01000,
  B00000
};

byte arrowLeft[] = {
  B00010,
  B00100,
  B01000,
  B10000,
  B01000,
  B00100,
  B00010,
  B00000
};

void setup() {

  Serial.begin(9600);
  Serial.println("This is for debugging");

  pinMode(greenPin, OUTPUT);
  pinMode(redPin, OUTPUT);

  digitalWrite(redPin, LOW);
  digitalWrite(greenPin, LOW);

  lcd.init();// initialize the lcd
  lcd.backlight();

  lcd.createChar(0, arrowRight);
  lcd.createChar(1, arrowLeft);

  WiFi.begin(ssid, password);

  lcd.setCursor(3, 0);
  lcd.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }
  lcd.clear();

  lcd.setCursor(5, 0);
  lcd.print("Wi-Fi");
  lcd.setCursor(3, 1);
  lcd.print("Connected");
  Serial.println("WiFi Connected");
  blinkLed(greenPin, 1, 150);
  delay(shortDelay);

  nfc.begin();

  uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {
    lcd.clear();
    Serial.println("Reader Not Ready, Restart");
    lcd.setCursor(0, 1);
    lcd.print("Restart Required");
    digitalWrite(redPin, HIGH);
    // halt
    while (1) {
      scrollText(0, 0, "Reader not detected", 200, lcdColumns);
    }
  }
  // configure board to read RFID tags
  nfc.SAMConfig();
  // Got ok data, print it out!
  Serial.print("Found chip PN5"); Serial.println((versiondata >> 24) & 0xFF, HEX);
  Serial.print("Firmware ver. "); Serial.print((versiondata >> 16) & 0xFF, DEC);
  Serial.print('.'); Serial.println((versiondata >> 8) & 0xFF, DEC);

  lcd.clear();
  lcd.setCursor(2, 0);
  lcd.print("Reader Ready");
  Serial.println("Reader Ready");
  blinkLed(greenPin, 1, 150);
  delay(shortDelay);
  lcd.clear();

}


void loop() {
  bool canceled = false;
  lcd.setCursor(2, 0);
  lcd.print("Waiting for");
  lcd.setCursor(0, 1);
  lcd.print("Instructor Card");

  String uid = getUID();
  if (uid != "") {

    Serial.println(uid);
    // getIf Instructor
    JSONVar mainCall = JSON.parse(getIfInstructor(uid));
    if (JSON.typeof(mainCall) == "undefined") {
      Serial.println("Parsing input failed!");
    }
    if (mainCall["isInstructor"]) {
      Serial.println("Instructor Card Read");
      Serial.println("Password authenticating");

      blinkLed(greenPin, 1, 200);
      // authenticating instructor
      lcd.clear();
      lcd.setCursor(1, 0);
      lcd.print("Enter Passcode");
      String pass = "";

      lcd.setCursor(6, 1);
      while (pass.length() != 4) {
        char keyPressed = kp4x4.getKey();
        if (keyPressed) {
          pass += keyPressed;
          lcd.print("*");
        }
      }
      // passcode correct
      if (pass == mainCall["password"]) {
        Serial.println("Passcode correct");
        lcd.clear();
        lcd.setCursor(3, 0);
        lcd.print("Passcode");
        lcd.setCursor(5, 1);
        lcd.print("Correct");

        blinkLed(greenPin, 1, 200);
        delay(normalDelay);
        Serial.println("Choosing course");

        // displaying instructor courses and choosing the course to unlock

        lcd.clear();
        lcd.setCursor(3, 0);
        lcd.print("Choose");
        lcd.setCursor(5, 1);
        lcd.print("Course");
        delay(normalDelay);

        int numOfCourses = mainCall["courses"].length();
        Serial.print("Total number of courses this instructor is giving: ");
        Serial.println(numOfCourses);
        int courseIndicator = 0;
        lcd.clear();
        lcd.setCursor(0, 0);
        lcd.write(1);
        lcd.setCursor(15, 0);
        lcd.write(0);
        lcd.setCursor(1, 0);
        lcd.print((const char*)mainCall["courses"][courseIndicator]["courseName"]);
        lcd.setCursor(2, 1);
        String startTime = (const char*)mainCall["courses"][courseIndicator]["startTime"];
        String endTime = (const char*)mainCall["courses"][courseIndicator]["endTime"];
        lcd.print(startTime.substring(0, 5));
        lcd.print("-");
        lcd.print(endTime.substring(0, 5));

        // choosing is here
        char keyPressed = kp4x4.getKey();
        while (keyPressed != 'D') {
          if (keyPressed == 'B') {
            courseIndicator++;
            if (courseIndicator >= numOfCourses) {
              courseIndicator = 0;
            }

            lcd.clear();
          }
          if (keyPressed == 'A') {
            courseIndicator--;
            if (courseIndicator < 0) {
              courseIndicator = numOfCourses - 1;
            }

            lcd.clear();
          }
          if (keyPressed == 'C') {
            canceled = true;
            lcd.clear();
            break;
          }
          lcd.setCursor(0, 0);
          lcd.write(1);
          lcd.setCursor(15, 0);
          lcd.write(0);
          lcd.setCursor(1, 0);
          lcd.print((const char*)mainCall["courses"][courseIndicator]["courseName"]);
          lcd.setCursor(2, 1);
          String startTime = (const char*)mainCall["courses"][courseIndicator]["startTime"];
          String endTime = (const char*)mainCall["courses"][courseIndicator]["endTime"];
          lcd.print(startTime.substring(0, 5));
          lcd.print("-");
          lcd.print(endTime.substring(0, 5));
          keyPressed = kp4x4.getKey();
        }

        // instructor chose course
        if (!canceled)
        {
          lcd.clear();
          int courseId = mainCall["courses"][courseIndicator]["id"];
          String instructorId = (const char*) mainCall["uid"];
          Serial.print("Course ID = ");
          Serial.println(courseId);
          Serial.print("Instructor ID = ");
          Serial.println(instructorId);

          String idRead = getUID();
          Serial.print("ID Read 1= ");
          Serial.println(idRead);


          while (idRead != instructorId) {
            lcd.setCursor(1, 0);
            lcd.print("Waiting for");
            lcd.setCursor(2, 1);
            lcd.print("Student Card");
            if (idRead != "") {
              JSONVar secondCall = JSON.parse(attendStudentToCourse(courseId, idRead));
              if (JSON.typeof(mainCall) == "undefined") {
                Serial.println("Parsing input failed!");
              }

              properDisplay((const char*)secondCall["responseMsg"]);
              if((int)secondCall["response"]==405)
              {
                blinkLed(redPin, 1, 300);
                
              } 
              else if((int)secondCall["response"]==500)
              {
                blinkLed(redPin, 2, 300);
                
              }
              else{
                blinkLed(greenPin, 1, 300);
              }

            }
            idRead = getUID();
          }
          lcd.clear();
        }
      }
      // passcode incorrect
      else {
        Serial.println("Passcode incorrect");
        lcd.clear();
        lcd.setCursor(4, 0);
        lcd.print("Passcode");
        lcd.setCursor(4, 1);
        lcd.print("Incorrect");
        blinkLed(redPin, 1, 300);
        delay(normalDelay);
      }
    }
    // not instructor card
    else Serial.println("Not Instructor Card Read");
    blinkLed(redPin, 1, 300);
  }
}

void blinkLed(int pin, int times, int spd) {
  for (int i = 0; i < times; i++) {
    digitalWrite(pin, HIGH);
    delay(spd);
    digitalWrite(pin, LOW);
    delay(spd);
  }
}

void properDisplay(String message) {
  int spaceCounter = 0;
  lcd.setCursor(0, 0);
  lcd.clear();
  int startFrom = 0;
  for (int i = 0; i < message.length(); i++) {
    if (spaceCounter == 2) {
      startFrom = i;
      break;
    }
    if (message[i] == ' ')
      spaceCounter += 1;
    lcd.print(message[i]);
  }
  lcd.setCursor(3, 1);
  for (startFrom; startFrom < message.length(); startFrom++) {

    lcd.print(message[startFrom]);
  }
  delay(normalDelay);
  lcd.clear();
}

String getIfInstructor(String uid) {

  HTTPClient http;

  String serverPath = serverName + "/instructor/" + uid;
  Serial.print("Getting Address: ");
  Serial.println(serverPath);

  // Your Domain name with URL path or IP address with path
  http.begin(serverPath.c_str());

  // Send HTTP GET request
  int httpResponseCode = http.GET();
  String payload = "{}";
  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    payload = http.getString();
  }
  else {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
  }
  // Free resources
  http.end();
  Serial.println(payload);
  return payload;
}

String attendStudentToCourse(int courseId, String studentId) {

  HTTPClient http;

  String serverPath = serverName + "/attend/" + (String)courseId + "/" + studentId;
  Serial.print("Getting Address: ");
  Serial.println(serverPath);

  // Your Domain name with URL path or IP address with path
  http.begin(serverPath.c_str());

  // Send HTTP GET request
  int httpResponseCode = http.GET();
  String payload = "{}";
  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    payload = http.getString();
  }
  else {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
  }
  // Free resources
  http.end();
  Serial.println(payload);
  return payload;
}

void scrollText(int row, int col, String message, int delayTime, int lcdColumns) {
  for (int i = 0; i < lcdColumns; i++) {
    message = " " + message;
  }
  message = message + " ";
  for (int pos = 0; pos < message.length(); pos++) {
    lcd.setCursor(col, row);
    lcd.print(message.substring(pos, pos + lcdColumns));
    delay(delayTime);
  }
}

String getUID() {
  uint8_t success;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Buffer to store the returned UID
  uint8_t uidLength;                        // Length of the UID (4 or 7 bytes depending on ISO14443A card type)
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);

  String out = "";
  if (success) {
    Serial.println("Success");
    for (int i = 0; i < uidLength; i++) {
      String part = String(uid[i], HEX);
      if (part.length() == 1)
        part = "0" + part;
      out += part;
      if (i != uidLength - 1)
        out += ":";
    }
    out.toUpperCase();
  }
  return out;

}
