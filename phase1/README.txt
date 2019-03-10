# bankmachine

A program that simulates a single bank ATM, with an administrator known as BankManager and multiple clients.
Users only interact with the ATM (no physical bank). They may perform actions on their accounts as well as profile.


## System Start-up

Run BankMachine.main, this is the main method for the entire program.
Upon the first start, the entire database of clients and accounts will be empty.
The only way to make the first login is through the standard BankManager, with username: admin, password: admin.


## Usage

Once logged in, the Bank Manager may add a new client for the bank, inputting the necessary information as below:
    * Phone numbers are of the form XXX-XXX-XXXX (with the hyphens).
    * Emails are of the form string1@string2.string3, where string1 and string2 being any two arbitrary strings
      (containing [a-zA-Z], [-], and [0-9]), and string3 is a two or three character-long string of letters only.
    * Currency must be added as a number to exactly two decimal places.

The Bank Manager has several special abilities:
    * The Shutdown option stops the system, everything that was done is saved in the files, but to get it running again you
      must run the main method (file-writing only occurs when Shutdown is called)
    *

Depositing money:
    To make a deposit, you must write the deposits.txt file.




