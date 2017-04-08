Project Design-

Big O - O(n) in the number of lines in the input text file.

The project can be broken into two parts:
1)Loading the data from input into a data structure
2)Parsing the results of the data structure and doing a reconciliation

Part 1)

Loading the data is loosely coupled from parsing the data by defining an interface with
the necessary functions. This enables the program to pull data from any input source without
affecting the core business logic.
The ReconLoaderFactory class follows the factory design pattern and should contain
one method for each different way of loading the data.
The method may also take parameters depending on what is needed. For example, for the
function returning a TextFileLoader, the method takes a parameter for the file name

The loader fills in either the ReconDay, List<Transaction>, or Map<String, Double> data structure.
Map<String,Double> represents positions where the key is either the stock symbol or "Cash"
and the value is the amount of shares or money for the key. List<Transaction> represents all
transactions for a given day. 
Lastly ReconDay represents the day's transactions and end of day position.
It contains both a Map<String,Double> and List<Transactions>.
ReconDay contains functionality for applying the transactions to a given Map, and it contains
functionality for comparing a given Map<String,Double> against it's  own Map<String,Double>. 

Part 2)
Given ReconDay stores nth transaction and nth position, and given nth position
represents the position after the nth transactions, the applyTransactions function is used
to apply the transactions to the nth-1 pos. Then doRecon and compares the resulting map against the
nth pos map. The map resulting from applying the transactions will be what the pos should be,
and the map on Recon Day will be what is in the file. As such comparing the two will
yield the discrepancies that will be outputted.

The last part is how to apply the Transactions themselves. Each transaction implements
the Transaction interface. This allows us to store all Transactions in a single data
structure and apply them one after another in a loop. That allows the code to apply as
many transactions as are in a given day. They also allow us to loosely couple the applying
and defining transactions.
The classes Buy, Sell, Deposit, Dividend, and Fee all implement this interface and represent
the types of transactions currently possible. Using Spring, they are injected into a Map
on the Actions object where there is a mapping between their name in the data file and
the class. This allows the Loader to know which class to generate for a given transaction.

For Buy and Sell, and for Deposit, Dividend, and Fee the code is very similar. Often times
the only difference is in which direction the share and cash is changing. As such much of
the code is abstracted to two parent classes, BuySell, and CashChange.

Functionality common to Buy and Sell is abstracted to BuySell, and functionality common to
Deposit, Dividend, and Fee is abstracted to CashChange.

Both BuySell and CashChange have an additional boolean parameter telling it which direction 
the share and cash are changing. This parameter is supplied by the child classes. The interface
does not and should not contain this boolean parameter because it is the job of the 
implementing class which direction shares and cash are moving in.


Spring Dependency Injection vs. Factory pattern:

Both ReconLoader and Transaction have the requirement of needing some
config or code to produce the right concerete implementation. The reason for using Spring
for one and Factory pattern for the other has to do with other factors.

For a single program only one ReconLoader is needed and it depends on
the input type. A Factory can produce a single ReconLoader depending on a variety of factors.

On the other hand it is possible all Transactions must be applied, and therefore instantiated.
Spring is better suited because it can autowire a Map together that maps the transaction
string in the input to the transaction class. This map can then be used to derive the
needed concrete implementations based on what is in the input.

Program Flow:
After understanding the components of the program, it is relatively easy to describe
the program flow. The program starts in a static Main function in Reconciliation class.
The main function utilizes Reconciliation, and therefore picks which ReconLoader it needs.
In this case it picks TextFileLoader. Then the TextFileLoader loads the initial position
into a Map<String,Double>. TextFileLoader then loads a ReconDay object for the rest of the
textFile. For each transaction, TextFileLoader loads the requisite concrete implementation, and
for the end of day pos it loads a new Map<String,Double>. Then the program applies all
transactions to the initial position, and compares against the end of day pos Map. This
comparison returns a Map<String,Double> containing the reconciliation result. Lastly the
result is printed to stdout.

Future Considerations-
1)New Transactions: Creating a new transaction is as easy as having a new Class implement
the PortfolioAction interface and editing the Spring config to autowire the new class
to the Map in Actions.
2)Multiple Days: Implementing multiple day is also relatively easy. One way would be
to change the return type of currentDayDisc to what the end of day position should be, putting
a loop around the currentDayDisc call in startRecon, updating the initial position
to be the return value of currentDayDisc, and let the loop rerun the reconciliation.
3)Different Input Sources: To add the ability to load data from a different input type,
simply define a new class that implements ReconLoader and add a method to ReconLoaderFactory
to return that class


