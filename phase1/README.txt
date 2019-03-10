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
    (containing alphanumeric characters as well as . and -), and string3 is a two or three character-long string of
    letters only.
    * Currency must be added as a number to exactly two decimal places.

The Bank Manager has several special abilities:
    * The Shutdown option stops the system, everything that was done is saved in the files, but to get it running again
    you must run the main method (file-writing only occurs when Shutdown is called)
    * The bank manager should check "view account creation requests" before starting anything, and should remove
    completed tasks by selecting the "remove completed creation requests" action.
    * Undo most recent transaction - cancels the most recent transaction of a specific account.
    * Set time - this can be done anytime the manager is logged in. It may be done as many times as wished.

Depositing money:
Deposits are made using cheques or cash bills using the following syntax in the deposits.txt file:
    * Cheques - write the amount to be deposited in the first line.
        E.g. a deposit of $250 made by a cheque:
        250
    * Cash - write only in the first four lines the amount of bills of each denomination in ascending order.
        E.g. a deposit of $250 by 3 bills of $50, 4 bills of $20, 2 bills of $10 and 0 bills of $5:
        0
        2
        4
        3
After writing to deposits.txt, the user then selects the account to be deposited to, and the deposit option.
NOTE: If the deposit goes through, the contents of deposits.txt will be erased (for the sake of previous users' privacy).


## Sidenotes

FileManager.main should never be run (it was created by us for testing purposes).
