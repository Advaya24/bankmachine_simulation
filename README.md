# bankmachine_simulation

A program that simulates a single bank ATM, with an administrator known as BankManager and multiple clients.
Users only interact with the ATM (no physical bank). They may perform actions on their accounts as well as profile. The project was made in two phases with the information given below relevant to phase 2.


## System Start-up

We used JUnit 5.3 for testing, with mockito 1.10.19 (you might need to install these for the code to compile). We also
used gson-2.8.5.
    *Instructions for installing mockito:
        1) Go to File > Project Structure > Libraries
        2) Press the add button ("+") and search mockito, then press the search button (magnifying glass)
        3) Click on the option with mockito 1.10.19
    *Instructions for installing gson-2.8.5
        1) Go to File > Project Structure > Libraries
        2) Press the add button ("+") and search google.code.gson, then press the search button (magnifying glass)
        3) Click on the option with gson-2.8.5

Run BankMachine.main (phase 2 > src > bankmachine > BankMachine.java), this is the main method for the entire program.
Once the run button is clicked, a popup prompting for you to log in should appear.


## Usage

Upon the first start, the entire database of clients and accounts will be empty.
The only way to make the first login is through the standard BankManager, with username: admin, password: admin. This
will log you in as a Bank Manager called Brad.

Once logged in, the Bank Manager has the option of accessing the bank as a client (their own personal accounts) or as a
manager (with their special abilities described below).

## Bank Manager (Accessing the bank)

    # Creating a new client
    A Bank Manager may add a new client for the bank, by selecting the "Create User" button. This will give them a form
    to fill in regarding the type of user and client's information:
        * The Bank Manager can create clients or employees. Note that an employee has less power than a Bank Manager but
        more power than a client.
            ** An employee can only create accounts, add bills, and view and remove creation requests.
        * Emails are of the form string1@string2.string3, where string1 and string2 being any two arbitrary strings
        (containing alphanumeric characters as well as . and -), and string3 is a two or three character-long string of
        letters only.
        * Phone numbers are of the form XXX-XXX-XXXX (with the hyphens).

    # Shutdown
    The Shutdown option stops the system, everything that was done is saved in the files, but to get it running again
    you must run the main method (file-writing only occurs when Shutdown is called). Note that simply clicking the "stop"
    button or the "x" on the top right of the popup will not ensure that information is saved!

    # View account creation requests
        * The Bank Manager should check this before starting anything! The Bank Manager should also remove completed tasks
        by selecting the "remove completed creation requests" action, after performing the task.

    # Creating a new account
        * Allows the Bank Manager to create any account (Chequing account, Credit card account, Line of credit account,
        Savings account, and Retirement account) for any client, including themselves.

    # Undo transaction
        * Choose a client and an account. This should allow the bank manager to see the most recent transactions
        (excluding withdrawal and pay bills) and will allow them to reverse that transaction.

    # Set time and Run monthly functions
        * Set time: sets the date.
        * Run monthly functions: advances the date to the first of the next month. This can be used to test whether
        interest for the savings account is being calculated and whether the retirement account is working.
        * Note, a date must be set prior to clicking on the "run monthly functions" button.

    # Other actions: add bills (should be done in whole numbers and at least one bill must be added in order for action
    to go through), and logout (switches user).


## Employee (Accessing the bank)
    # Note that an employee can only create accounts, add bills, and view and remove creation request. See bank manager
    above for more details on each action.


## Client (includes the personal accounts of a bank manager and employees)
    * Every client has a primary chequing account that is automatically created when the client is added to the system.

    # Account summary
        * Selecting this button allows the client to view the balances of each account, and the total amount of money
        they have in all accounts.

    # Request Creation of new account
        * Selecting this button allows the client to request the bank manager/employee to help them create an account.

    # Finances
        * Special feature that allows the client to check stocks, calculate exchange rates, and calculate their mortgage.
            * Stocks: real-time stock viewer that allows you to check the current stock rate for a certain stock code.
                ** Stock codes can be found on google, but you can use these as an example "NYSE:S" and "NYSE:APY".
            * Exchange: allows you to convert a certain country's currency to another.
                ** Note that the amount should be entered like any monetary value (2 decimal places).
                ** For "From" and "To", please enter the currency of a certain country that you would like to check
                exchange rates for. For example, from "CAD" (Canadian) to "USD" (American).
            * Mortgage calculator: helps the client calculate their mortgage. Note that principal should be entered
            like any monetary value (2 decimal places) and interest rate is given as any positive number (% form without
            the "%" sign).

    # Update profile
        * Allows client to change their phone number, email, or password.

    # Transfer
        * Internal transfer: allows a client to transfer money from one of their accounts to another. Note that account
         specifications still apply (e.g. can't transfer money out of a credit card account) /
        * Transfer to other user: allows a client to transfer money to another user. Please note that the other's user's
        name (not the user name) must be known.

    # Add User to Account
        * Allows the user to form a "joint account" with another existing user. Select the account you would like to
        share ownership with and enter the other user's name.
        * Note that there could only be ONE other person a user can share their account with (cannot have a 3 way joint
        account).

    # Other buttons: Withdraw, Pay Bills, and Deposit follow the specifications in the instructions. Note that monetary
    values should be entered as a number to 2 decimal places.


## Special user (flappy floof)
    * If the username "Flappy" and the password "floof" is entered in the login page, a game called flappy floof appears.
    This game is played like "flappy birds", where pressing the "space bar" or clicking the mouse, allows for the cube
    to "fly". To exit this mode, simply press on "e" key on the keyboard.


## Other remarks

## Depositing money:
    Deposits are made using cheques or cash (bills) using the following syntax in the deposits.txt file:
        * Cheques - write the amount to be deposited in the first line.
            E.g. a deposit of $250 made by a cheque:
            250
            E.g. another deposit of $250 made by a cheque:
            250.00
        * Cash - write only in the first four lines the amount of bills of each denomination in ascending order.
            E.g. a deposit of $250 by 3 bills of $50, 4 bills of $20, 2 bills of $10 and 0 bills of $5:
            0
            2
            4
            3
    After writing to deposits.txt, the user then selects the account to be deposited to, and lastly the deposit option.
    NOTE: If the deposit goes through, the contents of deposits.txt will be erased (for the sake of previous users'
    privacy).


## Side-notes

FileManager.main should never be run (it was created by us for testing purposes).

