package com.mycompany.applicationsystem; // package name

import java.util.*; // utilities
import java.text.SimpleDateFormat; // time format
import java.io.FileWriter; // file writing
import java.io.IOException; // IO errors
import java.util.regex.Pattern; // regex validation

public class ApplicationSystem { // main class

    // ---------------- MESSAGE OBJECT ----------------
    static class MessageRecord { // message class

        String id; // message id
        String message; // message text
        String recipient; // recipient number
        String timestamp; // message time
        String hash; // message hash
        String status; // message status

        // constructor
        MessageRecord(
                String id,
                String message,
                String recipient,
                String timestamp,
                String hash,
                String status
        ) {

            this.id = id; // save id
            this.message = message; // save message
            this.recipient = recipient; // save recipient
            this.timestamp = timestamp; // save time
            this.hash = hash; // save hash
            this.status = status; // save status
        }
    }

    // ---------------- STORAGE ----------------
    static ArrayList<MessageRecord> messageList = new ArrayList<>(); // store messages

    static int sentCount = 0; // total sent
    static int messageLimit = 0; // message limit

    static Scanner input = new Scanner(System.in); // scanner input

    // ---------------- USERNAME VALIDATION ----------------
    public static boolean checkUserName(String username) { // validate username

        return username.contains("_") && username.length() <= 5; // rules
    }

    // ---------------- PASSWORD VALIDATION ----------------
    public static boolean checkPassword(String password) { // validate password

        return Pattern.matches(
                "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", // regex
                password
        );
    }

    // ---------------- LOGIN CHECK ----------------
    public static boolean login(
            String enteredUser,
            String enteredPass,
            String savedUser,
            String savedPass
    ) {

        return enteredUser.equals(savedUser) // check username
                && enteredPass.equals(savedPass); // check password
    }

    // ---------------- PHONE VALIDATION ----------------
    public static String validateNumber(String number) { // validate phone

        if (number == null) { // null check
            return "Invalid"; // invalid
        }

        if (!number.startsWith("+27")) { // check country code
            return "Invalid: must start with +27"; // error
        }

        if (number.length() != 12) { // check length
            return "Invalid: must be 12 digits (+27XXXXXXXXX)"; // error
        }

        if (!number.substring(3).matches("\\d{9}")) { // digits only
            return "Invalid: only digits allowed after +27"; // error
        }

        return "Valid"; // valid number
    }

    // ---------------- CREATE HASH ----------------
    public static String createMessageHash(
            String id,
            int number,
            String message
    ) {

        String[] words = message.trim().split(" "); // split words

        String firstWord =
                words.length > 0 ? words[0] : "MSG"; // first word

        String lastWord =
                words.length > 1
                        ? words[words.length - 1]
                        : words[0]; // last word

        return (
                id.substring(0, 2) // first 2 chars
                        + ":" // separator
                        + number // message number
                        + ":" // separator
                        + firstWord // first word
                        + lastWord // last word
        ).toUpperCase(); // uppercase hash
    }

    // ---------------- SAVE MESSAGES ----------------
    public static void storeMessage() { // save file

        try {

            FileWriter file = new FileWriter("messages.json"); // create file

            file.write("[\n"); // open json array

            for (int i = 0; i < messageList.size(); i++) { // loop list

                MessageRecord msg = messageList.get(i); // current message

                file.write("  {\n"); // open object

                file.write("    \"id\": \"" + msg.id + "\",\n"); // write id

                file.write("    \"hash\": \"" + msg.hash + "\",\n"); // write hash

                file.write("    \"recipient\": \"" + msg.recipient + "\",\n"); // write recipient

                file.write("    \"message\": \"" + msg.message + "\",\n"); // write message

                file.write("    \"status\": \"" + msg.status + "\",\n"); // write status

                file.write("    \"time\": \"" + msg.timestamp + "\"\n"); // write time

                file.write("  }"); // close object

                if (i < messageList.size() - 1) { // not last object
                    file.write(","); // add comma
                }

                file.write("\n"); // new line
            }

            file.write("]"); // close json array

            file.close(); // close file

            System.out.println("\n[SYSTEM] File saved successfully!"); // success
            System.out.println("[SYSTEM] Total records saved: " + messageList.size()); // count

        } catch (IOException e) { // catch errors

            System.out.println("[ERROR] Failed to save file!"); // error msg
        }
    }

    // ---------------- SHOW MESSAGES ----------------
    public static void showMessages() { // display messages

        System.out.println("\n=============================="); // line
        System.out.println("      SHOW MESSAGES"); // heading
        System.out.println("=============================="); // line

        System.out.println("[COMING SOON] This feature is under development."); // placeholder
        System.out.println("Please check back later."); // note

        System.out.println("==============================\n"); // line
    }

    // ---------------- REGISTRATION ----------------
    public static String[] registerUser() { // register user

        System.out.print("Create Username: "); // ask username
        String savedUsername = input.nextLine(); // read username

        System.out.print("Create Password: "); // ask password
        String savedPassword = input.nextLine(); // read password

        if (!checkUserName(savedUsername) // validate username
                || !checkPassword(savedPassword)) { // validate password

            System.out.println("Invalid registration!"); // error
            return null; // stop
        }

        return new String[]{savedUsername, savedPassword}; // return data
    }

    // ---------------- LOGIN PROCESS ----------------
    public static void loginUser(
            String savedUsername,
            String savedPassword
    ) {

        boolean loggedIn = false; // login flag

        while (!loggedIn) { // repeat until correct

            System.out.print("Login Username: "); // ask username
            String username = input.nextLine(); // read username

            System.out.print("Login Password: "); // ask password
            String password = input.nextLine(); // read password

            loggedIn = login( // validate login
                    username,
                    password,
                    savedUsername,
                    savedPassword
            );

            if (!loggedIn) { // wrong login

                System.out.println("Login failed!"); // error
            }
        }

        System.out.println("Welcome to QuickChat"); // success
    }

    // ---------------- MESSAGE LIMIT ----------------
    public static void setMessageLimit() { // set limit

        System.out.print("How many messages do you want to send? "); // ask limit

        messageLimit = Integer.parseInt(input.nextLine()); // save limit
    }

    // ---------------- SEND MESSAGE ----------------
    public static void sendMessage() { // send message

        if (sentCount >= messageLimit) { // check limit

            System.out.println("[WARNING] Message limit reached!"); // warning
            return; // stop
        }

        System.out.print("Enter Recipient (+27XXXXXXXXX): "); // ask number

        String recipient = input.nextLine(); // read number

        String validation = validateNumber(recipient); // validate number

        if (!validation.equals("Valid")) { // invalid number

            System.out.println("[ERROR] " + validation); // error
            return; // stop
        }

        System.out.print("Enter Message (max 250 chars): "); // ask message

        String message = input.nextLine(); // read message

        if (message.length() > 250) { // length check

            System.out.println("[ERROR] Message too long!"); // error
            return; // stop
        }

        String id = String.format( // generate id
                "%010d",
                (long) (Math.random() * 10000000000L)
        );

        String hash = createMessageHash(id, sentCount, message); // create hash

        String time = new SimpleDateFormat("HH:mm:ss") // format time
                .format(new Date());

        MessageRecord newMessage = new MessageRecord( // create object
                id,
                message,
                recipient,
                time,
                hash,
                "SENT"
        );

        messageList.add(newMessage); // add message

        sentCount++; // increase count

        System.out.println("\n[SUCCESS] MESSAGE SENT"); // success
        System.out.println("ID   : " + id); // show id
        System.out.println("HASH : " + hash); // show hash
    }

    // ---------------- DISCARD MESSAGE ----------------
    public static void discardMessage() { // remove last message

        if (messageList.isEmpty()) { // check empty

            System.out.println("[INFO] No messages to discard."); // info
            return; // stop
        }

        int lastIndex = messageList.size() - 1; // last index

        System.out.println("[SYSTEM] Discarding last message..."); // notice

        System.out.println(
                "Removed: "
                        + messageList.get(lastIndex).message // show removed
        );

        messageList.remove(lastIndex); // remove item

        sentCount--; // reduce count

        System.out.println("[SUCCESS] Message discarded."); // success
    }

    // ---------------- EXIT SYSTEM ----------------
    public static void exitSystem() { // exit app

        System.out.print("Save before exit? (yes/no): "); // ask save

        String save = input.nextLine(); // read answer

        if (save.equalsIgnoreCase("yes")) { // if yes

            storeMessage(); // save file
        }

        System.out.println("Total messages sent: " + sentCount); // total count
        System.out.println("Goodbye!"); // goodbye
    }

    // ---------------- MENU ----------------
    public static void menuLoop() { // menu loop

        while (true) { // infinite loop

            System.out.println("\n========== MENU =========="); // heading
            System.out.println("1. Send Message"); // option 1
            System.out.println("2. Show Messages"); // option 2
            System.out.println("3. Discard Last Message"); // option 3
            System.out.println("4. Save Messages"); // option 4
            System.out.println("5. Quit"); // option 5
            System.out.println("=========================="); // line

            System.out.print("Choose option: "); // ask choice

            String choice = input.nextLine(); // read choice

            switch (choice) { // check option

                case "1":

                    sendMessage(); // send
                    break;

                case "2":

                    showMessages(); // show
                    break;

                case "3":

                    discardMessage(); // discard
                    break;

                case "4":

                    storeMessage(); // save
                    break;

                case "5":

                    exitSystem(); // exit
                    return;

                default:

                    System.out.println("[ERROR] Invalid option!"); // invalid
            }
        }
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) { // start app

        System.out.println("=== QUICKCHAT CONSOLE ==="); // title

        String[] registration = registerUser(); // register user

        if (registration == null) { // invalid registration

            return; // stop
        }

        String savedUsername = registration[0]; // save username
        String savedPassword = registration[1]; // save password

        loginUser(savedUsername, savedPassword); // login

        setMessageLimit(); // set limit

        menuLoop(); // open menu
    }
}
